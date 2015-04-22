package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.rename.RenameDialog;

/**
 * Created by DeBritoD on 18.03.2015.
 */
class JBehaveAnnotationRenameDialog extends RenameDialog {
    public JBehaveAnnotationRenameDialog(Project project, PsiElement psiElement, PsiElement nameSuggestionContext,
                                         Editor editor) {
        super(project, psiElement, nameSuggestionContext, editor);
    }

    @Override
    protected boolean areButtonsValid() {
        return true;
    }
}
