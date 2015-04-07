package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveLanguage;
import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.resolver.ScenarioStepReference;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
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
        return getPsiAnnotation(dataContext) != null || super.isAvailableOnDataContext(dataContext);
    }

    private PsiAnnotation getPsiAnnotation(DataContext dataContext) {
        Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        PsiFile psiFile = PlatformDataKeys.PSI_FILE.getData(dataContext);
        return getPsiAnnotation(editor, psiFile);
    }

    private PsiAnnotation getPsiAnnotationFromScenarioStep(@NotNull Editor editor, @NotNull PsiFile psiFile) {
        PsiElement elementAtCaret = BaseRefactoringAction.getElementAtCaret(editor, psiFile);
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

    private PsiAnnotation getPsiAnnotationFromJava(@NotNull Editor editor, @NotNull PsiFile psiFile) {
        PsiElement elementAtCaret = BaseRefactoringAction.getElementAtCaret(editor, psiFile);
        PsiAnnotation annotation = PsiTreeUtil.getParentOfType(elementAtCaret, PsiAnnotation.class);
        if (annotation != null) {
            String qualifiedName = annotation.getQualifiedName();
            if (qualifiedName != null && qualifiedName.startsWith("org.jbehave.core.annotations")) {
                return annotation;
            }
        }
        return null;
    }

    private PsiAnnotation getPsiAnnotation(Editor editor, PsiFile psiFile) {
        if (psiFile.getLanguage() == JavaLanguage.INSTANCE) {
            return getPsiAnnotationFromJava(editor, psiFile);
        }
        if (psiFile.getLanguage() == JBehaveLanguage.JBEHAVE_LANGUAGE) {
            return getPsiAnnotationFromScenarioStep(editor, psiFile);
        }
        return null;
    }

//    @Override
//    public boolean isRenaming(DataContext dataContext) {
//        return isAvailableOnDataContext(dataContext);
//    }

    @Override
    public void invoke(Project project, Editor editor, PsiFile file, DataContext dataContext) {
        PsiAnnotation psiAnnotation = getPsiAnnotation(editor, file);
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
        PsiAnnotation psiAnnotation = getPsiAnnotation(dataContext);
        if (psiAnnotation != null) {

        } else {
            super.invoke(project, elements, dataContext);
        }
    }
}
