package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveLanguage;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.resolver.ScenarioStepReference;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.actions.BaseRefactoringAction;
import com.intellij.refactoring.rename.PsiElementRenameHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by DeBritoD on 18.03.2015.
 */
public class JBehaveRenameAnnotationHandler extends PsiElementRenameHandler {
    @Override
    public boolean isAvailableOnDataContext(DataContext dataContext) {
        return getPsiAnnotation(dataContext) != null;
    }

    private PsiElement getPsiAnnotation(DataContext dataContext) {
        Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        PsiFile psiFile = PlatformDataKeys.PSI_FILE.getData(dataContext);
        return getPsiAnnotation(editor, psiFile);
    }

    private PsiElement getPsiAnnotationFromScenarioStep(@NotNull PsiElement elementAtCaret) {
        ScenarioStep scenarioStep = PsiTreeUtil.getParentOfType(elementAtCaret, ScenarioStep.class);
        if (scenarioStep != null) {
            PsiReference[] references = scenarioStep.getReferences();
            for (PsiReference reference : references) {
                reference.resolve();
                if (reference instanceof ScenarioStepReference) {
                    ScenarioStepReference stepRef = (ScenarioStepReference) reference;
                    Set<PsiAnnotation> annotations = stepRef.getAnnotations();
                    for (PsiAnnotation annotation : annotations) {
                        String qualifiedName = annotation.getQualifiedName();
                        if (qualifiedName != null && qualifiedName.startsWith("org.jbehave.core.annotations")) {
                            return annotation;
                        }
                    }
                }
            }

        }
        return null;
    }

    private PsiElement getPsiAnnotationFromJava(@NotNull PsiElement elementAtCaret) {
        PsiAnnotation annotation = PsiTreeUtil.getParentOfType(elementAtCaret, PsiAnnotation.class);
        if (annotation != null) {
            String qualifiedName = annotation.getQualifiedName();
            if (qualifiedName != null && qualifiedName.startsWith("org.jbehave.core.annotations")) {
                return annotation;
            }
        }
        return null;
    }

    private PsiElement getPsiAnnotation(Editor editor, PsiFile psiFile) {
        final PsiElement elementAtCaret = BaseRefactoringAction.getElementAtCaret(editor, psiFile);
        IElementType type = elementAtCaret.getNode().getElementType();
        if (type == IJBehaveElementType.JB_TOKEN_USER_INJECT || type == IJBehaveElementType.JB_TOKEN_INJECT) {
            return null;
        }
        if (type == IJBehaveElementType.JB_TOKEN_WORD) {
            Boolean userData = elementAtCaret.getUserData(ParserRule.isStepParameter);
            if (userData != null && userData) return null;
        }
        if (psiFile.getLanguage() == JavaLanguage.INSTANCE) {
            return getPsiAnnotationFromJava(elementAtCaret);
        }
        if (psiFile.getLanguage() == JBehaveLanguage.JBEHAVE_LANGUAGE) {
            return getPsiAnnotationFromScenarioStep(elementAtCaret);
        }
        return null;
    }

    //    @Override
    //    public boolean isRenaming(DataContext dataContext) {
    //        return isAvailableOnDataContext(dataContext);
    //    }

    @Override
    public void invoke(Project project, Editor editor, PsiFile file, DataContext dataContext) {
        PsiElement psiAnnotation = getPsiAnnotation(editor, file);
        if (psiAnnotation != null) {
            editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
            PsiElement element = PsiTreeUtil.findChildOfType(psiAnnotation, PsiLiteralExpression.class);
            if (element != null) {
                invoke(element, project, element, editor);
            }
        } else {
            super.invoke(project, editor, file, dataContext);
        }
    }

    @Override
    public void invoke(Project project, PsiElement[] elements, DataContext dataContext) {
        PsiElement psiAnnotation = getPsiAnnotation(dataContext);
        if (psiAnnotation == null) {
            super.invoke(project, elements, dataContext);
        }
    }
}
