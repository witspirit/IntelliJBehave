package com.github.kumaraman21.intellijbehave.codeStyle;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

/**
 * Created by DeBritoD on 30.03.2015.
 */
public class JBehaveCodeStyleSettings extends CustomCodeStyleSettings {
    public int INDENT_DEFAULT = 0;
    //
    public int INDENT_STORY_PATH = 14;
    public int INDENT_META_ELEMENT = 6;
    //
    public int SPACE_KEYWORD_AFTER = 1;
    //
    public int DESCRIPTION_LINEFEED = 2;
    public boolean DESCRIPTION_KEEPLINEBREAKS = false;
    public int DESCRIPTION_KEEPEMPTYLINES = 0;
    //
    public int META_LINEFEED = 2;
    public boolean META_KEEPLINEBREAKS = false;
    public int META_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_META_LINEFEED = 0;
    public boolean TOKEN_META_KEEPLINEBREAKS = false;
    public int TOKEN_META_KEEPEMPTYLINES = 0;
    //
    public int NARRATIVE_LINEFEED = 2;
    public boolean NARRATIVE_KEEPLINEBREAKS = false;
    public int NARRATIVE_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_NARRATIVE_LINEFEED = 1;
    public boolean TOKEN_NARRATIVE_KEEPLINEBREAKS = false;
    public int TOKEN_NARRATIVE_KEEPEMPTYLINES = 0;
    //
    public int GIVENSTORIES_LINEFEED = 2;
    public boolean GIVENSTORIES_KEEPLINEBREAKS = false;
    public int GIVENSTORIES_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_GIVENSTORIES_LINEFEED = 1;
    public boolean TOKEN_GIVENSTORIES_KEEPLINEBREAKS = false;
    public int TOKEN_GIVENSTORIES_KEEPEMPTYLINES = 0;
    //
    public int LIFECYCLE_LINEFEED = 2;
    public boolean LIFECYCLE_KEEPLINEBREAKS = false;
    public int LIFECYCLE_KEEPEMPTYLINES = 0;
    //
    public int LIFECYCLE_AFTER_LINEFEED = 2;
    public boolean LIFECYCLE_AFTER_KEEPLINEBREAKS = false;
    public int LIFECYCLE_AFTER_KEEPEMPTYLINES = 0;
    //
    public int LIFECYCLE_BEFORE_LINEFEED = 2;
    public boolean LIFECYCLE_BEFORE_KEEPLINEBREAKS = false;
    public int LIFECYCLE_BEFORE_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_LIFECYCLE_LINEFEED = 1;
    public boolean TOKEN_LIFECYCLE_KEEPLINEBREAKS = false;
    public int TOKEN_LIFECYCLE_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_LIFECYCLE_AFTER_LINEFEED = 1;
    public boolean TOKEN_LIFECYCLE_AFTER_KEEPLINEBREAKS = false;
    public int TOKEN_LIFECYCLE_AFTER_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_LIFECYCLE_BEFORE_LINEFEED = 1;
    public boolean TOKEN_LIFECYCLE_BEFORE_KEEPLINEBREAKS = false;
    public int TOKEN_LIFECYCLE_BEFORE_KEEPEMPTYLINES = 0;
    //
    public int SCENARIO_LINEFEED = 2;
    public boolean SCENARIO_KEEPLINEBREAKS = false;
    public int SCENARIO_KEEPEMPTYLINES = 0;
    //
    public int TOKEN_SCENARIO_LINEFEED = 0;
    public boolean TOKEN_SCENARIO_KEEPLINEBREAKS = false;
    public int TOKEN_SCENARIO_KEEPEMPTYLINES = 0;
    //
    public int SCENARIO_TITLE_LINEFEED = 1;
    public boolean SCENARIO_TITLE_KEEPLINEBREAKS = false;
    public int SCENARIO_TITLE_KEEPEMPTYLINES = 0;

    protected JBehaveCodeStyleSettings(CodeStyleSettings settings) {
        super("JBehave", settings);
    }
}
