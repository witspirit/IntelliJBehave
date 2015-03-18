package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;

/**
 * Created by DeBritoD on 18.03.2015.
 */
public class RenameJbehaveAnnotationProcessor extends RenamePsiElementProcessor {
    @Override
    public boolean canProcessElement(PsiElement element) {
        PsiElement help = element;
        while (!(help instanceof PsiAnnotation) && help != null) {
            help = help.getParent();
        }
        return help != null;
    }
}
