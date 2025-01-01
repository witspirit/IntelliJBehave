package com.github.kumaraman21.intellijbehave

import com.github.kumaraman21.intellijbehave.kotlin.pluginIsEnabled
import com.github.kumaraman21.intellijbehave.kotlin.support.services.KotlinPsiClassesHandler
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref
import com.intellij.psi.JavaRecursiveElementVisitor
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiTreeChangeAdapter
import com.intellij.psi.PsiTreeChangeEvent

/**
 * Reacts to changes in JBehave Java step definition files.
 *
 * TODO: this should probably have a counterpart for Kotlin step def classes
 */
class JBehaveStepDefClassPsiChangeListener(val project: Project) : PsiTreeChangeAdapter() {

    override fun childrenChanged(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childAdded(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childMoved(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childRemoved(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun childReplaced(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)
    override fun propertyChanged(event: PsiTreeChangeEvent) = updateJBehaveTestClassModificationTracker(event)

    private fun updateJBehaveTestClassModificationTracker(event: PsiTreeChangeEvent) {
        val file = event.file
        if (file != null && file.isValid) {
            updateModificationTrackerIfFileContainsJBehaveStepDefClass(file)
        }
        //file is null when the file has just been deleted
        else {
            val child = event.child
            if (child is PsiJavaFile && child.isValid) {
                updateModificationTrackerIfFileContainsJBehaveStepDefClass(child)
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
            val hasJBehaveStepDefTestClass = Ref<Boolean>()
            if (file is PsiJavaFile) {
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
            } else if (pluginIsEnabled && KotlinPsiClassesHandler.isKotlinFile(file)) {
                if (KotlinPsiClassesHandler.visitClasses(file))
                    hasJBehaveStepDefTestClass.set(true)
            }

            if (!hasJBehaveStepDefTestClass.isNull && hasJBehaveStepDefTestClass.get()) {
                JBehaveStepDefClassesModificationTracker.getInstance(project).increaseModificationCount()
            }
        }
    }

    private fun isJavaJBehaveStepDefClass(aClass: PsiClass): Boolean {
        return try {
            !aClass.isEnum && !aClass.isRecord && !aClass.isInterface && aClass.qualifiedName != null && aClass.allMethods.any {
                it.hasAnnotation("org.jbehave.core.annotations.Given")
                    || it.hasAnnotation("org.jbehave.core.annotations.When")
                    || it.hasAnnotation("org.jbehave.core.annotations.Then")
                    || it.hasAnnotation("org.jbehave.core.annotations.Alias")
                    || it.hasAnnotation("org.jbehave.core.annotations.Aliases")
                    || it.hasAnnotation("org.jbehave.core.annotations.Composite")
            }
        } catch (e: Exception) {
            false
        }
    }
}
