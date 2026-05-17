package com.github.kumaraman21.intellijbehave.execution;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.execution.PsiLocation;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiDirectory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.intellij.openapi.application.ReadAction.compute;

public class JBehaveRunConfigurationTest extends ContentEntryTestBase {

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/runner/runconfiguration";
    }

    @BeforeEach
    public void setup() {
        copySrcDirectoryToProject();
    }

    @Test
    public void testCreateRunConfigurationOnFolderWithStories() {
        getFixture().configureByFile("test/resources/Stories/test.story");

        var location = compute(() -> {
            PsiDirectory directory = getFixture().getFile().getParent();
            return new PsiLocation<>(Objects.requireNonNull(directory));
        });

        var context = ConfigurationContext.createEmptyContextForLocation(location);
        var configuration = createConfiguration();
        var producer = new JBehaveRunConfigurationProducer();

        var actual = compute(() -> producer.setupConfigurationFromContext(configuration, context, new Ref<>()));
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    public void testCreateRunConfigurationOnStoryFile() {
        getFixture().configureByFile("test/resources/Stories/test.story");
        var location = compute(() -> new PsiLocation<>(getFixture().getFile()));

        var context = ConfigurationContext.createEmptyContextForLocation(location);
        var configuration = createConfiguration();
        var producer = new JBehaveRunConfigurationProducer();

        var actual = compute(() -> producer.setupConfigurationFromContext(configuration, context, new Ref<>()));
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    public void testCreateRunConfigurationOnStory() {
        getFixture().configureByFile("test/resources/Stories/test.story");
        var location = compute(() -> new PsiLocation<>(getFixture().getFile().getFirstChild()));

        var context = compute(() -> ConfigurationContext.createEmptyContextForLocation(location));
        var configuration = createConfiguration();
        var producer = new JBehaveRunConfigurationProducer();

        var actual = compute(() -> producer.setupConfigurationFromContext(configuration, context, new Ref<>()));
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    public void testNotCreateRunConfigurationOnFolderWithoutStories() {
        getFixture().configureByFile("test/resources/Files/test.txt");
        var location = compute(() -> new PsiLocation<>(getFixture().getFile()));

        var context = ConfigurationContext.createEmptyContextForLocation(location);
        var configuration = createConfiguration();
        var producer = new JBehaveRunConfigurationProducer();

        var actual = compute(() -> producer.setupConfigurationFromContext(configuration, context, new Ref<>()));
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    public void testNotCrateRunConfigurationOnNonStoryFile() {
        getFixture().configureByFile("test/resources/Files/test.txt");
        var location = compute(() -> new PsiLocation<>(getFixture().getFile()));

        var context = ConfigurationContext.createEmptyContextForLocation(location);
        var configuration = createConfiguration();
        var producer = new JBehaveRunConfigurationProducer();

        var actual = compute(() -> producer.setupConfigurationFromContext(configuration, context, new Ref<>()));
        Assertions.assertThat(actual).isFalse();
    }

    private JBehaveRunConfiguration createConfiguration() {
        Project project = getFixture().getProject();
        ConfigurationFactory factory = JBehaveConfigurationType.getInstance().getConfigurationFactories()[0];
        return new JBehaveRunConfiguration("Test", project, factory);
    }
}
