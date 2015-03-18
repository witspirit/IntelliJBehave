package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.rename.RenameHandler;

/**
 * Created by DeBritoD on 18.03.2015.
 */
public class JbehaveRenameAnnotationHandler implements RenameHandler {
    @Override
    public boolean isAvailableOnDataContext(DataContext dataContext) {
        return false;
    }

    @Override
    public boolean isRenaming(DataContext dataContext) {
        return false;
    }

    @Override
    public void invoke(Project project, Editor editor, PsiFile file, DataContext dataContext) {

    }

    @Override
    public void invoke(Project project, PsiElement[] elements, DataContext dataContext) {

    }
}
