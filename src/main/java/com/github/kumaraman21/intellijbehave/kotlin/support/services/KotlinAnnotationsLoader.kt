package com.github.kumaraman21.intellijbehave.kotlin.support.services

import com.github.kumaraman21.intellijbehave.kotlin.psi.NavigableKotlinPsiAnnotation
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.QualifiedName
import org.jetbrains.kotlin.asJava.LightClassUtil
import org.jetbrains.kotlin.idea.stubindex.KotlinAnnotationsIndex
import org.jetbrains.kotlin.psi.KtFunction

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
class KotlinAnnotationsLoader private constructor() {

    companion object {
        /**
         * Returns all occurrences of the step annotation referenced by [qualifiedName] in Kotlin files, in the given [scope].
         *
         * @param qualifiedName a step annotation, e.g. `@org.jbehave.core.annotations.Given`
         * @param project       the current project
         * @param scope         the search scope where to look up the annotation usage
         *
         * @see com.github.kumaraman21.intellijbehave.service.JBehaveStepsIndex.getAllStepAnnotations
         */
        @JvmStatic
        fun getAnnotations(qualifiedName: QualifiedName, project: Project, scope: GlobalSearchScope): Collection<PsiAnnotation> {
            val name = qualifiedName.lastComponent
            return if (name != null) {
                KotlinAnnotationsIndex.get(name, project, scope)
                    .asSequence()
                    .map { ktAnnotation ->
                        val function = ktAnnotation.parent?.parent as? KtFunction
                        function?.let {
                            val psiAnnotation = LightClassUtil.getLightClassMethod(function)?.modifierList?.findAnnotation(qualifiedName.toString())
                            psiAnnotation?.let { NavigableKotlinPsiAnnotation(psiAnnotation, ktAnnotation) }
                        }
                    }.filterNotNull().toList()
            } else emptyList()
        }
    }
}
