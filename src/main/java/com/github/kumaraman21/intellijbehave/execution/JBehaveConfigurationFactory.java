package com.github.kumaraman21.intellijbehave.execution;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JBehaveConfigurationFactory extends ConfigurationFactory {

    protected JBehaveConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return JBehaveConfigurationType.ID;
    }

    @Override
    public @Nullable Class<? extends BaseState> getOptionsClass() {
        return JBehaveConfigurationOptions.class;
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new JBehaveRunConfiguration("JBehave", project, this);
    }
}
