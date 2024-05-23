package com.github.kumaraman21.intellijbehave.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.junit5.RunInEdt;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link StepPsiReferenceContributor}.
 */
@RunInEdt
class StepPsiReferenceContributorTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/reference/storystep";
    }

    @Test
    void shouldProviderReferenceForStep() {
        var storyFile = getFixture().configureByFile("test/resources/step_reference.story");
        getFixture().copyFileToProject("main/java/StepDefs.java");
        var step = storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        PsiReference[] references = step.getReferences();
        assertThat(references).hasSize(1);
        assertThat(references[0])
            .isInstanceOf(StepPsiReference.class)
            .extracting(ref -> ref.resolve().getText())
            .isEqualTo("""
                @When("search for $string")
                    public void searchForText(@Named("string") String string) {
                    }""");
    }

    @Test
    void shouldNotProvideReferenceForNonStepElement() {
        var storyFile = getFixture().configureByFile("test/resources/non_step_reference.story");
        getFixture().copyFileToProject("main/java/StepDefs.java");
        var step = storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        assertThat(step.getReferences()).isEmpty();
    }
}
