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
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinPsiClassesHandler private constructor() {

    companion object {
        @JvmStatic
        fun getPsiClasses(psiFile: PsiFile): Array<PsiClass>? = if (psiFile is KtFile) {
            psiFile.classes
        } else null

        @JvmStatic
        fun isKotlinFile(psiFile: PsiFile): Boolean = psiFile is KtFile

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

        private fun isKotlinJBehaveStepDefClass(aClass: KtClass): Boolean {
            return !aClass.isEnum() && !aClass.isInterface() && aClass.fqName != null && aClass.body?.functions?.any {
                it.findAnnotation(FqName("org.jbehave.core.annotations.Given")) != null
                        || it.findAnnotation(FqName("org.jbehave.core.annotations.When")) != null
                        || it.findAnnotation(FqName("org.jbehave.core.annotations.Then")) != null
                        || it.findAnnotation(FqName("org.jbehave.core.annotations.Alias")) != null
                        || it.findAnnotation(FqName("org.jbehave.core.annotations.Aliases")) != null
                        || it.findAnnotation(FqName("org.jbehave.core.annotations.Composite")) != null
            } == true
        }
    }
}
