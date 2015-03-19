package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.actions.BaseRefactoringAction;
import com.intellij.refactoring.rename.PsiElementRenameHandler;

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

    private PsiAnnotation getPsiAnnotation(Editor editor, PsiFile psiFile) {
        if (editor != null && psiFile != null) {
            PsiElement elementAtCaret = BaseRefactoringAction.getElementAtCaret(editor, psiFile);
            PsiAnnotation annotation = PsiTreeUtil.getParentOfType(elementAtCaret, PsiAnnotation.class);
            if (annotation != null) {
                String qualifiedName = annotation.getQualifiedName();
                if (qualifiedName != null && qualifiedName.startsWith("org.jbehave.core.annotations")) {
                    return annotation;
                }
            }
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
