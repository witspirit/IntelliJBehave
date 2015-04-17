package com.github.kumaraman21.intellijbehave.codeStyle;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

/**
 * Created by DeBritoD on 30.03.2015.
 */
public class JBehaveCodeStyleSettings extends CustomCodeStyleSettings {
    public int INDENT_STORY_PATH = 14;
    public int INDENT_META_ELEMENT = 6;
    public int INDENT_DEFAULT = 0;
    //
    public int SPACE_KEYWORD_AFTER = 1;
    //
    public int META_LINEFEED = 0;
    public boolean META_KEEPLINEBREAKS = false;
    public int META_KEEPEMPTYLINES = 0;

    protected JBehaveCodeStyleSettings(CodeStyleSettings settings) {
        super("JBehave", settings);
    }
}
