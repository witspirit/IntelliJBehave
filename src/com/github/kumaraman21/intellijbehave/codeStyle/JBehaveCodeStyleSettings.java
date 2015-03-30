package com.github.kumaraman21.intellijbehave.codeStyle;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

/**
 * Created by DeBritoD on 30.03.2015.
 */
public class JBehaveCodeStyleSettings extends CustomCodeStyleSettings {
    protected JBehaveCodeStyleSettings(CodeStyleSettings container) {
        super("JBehave", container);
    }

}
