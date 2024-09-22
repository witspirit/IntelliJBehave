package com.github.kumaraman21.intellijbehave.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.openapi.project.Project;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

/**
 * Integration test for {@link StepDefinitionIterator}.
 */
class StepDefinitionIteratorTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/resolver/stepdefiterator";
    }

    @Test
    void processesEverything() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("main/kotlin/AnotherStepDefs.kt");
        getFixture().copyFileToProject("src/test/resources/iterator.story");
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");

        var iterator = new DummyIterator(StepType.GIVEN, getFixture().getProject(), __ -> true);
        boolean isProcessed = iterator.processFile(stepDefFile.getVirtualFile());

        assertThat(isProcessed).isTrue();
    }

    @Test
    void processesNothing() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("main/kotlin/AnotherStepDefs.kt");
        getFixture().copyFileToProject("src/test/resources/iterator.story");
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");

        var iterator = new DummyIterator(StepType.WHEN, getFixture().getProject(), __ -> false);
        boolean isProcessed = iterator.processFile(stepDefFile.getVirtualFile());

        assertThat(isProcessed).isFalse();
    }

    @Test
    void processesFiltered() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("main/kotlin/AnotherStepDefs.kt");
        getFixture().copyFileToProject("src/test/resources/iterator.story");
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");

        var iterator = new DummyIterator(StepType.THEN, getFixture().getProject(), ann -> ann.annotationText().contains("size"));
        boolean isProcessed = iterator.processFile(stepDefFile.getVirtualFile());

        assertThat(isProcessed).isTrue();
    }

    @Test
    void processesFilteredKotlin() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("main/java/StepDefs.java");
        getFixture().copyFileToProject("src/test/resources/iterator.story");
        var stepDefFile = getFixture().configureByFile("main/kotlin/AnotherStepDefs.kt");

        var iterator = new DummyIterator(StepType.THEN, getFixture().getProject(), ann -> ann.annotationText().contains("size"));
        boolean isProcessed = iterator.processFile(stepDefFile.getVirtualFile());

        assertThat(isProcessed).isFalse(); //False because there is a step that doesn't match the condition
    }

    private static final class DummyIterator extends StepDefinitionIterator {
        private final Predicate<StepDefinitionAnnotation> annotationPredicate;

        public DummyIterator(@Nullable StepType stepType, Project project, Predicate<StepDefinitionAnnotation> annotationPredicate) {
            super(stepType, project);
            this.annotationPredicate = annotationPredicate;
        }

        @Override
        public boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation) {
            return annotationPredicate.test(stepDefinitionAnnotation);
        }
    }
}
