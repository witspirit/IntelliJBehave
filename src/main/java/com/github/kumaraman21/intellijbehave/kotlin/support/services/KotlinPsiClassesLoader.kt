package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtFile

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinPsiClassesLoader private constructor() {

    companion object {
        @JvmStatic
        fun getPsiClasses(psiFile: PsiFile): Array<PsiClass>? = if (psiFile is KtFile) {
            psiFile.classes
        }
        else null
    }
}