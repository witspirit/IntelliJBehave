package com.github.kumaraman21.intellijbehave.structure;

import static com.intellij.util.containers.ContainerUtil.map;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.icons.AllIcons;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import javax.swing.*;

/**
 * Functional test for {@link JBehaveStructureViewElement}.
 */
public class JBehaveStructureViewElementTest extends BasePlatformTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/structureview";
    }

    // Story

    public void testStoryItemIcon() {
        PsiFile psiFile = myFixture.configureByText("a_story.story", "<caret>Narrative: this is the narrative");

        Icon icon = new JBehaveStructureViewElement(getStoryElement(psiFile)).getIcon(false);

        assertThat(icon).isSameAs(JBehaveIcons.JB);
    }

    public void testStoryItemWithFullText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "<caret>Narrative: this is the narrative\n" +
                "\n" +
                "Scenario: a scenario");

        String storyText = new JBehaveStructureViewElement(getStoryElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Story");
    }

    public void testStoryItemWithTruncatedText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "<caret>Narrative: this is the narrative\n" +
                "with a second row of description\n" +
                "and with a third row of description as well\n" +
                "\n" +
                "Scenario: a scenario");

        String storyText = new JBehaveStructureViewElement(getStoryElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Story");

    }

    public void testStoryItemWithDefaultText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story", "<caret>Scenario: a scenario");

        String storyText = new JBehaveStructureViewElement(getStoryElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Story");
    }

    private ASTWrapperPsiElement getStoryElement(PsiFile psiFile) {
        return (ASTWrapperPsiElement) psiFile.findElementAt(myFixture.getCaretOffset()).getParent().getParent();
    }

    // Lifecycle steps excluded

    public void testStoryLLifecycleSteps() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "<caret>Narrative: this is the narrative\n" +
                "Lifecycle:\n" +
                "Before:\n" +
                "Scope: STORY\n" +
                "Given a step that is executed before each story\n" +
                "Scope: SCENARIO\n" +
                "Given a step that is executed before each scenario\n" +
                "Scenario: a scenario");

        var children = map(
            new JBehaveStructureViewElement(getStoryElement(psiFile)).getChildrenBase(),
            StructureViewTreeElement::getValue
        );

        assertThat(children).doesNotHaveAnyElementsOfTypes(JBehaveStep.class);
    }

    // Scenario

    public void testScenarioItemIcon() {
        PsiFile psiFile = myFixture.configureByText("a_story.story", "<caret>Scenario: a scenario");

        Icon icon = new JBehaveStructureViewElement(getElement(psiFile)).getIcon(false);

        assertThat(icon).isSameAs(AllIcons.Nodes.LogFolder);
    }

    public void testScenarioItemWithFullText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story", "<caret>Scenario: a scenario");

        String storyText = new JBehaveStructureViewElement(getElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Scenario: a scenario");
    }

    public void testScenarioItemWithTruncatedText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "<caret>Scenario: a scenario with a long long long and still long title");

        String storyText = new JBehaveStructureViewElement(getElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Scenario: a scenario with a long long long and sti...");

    }

    public void testScenarioItemWithDefaultText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story", "<caret>Scenario:");

        String storyText = new JBehaveStructureViewElement(getElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Scenario");
    }

    private ASTWrapperPsiElement getElement(PsiFile psiFile) {
        return (ASTWrapperPsiElement) psiFile.findElementAt(myFixture.getCaretOffset()).getParent();
    }

    // Step

    public void testStepItemIcon() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "Scenario: a scenario\n" +
                "<caret>Given some precondition");

        Icon icon = new JBehaveStructureViewElement(getElement(psiFile)).getIcon(false);

        assertThat(icon).isSameAs(AllIcons.Nodes.Static);
    }

    public void testStepItemText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "Scenario: a scenario\n" +
                "<caret>Given some precondition");

        String storyText = new JBehaveStructureViewElement(getElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Given: some precondition");
    }

    // Examples

    public void testExamplesItemIcon() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "Scenario: a scenario\n" +
                "Given some precondition\n" +
                "<caret>Examples:\n" +
                "| some | thing |");

        Icon icon = new JBehaveStructureViewElement(getElement(psiFile)).getIcon(false);

        assertThat(icon).isNull();
    }

    public void testExamplesItemText() {
        PsiFile psiFile = myFixture.configureByText("a_story.story",
            "Scenario: a scenario\n" +
                "Given some precondition\n" +
                "<caret>Examples:\n" +
                "| some | thing |");

        String storyText = new JBehaveStructureViewElement(getElement(psiFile)).getPresentableText();

        assertThat(storyText).isEqualTo("Examples");
    }
}
