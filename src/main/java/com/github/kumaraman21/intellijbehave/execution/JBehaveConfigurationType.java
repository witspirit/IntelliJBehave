package com.github.kumaraman21.intellijbehave.execution;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.util.NotNullLazyValue;

public class JBehaveConfigurationType extends ConfigurationTypeBase {

    static final String ID = "JBehaveConfiguration";

    public JBehaveConfigurationType() {
        super(ID, "JBehave", null, NotNullLazyValue.createValue(() -> JBehaveIcons.JB));
        addFactory(new JBehaveConfigurationFactory(this));
    }

    public static JBehaveConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(JBehaveConfigurationType.class);
    }
}
