package com.github.kumaraman21.intellijbehave.structureView;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.parser.JBehaveFile;
import com.github.kumaraman21.intellijbehave.parser.Scenario;
import com.github.kumaraman21.intellijbehave.psi.JBehaveGivenStories;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaElement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaStatement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveNarrative;
import com.intellij.icons.AllIcons;
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
import java.util.List;

/**
 * Created by DeBritoD on 03.04.2015.
 */
public class StructureViewElement implements StructureViewTreeElement {
    private NavigatablePsiElement element;

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
            presentation = new ItemPresentation() {
                @Nullable
                @Override
                public String getPresentableText() {
                    String text = "";

                    try {
                        if (element instanceof JBehaveMetaStatement) {
                            List<JBehaveMetaElement> elements = ((JBehaveMetaStatement) element).getMetaElementList();
                            if (!elements.isEmpty()) {
                                text = elements.get(0).getText();
                            } else text = "";
                        } else if (element instanceof JBehaveNarrative)
                            text = ((JBehaveNarrative) element).getNarrativeText().getText();
                        else if (element instanceof JBehaveGivenStories)
                            text = ((JBehaveGivenStories) element).getStoryPaths().getText();
                        text = text.replace("\n", " ");
                        if (text.length() > 40) {
                            text = text.substring(0, 40) + "...";
                        }
                    } catch (NullPointerException e) {
                        text = "";
                    }
                    return text;
                }

                @Nullable
                @Override
                public String getLocationString() {
                    return null;
                }

                @Nullable
                @Override
                public Icon getIcon(boolean unused) {
                    if (element instanceof JBehaveMetaStatement) return JBehaveIcons.META;
                    if (element instanceof JBehaveNarrative) return JBehaveIcons.NARRATIVE;
                    if (element instanceof JBehaveGivenStories) return JBehaveIcons.GIVEN_STORIES;
                    return AllIcons.Hierarchy.Base;
                }
            };
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
        return ContainerUtil.map2Array(structureViewChildren, TreeElement.class,
                new Function<PsiElement, TreeElement>() {
                    @Override
                    public TreeElement fun(PsiElement psiElement) {
                        return new StructureViewElement((NavigatablePsiElement) psiElement);
                    }
                });
    }

}
