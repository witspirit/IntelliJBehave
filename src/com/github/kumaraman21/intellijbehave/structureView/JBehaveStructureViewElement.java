package com.github.kumaraman21.intellijbehave.structureView;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.parser.StoryFile;
import com.github.kumaraman21.intellijbehave.peg.PegStoryScenario;
import com.github.kumaraman21.intellijbehave.psi.StoryGivenStories;
import com.github.kumaraman21.intellijbehave.psi.StoryMetaElement;
import com.github.kumaraman21.intellijbehave.psi.StoryMetaStatement;
import com.github.kumaraman21.intellijbehave.psi.StoryNarrative;
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
public class JBehaveStructureViewElement implements StructureViewTreeElement {
    private NavigatablePsiElement element;

    public JBehaveStructureViewElement(NavigatablePsiElement element) {
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
                        if (element instanceof StoryMetaStatement) {
                            List<StoryMetaElement> elements = ((StoryMetaStatement) element).getMetaElementList();
                            if (!elements.isEmpty()) {
                                text = elements.get(0).getText();
                            } else text = "";
                        } else if (element instanceof StoryNarrative)
                            text = ((StoryNarrative) element).getNarrativeText().getText();
                        else if (element instanceof StoryGivenStories)
                            text = ((StoryGivenStories) element).getStoryPaths().getText();
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
                    if (element instanceof StoryMetaStatement) return JBehaveIcons.META;
                    if (element instanceof StoryNarrative) return JBehaveIcons.NARRATIVE;
                    if (element instanceof StoryGivenStories) return JBehaveIcons.GIVEN_STORIES;
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
        if (element instanceof StoryFile) {
            structureViewChildren = ((StoryFile) element).getStructureViewChildren();
        } else if (element instanceof PegStoryScenario) {
            structureViewChildren = ((PegStoryScenario) element).getSteps();
        } else return EMPTY_ARRAY;
        return ContainerUtil.map2Array(structureViewChildren, TreeElement.class,
                new Function<PsiElement, TreeElement>() {
                    @Override
                    public TreeElement fun(PsiElement psiElement) {
                        return new JBehaveStructureViewElement((NavigatablePsiElement) psiElement);
                    }
                });
    }

}
