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
public class StoryColorsAndFontsPage implements ColorSettingsPage {

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

    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{//
            new AttributesDescriptor("Story description", StorySyntaxHighlighter.STORY_DESCRIPTION),//
            new AttributesDescriptor("Narrative keyword", StorySyntaxHighlighter.STORY_TOKEN_NARRATIVE),//
            new AttributesDescriptor("Narrative text", StorySyntaxHighlighter.STORY_NARRATIVE_TEXT),//
            new AttributesDescriptor("Scenario keyword", StorySyntaxHighlighter.STORY_TOKEN_SCENARIO),//
            new AttributesDescriptor("Scenario text", StorySyntaxHighlighter.STORY_SCENARIO_TITLE),//
            new AttributesDescriptor("And keyword", StorySyntaxHighlighter.STORY_TOKEN_AND),//
            new AttributesDescriptor("Then keyword", StorySyntaxHighlighter.STORY_TOKEN_THEN),//
            new AttributesDescriptor("When keyword", StorySyntaxHighlighter.STORY_TOKEN_WHEN),//
            new AttributesDescriptor("Given keyword", StorySyntaxHighlighter.STORY_TOKEN_GIVEN),//
            new AttributesDescriptor("Step text", StorySyntaxHighlighter.STORY_STEP_LINE), //
            new AttributesDescriptor("Table delimiter", StorySyntaxHighlighter.STORY_TOKEN_PIPE),//
            new AttributesDescriptor("Table cell", StorySyntaxHighlighter.STORY_TABLE_CELL),//
            new AttributesDescriptor("Meta keyword", StorySyntaxHighlighter.STORY_TOKEN_META),//
            new AttributesDescriptor("Meta key", StorySyntaxHighlighter.STORY_META_KEY),//
            new AttributesDescriptor("Meta text", StorySyntaxHighlighter.STORY_META_VALUE), //
            new AttributesDescriptor("Line comment", StorySyntaxHighlighter.STORY_TOKEN_COMMENT),//
            //new AttributesDescriptor("Bad Character", StorySyntaxHighlighter.BAD_CHARACTER),//
            new AttributesDescriptor("Given Stories", StorySyntaxHighlighter.STORY_TOKEN_GIVEN_STORIES),//
            new AttributesDescriptor("Story path", StorySyntaxHighlighter.STORY_TOKEN_PATH),//
            //new AttributesDescriptor("Story path2", StorySyntaxHighlighter.STORY_STORY_PATH),//
            new AttributesDescriptor("System parameter inject", StorySyntaxHighlighter.STORY_TOKEN_INJECT),//
            new AttributesDescriptor("User parameter inject", StorySyntaxHighlighter.STORY_TOKEN_USERINJECT),//
            new AttributesDescriptor("IP", StorySyntaxHighlighter.STORY_IP_ADDRESS)//
    };

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new StorySyntaxHighlighter();
    }

    @NonNls
    @NotNull
    public String getDemoText() {
        return  "<description>This is a demo text for\n" + //
                "configuration of\n" + //
                "JBehave syntax highlighting</description>\n" + //
                "\n" + //
                "Narrative: \n" + //
                "<narrativeText>In order to play a game\n" + //
                "As a player\n" + //
                "I want to be able to create and manage my account\n</narrativeText>" + //
                "\n" + //
                "GivenStories: product/wire/ConfigStory.story\n\n" + //
                "Scenario: <scenText>An unknown user cannot be logged</scenText>\n" + //
                "\n" + //
                "Meta:\n" + //
                "@author mccallum\n" + //
                "@skip\n" + //
                "\n" + //
                "Given <stepText>i am the user with nickname: <\"weird\"></stepText>\n" + //
                "When <stepText>i try to login using the password <<\"soweird\">></stepText>\n" + //
                "!-- TODO: Then i get an error message of type \"Wrong Credentials\"\n" + //
                "\n" + //
                "\n" + //
                "Scenario: <scenText>A known user cannot be logged using a wrong password</scenText>\n" + //
                "\n" + //
                "Given <stepText>the following existing users:</stepText>\n" + //
                "| <tableCell>nickname</tableCell> | <tableCell>password</tableCell> |\n" + //
                "|  <tableCell>Travis</tableCell>  |  <tableCell>PacMan</tableCell>  |\n" + //
                "Given <stepText>i am the user with nickname: \"Travis\"</stepText>\n" + //
                "When <stepText>i try to login using the password \"McCallum\"</stepText>\n" + //
                "Then <stepText>i get an error message of type \"Wrong Credentials\"</stepText>";
    }

    @Nullable
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        Map<String, TextAttributesKey> result = new HashMap<String, TextAttributesKey>();
        result.put("scenText", StorySyntaxHighlighter.STORY_SCENARIO_TITLE);
        //result.put("storyPath", StorySyntaxHighlighter.STORY_STORY_PATH);
        result.put("stepText", StorySyntaxHighlighter.STORY_STEP_LINE);
        result.put("tableCell", StorySyntaxHighlighter.STORY_TABLE_CELL);
        result.put("description", StorySyntaxHighlighter.STORY_DESCRIPTION);
        result.put("narrativeText", StorySyntaxHighlighter.STORY_NARRATIVE_TEXT);
        result.put("ipAdress", StorySyntaxHighlighter.STORY_IP_ADDRESS);
        return result;
    }
}
