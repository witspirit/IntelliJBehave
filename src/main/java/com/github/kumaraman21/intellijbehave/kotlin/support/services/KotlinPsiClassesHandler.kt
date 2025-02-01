package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.idea.structuralsearch.visitor.KotlinRecursiveElementVisitor
import org.jetbrains.kotlin.idea.util.findAnnotation
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import kotlin.jvm.internal.Ref.BooleanRef

/**
 * Provides utilities for working with and processing Kotlin files and class.
 *
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinPsiClassesHandler private constructor() {

    companion object {
        private val GIVEN = FqName("org.jbehave.core.annotations.Given")
        private val WHEN = FqName("org.jbehave.core.annotations.When")
        private val THEN = FqName("org.jbehave.core.annotations.Then")
        private val ALIAS = FqName("org.jbehave.core.annotations.Alias")
        private val ALIASES = FqName("org.jbehave.core.annotations.Aliases")
        private val COMPOSITE = FqName("org.jbehave.core.annotations.Composite")

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
            val hasJBehaveStepDefTestClass = BooleanRef()
            if (file is KtFile) {
                file.accept(object : KotlinRecursiveElementVisitor() {
                    override fun visitClass(aClass: KtClass) {
                        if (isKotlinJBehaveStepDefClass(aClass)) {
                            hasJBehaveStepDefTestClass.element = true
                            return
                        }
                        super.visitClass(aClass)
                    }
                })
            }

            return hasJBehaveStepDefTestClass.element
        }

        /**
         * Returns if any of the functions in the provided Kotlin class is a step definition function.
         */
        private fun isKotlinJBehaveStepDefClass(aClass: KtClass): Boolean {
            return try {
                !aClass.isEnum() && !aClass.isInterface() && aClass.fqName != null && aClass.body?.functions?.any {
                    return@any try {
                        it.findAnnotation(GIVEN) != null
                                || it.findAnnotation(WHEN) != null
                                || it.findAnnotation(THEN) != null
                                || it.findAnnotation(ALIAS) != null
                                || it.findAnnotation(ALIASES) != null
                                || it.findAnnotation(COMPOSITE) != null
                    } catch (e: Exception) {
                        false
                    }
                } == true
            } catch (e: Exception) {
                false
            }
        }
    }
}
