package com.github.kumaraman21.intellijbehave.resolver;

import static com.intellij.openapi.application.ReadAction.compute;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link StepPsiReference}.
 */
class StepPsiReferenceTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/reference/steppsi";
    }

    //isReferenceTo

    @Test
    void shouldBeReferenceToPsiMethod() {
        copySrcDirectoryToProject();
        var storyFile = getFixture().configureByFile("test/resources/step_reference.story");
        getFixture().copyFileToProject("main/java/StepDefs.java");
        var step = getParentOfElementAtCaretIn(storyFile);

        PsiReference[] references = step.getReferences();
        assertThat(references).hasSize(1);

        var resolvedMethod = (PsiMethod) compute(() -> references[0].resolve());

        assertThat(references[0].isReferenceTo(resolvedMethod)).isTrue();
    }

    @Test
    void shouldNotBeReferenceToElement() {
        copySrcDirectoryToProject();
        var storyFile = getFixture().configureByFile("test/resources/step_reference.story");
        var stepDefVirtualFile = getFixture().copyFileToProject("main/java/StepDefs.java");
        var step = getParentOfElementAtCaretIn(storyFile);
        var stepDefFile = compute(() -> PsiManager.getInstance(getFixture().getProject()).findFile(stepDefVirtualFile));
        var notReferencedStepDefMethod = compute(() -> ((PsiJavaFile) stepDefFile).getClasses()[0].findMethodsByName("openAUrl", false)[0]);

        PsiReference[] references = step.getReferences();
        assertThat(references).hasSize(1);

        assertThat(references[0].isReferenceTo(notReferencedStepDefMethod)).isFalse();
    }
}
