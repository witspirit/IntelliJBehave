package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.refactoring.actions.BaseRefactoringAction;
import com.intellij.refactoring.rename.PsiElementRenameHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DeBritoD on 18.03.2015.
 */
public class JBehaveRenameStepParameterHandler extends PsiElementRenameHandler {

    private boolean canProcessElement(PsiElement element) {
        IElementType type = element.getNode().getElementType();
        if (type == IJBehaveElementType.JB_TOKEN_WORD) {
            Boolean userData = element.getUserData(ParserRule.isStepParameter);
            return userData != null && userData;
        }
        return false;
    }

    @Override
    public boolean isAvailableOnDataContext(DataContext dataContext) {
        return getStepParameter(dataContext) != null;
    }

    private PsiElement getStepParameter(DataContext dataContext) {
        Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        PsiFile psiFile = PlatformDataKeys.PSI_FILE.getData(dataContext);
        return getStepParameter(editor, psiFile);
    }

    private PsiElement getStepParameter(Editor editor, PsiFile psiFile) {
        final PsiElement elementAtCaret = BaseRefactoringAction.getElementAtCaret(editor, psiFile);
        if (canProcessElement(elementAtCaret)) {
            return elementAtCaret;
        }
        return null;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file, DataContext dataContext) {
        PsiElement inject = getStepParameter(editor, file);
        if (inject != null) {
            editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
            invoke(inject, project, inject, editor);
        } else {
            super.invoke(project, editor, file, dataContext);
        }
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiElement[] elements, DataContext dataContext) {
        PsiElement psiAnnotation = getStepParameter(dataContext);
        if (psiAnnotation == null) {
            super.invoke(project, elements, dataContext);
        }
    }
}
