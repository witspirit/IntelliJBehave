package com.github.kumaraman21.intellijbehave.codeStyle;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

/**
 * Created by DeBritoD on 30.03.2015.
 */
public class JBehaveCodeStyleSettings extends CustomCodeStyleSettings {
    public final boolean ALIGN_META_KEY = true;
    public final boolean ALIGN_STORY_PATH = true;
    public final int INDENT_DEFAULT = 0;
    //
    public final int INDENT_STORY_PATH = 14;
    public final int INDENT_META_ELEMENT = 6;
    //
    public int INDENT_PARENTS = 0;
    public final int INDENT_LEAFS = 0;
    public final int INDENT_NARRATIVE_TEXT = 0;
    //
    public final int SPACE_KEYWORD_AFTER = 1;
    //
    public final int ALL_KEEPEMPTYLINES = 0;
    //
    public final int DESCRIPTION_LINEFEED = 1;
    public final boolean DESCRIPTION_KEEPLINEBREAKS = true;
    //public int DESCRIPTION_KEEPEMPTYLINES = 0;
    //
    public final int META_LINEFEED = 1;
    public final boolean META_KEEPLINEBREAKS = true;
    //public int META_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_META_LINEFEED = 0;
    public final boolean TOKEN_META_KEEPLINEBREAKS = false;
    //public int TOKEN_META_KEEPEMPTYLINES = 0;
    //
    public final int NARRATIVE_LINEFEED = 1;
    public final boolean NARRATIVE_KEEPLINEBREAKS = true;
    //public int NARRATIVE_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_NARRATIVE_LINEFEED = 0;
    public final boolean TOKEN_NARRATIVE_KEEPLINEBREAKS = true;
    //public int TOKEN_NARRATIVE_KEEPEMPTYLINES = 0;
    //
    public final int GIVENSTORIES_LINEFEED = 1;
    public final boolean GIVENSTORIES_KEEPLINEBREAKS = true;
    //public int GIVENSTORIES_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_GIVENSTORIES_LINEFEED = 0;
    public final boolean TOKEN_GIVENSTORIES_KEEPLINEBREAKS = false;
    //public int TOKEN_GIVENSTORIES_KEEPEMPTYLINES = 0;
    //
    public final int LIFECYCLE_LINEFEED = 1;
    public final boolean LIFECYCLE_KEEPLINEBREAKS = true;
    //public int LIFECYCLE_KEEPEMPTYLINES = 0;
    //
    public final int LIFECYCLE_AFTER_LINEFEED = 1;
    public final boolean LIFECYCLE_AFTER_KEEPLINEBREAKS = true;
    //public int LIFECYCLE_AFTER_KEEPEMPTYLINES = 0;
    //
    public final int LIFECYCLE_BEFORE_LINEFEED = 1;
    public final boolean LIFECYCLE_BEFORE_KEEPLINEBREAKS = true;
    //public int LIFECYCLE_BEFORE_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_LIFECYCLE_LINEFEED = 0;
    public final boolean TOKEN_LIFECYCLE_KEEPLINEBREAKS = true;
    //public int TOKEN_LIFECYCLE_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_LIFECYCLE_AFTER_LINEFEED = 0;
    public final boolean TOKEN_LIFECYCLE_AFTER_KEEPLINEBREAKS = true;
    //public int TOKEN_LIFECYCLE_AFTER_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_LIFECYCLE_BEFORE_LINEFEED = 0;
    public final boolean TOKEN_LIFECYCLE_BEFORE_KEEPLINEBREAKS = true;
    //public int TOKEN_LIFECYCLE_BEFORE_KEEPEMPTYLINES = 0;
    //
    public final int SCENARIO_LINEFEED = 1;
    public final boolean SCENARIO_KEEPLINEBREAKS = true;
    //public int SCENARIO_KEEPEMPTYLINES = 0;
    //
    public final int TOKEN_SCENARIO_LINEFEED = 0;
    public final boolean TOKEN_SCENARIO_KEEPLINEBREAKS = false;
    //public int TOKEN_SCENARIO_KEEPEMPTYLINES = 0;
    //
    public final int SCENARIO_TITLE_LINEFEED = 0;
    public final boolean SCENARIO_TITLE_KEEPLINEBREAKS = true;
    //public int SCENARIO_TITLE_KEEPEMPTYLINES = 0;
    //
    public final int FIRST_STEP_LINEFEED = 0;
    public final boolean FIRST_STEP_KEEPLINEBREAKS = true;
    //public int FIRST_STEP_KEEPEMPTYLINES = 0;
    //
    public final int GIVEN_STEP_LINEFEED = 1;
    public final boolean GIVEN_STEP_KEEPLINEBREAKS = true;
    //public int GIVEN_STEP_KEEPEMPTYLINES = 0;
    //
    public final int AND_STEP_LINEFEED = 0;
    public final boolean AND_STEP_KEEPLINEBREAKS = true;
    //public int AND_STEP_KEEPEMPTYLINES = 0;
    //
    public final int WHEN_STEP_LINEFEED = 0;
    public final boolean WHEN_STEP_KEEPLINEBREAKS = true;
    //public int WHEN_STEP_KEEPEMPTYLINES = 0;
    //
    public final int THEN_STEP_LINEFEED = 0;
    public final boolean THEN_STEP_KEEPLINEBREAKS = true;
    //public int THEN_STEP_KEEPEMPTYLINES = 0;

    protected JBehaveCodeStyleSettings(CodeStyleSettings settings) {
        super("JBehave", settings);
    }
}
