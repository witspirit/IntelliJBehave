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
    public LineFeeds LINEFEED_META = new LineFeeds(0, false, 0);

    protected JBehaveCodeStyleSettings(CodeStyleSettings settings) {
        super("JBehave", settings);
        settings.OTHER_INDENT_OPTIONS.CONTINUATION_INDENT_SIZE = 0;
        settings.OTHER_INDENT_OPTIONS.INDENT_SIZE = 0;
        settings.OTHER_INDENT_OPTIONS.TAB_SIZE = 0;
    }

    public static class LineFeeds {
        public int LINEFEED = 0;
        public boolean KEEPLINEBREAKS = false;
        public int KEEPEMPTYLINES = 0;

        public LineFeeds(int KEEPEMPTYLINES, boolean KEEPLINEBREAKS, int LINEFEED) {
            this.KEEPEMPTYLINES = KEEPEMPTYLINES;
            this.KEEPLINEBREAKS = KEEPLINEBREAKS;
            this.LINEFEED = LINEFEED;
        }
    }
}
