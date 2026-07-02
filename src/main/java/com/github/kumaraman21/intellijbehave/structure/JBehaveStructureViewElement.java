package com.github.kumaraman21.intellijbehave.structure;

import static com.intellij.util.containers.ContainerUtil.exists;

import com.github.kumaraman21.intellijbehave.highlighter.StoryTokenType;
import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.parser.StoryElementType;
import com.github.kumaraman21.intellijbehave.parser.StoryFile;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.icons.AllIcons;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents an item in the structure view for .story files created by {@link StoryStructureViewFactory}.
 * <p>
 * Supported language elements: Story file, Scenario, step, Examples table.
 * <p>
 * Lifecycle elements and steps specified within lifecycle scope are not supported for now due to lack of support on the Story language side.
 */
public class JBehaveStructureViewElement extends PsiTreeElementBase<PsiElement> {
    private static final Pattern NON_SCENARIO_DESCRIPTIONS = Pattern.compile("^(Narrative|Composite|Lifecycle):.*");

    public JBehaveStructureViewElement(PsiElement root) {
        super(root);
    }

    @Override
    public @NotNull Collection<StructureViewTreeElement> getChildrenBase() {
        List<StructureViewTreeElement> result = new ArrayList<>();
        for (PsiElement element : getElement().getChildren()) {
            if (element instanceof StoryFile
                //Lifecycle steps are not recognized properly by the Story language, thus excluding them for now
                || (element instanceof JBehaveStep && !isASTWrapperWithType(element.getParent(), StoryElementType.STORY))
                //Composite and Lifecycle Examples tables are excluded because such steps are also excluded
                || (isASTWrapperWithType(element, StoryElementType.EXAMPLES) && !isASTWrapperWithType(element.getParent(), StoryElementType.STORY))
                || isASTWrapperWithAnyType(element, StoryElementType.STORY, StoryElementType.SCENARIO)
                || (isASTWrapperWithAnyType(element, StoryElementType.STORY_DESCRIPTION) && NON_SCENARIO_DESCRIPTIONS.matcher(element.getText()).matches())) {
                result.add(new JBehaveStructureViewElement(element));
            }
        }
        return result;
    }

    @Override
    public Icon getIcon(boolean open) {
        final PsiElement element = getElement();
        if (element instanceof StoryFile || isASTWrapperWithType(element, StoryElementType.STORY))
            return JBehaveIcons.JB;
        if (isASTWrapperWithType(element, StoryElementType.STORY_DESCRIPTION)) {
            if (element.getText().startsWith("Narrative:"))
                return AllIcons.General.User;
            if (element.getText().startsWith("Composite:"))
                return AllIcons.Actions.ListFiles;
            if (element.getText().startsWith("Lifecycle:"))
                return AllIcons.Actions.Refresh;
        }
        if (isASTWrapperWithType(element, StoryElementType.SCENARIO))
            return AllIcons.Nodes.LogFolder;
        if (element instanceof JBehaveStep)
            return AllIcons.Nodes.Static;

        return null;
    }

    @Override
    public @NlsSafe @Nullable String getPresentableText() {
        PsiElement element = getElement();
        if (element instanceof StoryFile) return ((StoryFile) element).getName();

        if (element instanceof ASTWrapperPsiElement) {
            IElementType elementType = elementTypeOf(element);
            if (elementType == StoryElementType.STORY) return "Story";
            if (elementType == StoryElementType.STORY_DESCRIPTION) return getStoryDescriptionText(element);
            if (elementType == StoryElementType.SCENARIO) return getScenarioText(element);
            if (elementType == StoryElementType.EXAMPLES) return "Examples";
        }

        if (element instanceof JBehaveStep) return getStepText((JBehaveStep) element);

        return null;
    }

    //Text retrieval

    /**
     * Returns the text for the Story/Narrative element.
     * <p>
     * Lifecycle elements have to be filtered since they are too recognized as story description.
     */
    private String getStoryDescriptionText(PsiElement element) {
        if (element.getText().startsWith("Narrative:"))
            return "Narrative";
        if (element.getText().startsWith("Composite:"))
            return element.getText();
        if (element.getText().startsWith("Lifecycle:"))
            return "Lifecycle";

        return "";
    }

    /**
     * Returns the keyword and title of the scenario without any meta information.
     */
    private String getScenarioText(PsiElement element) {
        var scenarioTexts = new SmartList<String>();
        PsiElement sibling = element.getFirstChild();
        while ((sibling = PsiTreeUtil.findSiblingForward(sibling, StoryTokenType.SCENARIO_TEXT, null)) != null) {
            scenarioTexts.add(sibling.getText());
        }
        return !scenarioTexts.isEmpty() ? truncate("Scenario:" + String.join(" ", scenarioTexts), 50) : "Scenario";
    }

    /**
     * Returns the text for the step including the step type (Given, When, Then) as well.
     */
    @NotNull
    private String getStepText(JBehaveStep step) {
        return step.getActualStepPrefix() + ": " + step.getStepText();
    }

    /**
     * Returns the provided text if fits within the provided length, or truncates it at the provided length, appended with the ... postfix.
     */
    private String truncate(String text, int length) {
        return text.length() <= length ? text : text.substring(0, length) + "...";
    }

    //Elements

    private boolean isASTWrapperWithAnyType(PsiElement element, StoryElementType... types) {
        if (element instanceof ASTWrapperPsiElement) {
            IElementType elementType = elementTypeOf(element);
            return exists(types, type -> elementType == type);
        }
        return false;
    }

    private boolean isASTWrapperWithType(PsiElement element, StoryElementType type) {
        return element instanceof ASTWrapperPsiElement && elementTypeOf(element) == type;
    }

    private IElementType elementTypeOf(PsiElement element) {
        return ((ASTWrapperPsiElement) element).getNode().getElementType();
    }
}
