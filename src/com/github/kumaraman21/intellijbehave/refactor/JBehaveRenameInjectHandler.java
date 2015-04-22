package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveLanguage;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
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
public class JBehaveRenameInjectHandler extends PsiElementRenameHandler {
    @Override
    public boolean isAvailableOnDataContext(DataContext dataContext) {
        return getInject(dataContext) != null;
    }

    private PsiElement getInject(DataContext dataContext) {
        Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        PsiFile psiFile = PlatformDataKeys.PSI_FILE.getData(dataContext);
        return getInject(editor, psiFile);
    }

    private PsiElement getInject(Editor editor, PsiFile psiFile) {
        final PsiElement elementAtCaret = BaseRefactoringAction.getElementAtCaret(editor, psiFile);
        if (psiFile.getLanguage() == JBehaveLanguage.JBEHAVE_LANGUAGE) {
            IElementType type = elementAtCaret.getNode().getElementType();
            if (type == IJBehaveElementType.JB_TOKEN_USER_INJECT || type == IJBehaveElementType.JB_TOKEN_INJECT) {
                return elementAtCaret;
            }
        }
        return null;
    }

    //    @Override
    //    public boolean isRenaming(DataContext dataContext) {
    //        return isAvailableOnDataContext(dataContext);
    //    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file, DataContext dataContext) {
        PsiElement inject = getInject(editor, file);
        if (inject != null) {
            editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
            invoke(inject, project, inject, editor);
        } else {
            super.invoke(project, editor, file, dataContext);
        }
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiElement[] elements, DataContext dataContext) {
        PsiElement psiAnnotation = getInject(dataContext);
        if (psiAnnotation == null) {
            super.invoke(project, elements, dataContext);
        }
    }
}
