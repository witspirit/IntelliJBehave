package com.github.kumaraman21.intellijbehave.structureView;

import com.github.kumaraman21.intellijbehave.parser.JBehaveFile;
import com.github.kumaraman21.intellijbehave.parser.Scenario;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/**
 * Created by DeBritoD on 03.04.2015.
 */
class StructureViewElement implements StructureViewTreeElement {
    private final NavigatablePsiElement element;

    public StructureViewElement(NavigatablePsiElement element) {
        this.element = element;
    }

    @Override
    public Object getValue() {
        return element;
    }

    @Override
    public void navigate(boolean requestFocus) {
        element.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return element.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element.canNavigateToSource();
    }


    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = element.getPresentation();
        if (presentation == null) {
            presentation = new MyItemPresentation();
        }
        return presentation;
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        Collection<? extends PsiElement> structureViewChildren;
        if (element instanceof JBehaveFile) {
            structureViewChildren = ((JBehaveFile) element).getStructureViewChildren();
        } else if (element instanceof Scenario) {
            structureViewChildren = ((Scenario) element).getSteps();
        } else return EMPTY_ARRAY;
        return ContainerUtil.map2Array(structureViewChildren, TreeElement.class, new PsiElementTreeElementFunction());
    }

    private static class PsiElementTreeElementFunction implements Function<PsiElement, TreeElement> {
        @Override
        public TreeElement fun(PsiElement psiElement) {
            return new StructureViewElement((NavigatablePsiElement) psiElement);
        }
    }

    private static class MyItemPresentation implements ItemPresentation {
        @Nullable
        @Override
        public String getPresentableText() {
            return null;
        }

        @Nullable
        @Override
        public String getLocationString() {
            return null;
        }

        @Nullable
        @Override
        public Icon getIcon(boolean unused) {
            return null;
        }
    }
}
