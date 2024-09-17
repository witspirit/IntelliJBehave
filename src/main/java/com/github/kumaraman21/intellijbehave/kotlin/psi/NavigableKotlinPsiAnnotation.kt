package com.github.kumaraman21.intellijbehave.kotlin.psi

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtElement

/**
 * Represents a Kotlin PSI annotation with its PSI and Kotlin element counterparts.
 * This type also provides (overrides) some of the navigation behaviours from its parent class.
 *
 * Created by Rodrigo Quesada on 20/09/15.
 */
class NavigableKotlinPsiAnnotation(
        private val psiAnnotation: PsiAnnotation,
        ktElement: KtElement
) : PsiAnnotation by psiAnnotation {

    private val navigableKotlinPsiElement = NavigableKotlinPsiElement(psiAnnotation, ktElement)

    override fun getTextOffset(): Int = navigableKotlinPsiElement.textOffset

    override fun getParent(): PsiElement = navigableKotlinPsiElement.parent

    override fun getNavigationElement(): PsiElement = navigableKotlinPsiElement.navigationElement
}