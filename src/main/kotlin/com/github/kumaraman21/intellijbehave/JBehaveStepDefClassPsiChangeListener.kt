package com.github.kumaraman21.intellijbehave

import com.github.kumaraman21.intellijbehave.kotlin.isKotlinPluginEnabled
import com.github.kumaraman21.intellijbehave.kotlin.support.services.KotlinPsiClassesHandler
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.application.readAction
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref
import com.intellij.psi.JavaRecursiveElementVisitor
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiTreeChangeAdapter
import com.intellij.psi.PsiTreeChangeEvent
import com.intellij.util.ui.update.MergingUpdateQueue
import com.intellij.util.ui.update.Update
import kotlinx.coroutines.launch

/**
 * Reacts to changes in JBehave Java step definition files.
 *
 * TODO: this should probably have a counterpart for Kotlin step def classes
 */
class JBehaveStepDefClassPsiChangeListener(val project: Project) : PsiTreeChangeAdapter() {

    private val updateQueue = MergingUpdateQueue(
        "JBehaveStepDefClassPsiChangeListener",
        300, // ms delay
        true, // activate
        null,
        JBehaveStepDefClassesModificationTracker.getInstance(project) // disposable parent
    )

    override fun childrenChanged(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childAdded(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childMoved(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childRemoved(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childReplaced(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun propertyChanged(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)

    private fun updateJBehaveTestClassModificationTracker(event: PsiTreeChangeEvent) {
        val file = event.file
        if (file != null && file.isValid) {
            updateQueue.queue(Update.create(project) { updateModificationTrackerIfFileContainsJBehaveStepDefClass(file) })
        }
        //file is null when the file has just been deleted
        else {
            val child = event.child
            if (child is PsiJavaFile && child.isValid) {
                updateQueue.queue(Update.create(project) {
                    updateModificationTrackerIfFileContainsJBehaveStepDefClass(
                        child
                    )
                })
            }
        }
    }

    /**
     * Updates the [JBehaveStepDefClassesModificationTracker] if the provided file is a JBehave step definitions file.
     *
     * @param file the PsiFile being examined
     */
    private fun updateModificationTrackerIfFileContainsJBehaveStepDefClass(file: PsiFile) {
        if (!DumbService.isDumb(project)) {
            val tracker = JBehaveStepDefClassesModificationTracker.getInstance(project)

            tracker.cs.launch {
                val hasJBehaveStepDefTestClass = Ref<Boolean>()
                if (file is PsiJavaFile) {
                    readAction {
                        //If it finds a JBehave step def class in the file, then modification tracker will be eligible for update
                        file.accept(object : JavaRecursiveElementVisitor() {
                            override fun visitClass(aClass: PsiClass) {
                                if (isJavaJBehaveStepDefClass(aClass)) {
                                    hasJBehaveStepDefTestClass.set(true)
                                    return
                                }
                                super.visitClass(aClass)
                            }
                        })
                    }
                } else if (isKotlinPluginEnabled && KotlinPsiClassesHandler.isKotlinFile(file)) {
                    if (KotlinPsiClassesHandler.visitClasses(file))
                        hasJBehaveStepDefTestClass.set(true)
                }

                if (!hasJBehaveStepDefTestClass.isNull && hasJBehaveStepDefTestClass.get()) {
                    tracker.increaseModificationCount()
                }
            }
        }
    }

    private fun isJavaJBehaveStepDefClass(aClass: PsiClass): Boolean {
        return try {
            ReadAction.computeBlocking<Boolean, Exception> {
                !aClass.isEnum
                        && !aClass.isRecord
                        && !aClass.isInterface
                        && aClass.qualifiedName != null
                        //Treat only the public methods of this class as step defs as the visitor also goes into nested and
                        // super classes, so listing methods from no need
                        && aClass.methods.asSequence().filter { it.hasModifierProperty(PsiModifier.PUBLIC) }.any {
                    return@any try {
                        it.hasAnnotation("org.jbehave.core.annotations.Given")
                                || it.hasAnnotation("org.jbehave.core.annotations.When")
                                || it.hasAnnotation("org.jbehave.core.annotations.Then")
                                || it.hasAnnotation("org.jbehave.core.annotations.Alias")
                                || it.hasAnnotation("org.jbehave.core.annotations.Aliases")
                                || it.hasAnnotation("org.jbehave.core.annotations.Composite")
                    } catch (_: Exception) {
                        false
                    }
                }
            }
        } catch (_: Exception) {
            false
        }
    }
}
