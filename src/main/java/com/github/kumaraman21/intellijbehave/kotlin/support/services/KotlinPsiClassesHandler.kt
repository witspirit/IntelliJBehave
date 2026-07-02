package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.analysis.api.KaSession
import org.jetbrains.kotlin.analysis.api.analyze
import org.jetbrains.kotlin.idea.k2.codeinsight.structuralsearch.visitor.KotlinRecursiveElementVisitor
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.isPublic

/**
 * Provides utilities for working with and processing Kotlin files and class.
 *
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinPsiClassesHandler private constructor() {

    companion object {
        private val GIVEN = ClassId.fromString("org/jbehave/core/annotations/Given")
        private val WHEN = ClassId.fromString("org/jbehave/core/annotations/When")
        private val THEN = ClassId.fromString("org/jbehave/core/annotations/Then")
        private val ALIAS = ClassId.fromString("org/jbehave/core/annotations/Alias")
        private val ALIASES = ClassId.fromString("org/jbehave/core/annotations/Aliases")
        private val COMPOSITE = ClassId.fromString("org/jbehave/core/annotations/Composite")

        /**
         * Returns the classes in [psiFile] if it is a Kotlin file, otherwise returns null.
         */
        @JvmStatic
        fun getPsiClasses(psiFile: PsiFile): Array<PsiClass>? = if (psiFile is KtFile) {
            psiFile.classes
        } else null

        @JvmStatic
        fun isKotlinFile(psiFile: PsiFile): Boolean = psiFile is KtFile

        /**
         * Returns if the provided Kotlin [file] contains any step definition class,
         * meaning at least one class that contains at least one step definition method.
         *
         * If the file is not a Kotlin file, it returns false.
         */
        @JvmStatic
        fun visitClasses(file: PsiFile): Boolean {
            val hasJBehaveStepDefTestClass = Ref<Boolean>(false)
            if (file is KtFile) {
                analyze(file) {
                    file.accept(object : KotlinRecursiveElementVisitor() {
                        override fun visitClass(aClass: KtClass) {
                            if (isKotlinJBehaveStepDefClass(aClass)) {
                                hasJBehaveStepDefTestClass.set(true)
                                return
                            }
                            super.visitClass(aClass)
                        }
                    })
                }
            }

            return hasJBehaveStepDefTestClass.get()
        }

        /**
         * Returns if any of the functions in the provided Kotlin class is a step definition function.
         */
        private fun KaSession.isKotlinJBehaveStepDefClass(aClass: KtClass): Boolean {
            return try {
                ReadAction.computeBlocking<Boolean, Exception> {
                    !aClass.isEnum()
                            && !aClass.isInterface()
                            &&  aClass.fqName != null
                            && aClass.body?.functions?.asSequence()?.filter { it.isPublic }?.any {
                        return@any try {
                            it.symbol.annotations.any { ann ->
                                ann.classId == GIVEN
                                        || ann.classId == WHEN
                                        || ann.classId == THEN
                                        || ann.classId == ALIAS
                                        || ann.classId == ALIASES
                                        || ann.classId == COMPOSITE
                            }
                        } catch (_: Exception) {
                            false
                        }
                    } == true
                }
            } catch (_: Exception) {
                false
            }
        }
    }
}
