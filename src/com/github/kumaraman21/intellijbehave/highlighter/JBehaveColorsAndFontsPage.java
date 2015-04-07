package com.github.kumaraman21.intellijbehave.highlighter;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class JBehaveColorsAndFontsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{//
            new AttributesDescriptor("Story description", JBehaveSyntaxHighlighter.JB_DESCRIPTION),//
            new AttributesDescriptor("Narrative keyword", JBehaveSyntaxHighlighter.JB_TOKEN_NARRATIVE),//
            new AttributesDescriptor("Narrative text", JBehaveSyntaxHighlighter.JB_NARRATIVE_TEXT),//
            new AttributesDescriptor("Scenario keyword", JBehaveSyntaxHighlighter.JB_TOKEN_SCENARIO),//
            new AttributesDescriptor("Scenario text", JBehaveSyntaxHighlighter.JB_SCENARIO_TITLE),//
            new AttributesDescriptor("And keyword", JBehaveSyntaxHighlighter.JB_TOKEN_AND),//
            new AttributesDescriptor("Then keyword", JBehaveSyntaxHighlighter.JB_TOKEN_THEN),//
            new AttributesDescriptor("When keyword", JBehaveSyntaxHighlighter.JB_TOKEN_WHEN),//
            new AttributesDescriptor("Given keyword", JBehaveSyntaxHighlighter.JB_TOKEN_GIVEN),//
            new AttributesDescriptor("Step text", JBehaveSyntaxHighlighter.JB_STEP_LINE), //
            new AttributesDescriptor("Step parameter", JBehaveSyntaxHighlighter.JB_STEP_POST_PARAMETER), //
            new AttributesDescriptor("Table delimiter", JBehaveSyntaxHighlighter.JB_TOKEN_PIPE),//
            new AttributesDescriptor("Table cell", JBehaveSyntaxHighlighter.JB_TABLE_CELL),//
            new AttributesDescriptor("Meta keyword", JBehaveSyntaxHighlighter.JB_TOKEN_META),//
            new AttributesDescriptor("Meta key", JBehaveSyntaxHighlighter.JB_META_KEY),//
            new AttributesDescriptor("Meta value", JBehaveSyntaxHighlighter.JB_META_VALUE), //
            new AttributesDescriptor("Line comment", JBehaveSyntaxHighlighter.JB_TOKEN_COMMENT),//
            //new AttributesDescriptor("Bad Character", StorySyntaxHighlighter.BAD_CHARACTER),//
            new AttributesDescriptor("Given Stories", JBehaveSyntaxHighlighter.JB_TOKEN_GIVEN_STORIES),//
            new AttributesDescriptor("Story path", JBehaveSyntaxHighlighter.JB_TOKEN_PATH),//
            //new AttributesDescriptor("Story path2", StorySyntaxHighlighter.JB_JB_PATH),//
            new AttributesDescriptor("System parameter inject", JBehaveSyntaxHighlighter.JB_TOKEN_INJECT),//
            new AttributesDescriptor("User parameter inject", JBehaveSyntaxHighlighter.JB_TOKEN_USERINJECT),//
            new AttributesDescriptor("IP", JBehaveSyntaxHighlighter.JB_IP_ADDRESS),//
            new AttributesDescriptor("File not found", JBehaveSyntaxHighlighter.JB_ERROR_FILE_NOT_FOUND),//
            new AttributesDescriptor("Stepdefinition not found", JBehaveSyntaxHighlighter.JB_ERROR_NO_DEF_FOUND)//

    };

    @NotNull
    public String getDisplayName() {
        return "JBehave";
    }

    @Nullable
    public Icon getIcon() {
        return JBehaveIcons.JB;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new JBehaveSyntaxHighlighter();
    }

    @NonNls
    @NotNull
    public String getDemoText() {
        return "<description>This is a demo text for\n" + //
                "configuration of\n" + //
                "JBehave syntax highlighting</description>\n" + //
                "\n" + //
                "Narrative: \n" + //
                "<narrativeText>In order to play a game\n" + //
                "As a player\n" + //
                "I want to be able to create and manage my account\n</narrativeText>" + //
                "\n" + //
                "GivenStories: product/wire/ConfigStory.story, <errorFile>product/wire/Unknown.story</errorFile>\n\n" + //
                "Scenario: <scenText>An unknown user cannot be logged</scenText>\n" + //
                "\n" + //
                "Meta:\n" + //
                "<metaKey>@author</metaKey> <metaValue>mccallum</metaValue>\n" + //
                "<metaKey>@skip</metaKey>\n" + //
                "\n" + //
                "Given <stepText>i am the user with nickname: <weird></stepText>\n" + //
                "When <stepText>i try to login using the password <<soweird>></stepText>\n" + //
                "!-- TODO: Then i get an error message of type \"Wrong Credentials\"\n" + //
                "\n" + //
                "\n" + //
                "Scenario: <scenText>A known user cannot be logged using a wrong password</scenText>\n" + //
                "\n" + //
                "Given <stepText>the following existing users:</stepText>\n" + //
                "| <tableCell>nickname</tableCell> | <tableCell>password</tableCell> |\n" + //
                "|  <tableCell>Travis</tableCell>  |  <tableCell>PacMan</tableCell>  |\n" + //
                "Given <errorDef>i am the user with nickname: \"Travis\"</errorDef>\n" + //
                "When <stepText>i try to login using the password <stepParameter>McCallum</stepParameter></stepText>\n" + //
                "Then <stepText>i get an error message of type \"Wrong Credentials\"</stepText>";
    }

    @Nullable
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        Map<String, TextAttributesKey> result = new HashMap<String, TextAttributesKey>();
        result.put("scenText", JBehaveSyntaxHighlighter.JB_SCENARIO_TITLE);
        //result.put("storyPath", StorySyntaxHighlighter.STORY_STORY_PATH);
        result.put("stepText", JBehaveSyntaxHighlighter.JB_STEP_LINE);
        result.put("stepParameter", JBehaveSyntaxHighlighter.JB_STEP_POST_PARAMETER);
        result.put("tableCell", JBehaveSyntaxHighlighter.JB_TABLE_CELL);
        result.put("description", JBehaveSyntaxHighlighter.JB_DESCRIPTION);
        result.put("narrativeText", JBehaveSyntaxHighlighter.JB_NARRATIVE_TEXT);
        result.put("ipAdress", JBehaveSyntaxHighlighter.JB_IP_ADDRESS);
        result.put("metaKey", JBehaveSyntaxHighlighter.JB_META_KEY);
        result.put("metaValue", JBehaveSyntaxHighlighter.JB_META_VALUE);
        result.put("errorDef", JBehaveSyntaxHighlighter.JB_ERROR_NO_DEF_FOUND);
        result.put("errorFile", JBehaveSyntaxHighlighter.JB_ERROR_FILE_NOT_FOUND);
        return result;
    }
}
