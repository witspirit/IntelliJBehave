package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.codeStyle.JBehaveCodeStyleSettings;
import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.github.kumaraman21.intellijbehave.parser.JBehaveParserDefinition;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryFormattingBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        final JBehaveCodeStyleSettings jbehaveSettings = settings.getCustomSettings(JBehaveCodeStyleSettings.class);
        PsiFile file = element.getContainingFile();
        SpacingBuilder spaceBuilder = createSpaceBuilder(jbehaveSettings);
        IndentingMappings indents = createIndents(jbehaveSettings);
        StoryBlock storyBlock = new StoryBlock(element.getNode(), jbehaveSettings, spaceBuilder, indents);
        return FormattingModelProvider.createFormattingModelForPsiFile(file, storyBlock, settings);
    }

    private static void spacingTokens(IElementType token, IElementType within, int space, SpacingBuilder spacingBuilder,
                                      int lf, boolean keepLineBreaks, int keepEmptyLines) {
        if (keepLineBreaks) {
            spacingBuilder.afterInside(token, within).spacing(space, space, lf + 1, false, keepEmptyLines);
        } else {
            spacingBuilder.afterInside(token, within).spacing(space, space, 0, false, keepEmptyLines);
        }
    }

    private static SpacingBuilder createSpaceBuilder(JBehaveCodeStyleSettings settings) {
        SpacingBuilder spacingBuilder =
                new SpacingBuilder(settings.getContainer(), JBehaveFileType.JBEHAVE_FILE_TYPE.getLanguage());
        IFileElementType root = JBehaveParserDefinition.STORY_FILE;
        spacingTokens(JB_DESCRIPTION, root, settings.SPACE_KEYWORD_AFTER, spacingBuilder, settings.DESCRIPTION_LINEFEED,
                      settings.DESCRIPTION_KEEPLINEBREAKS, settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_META_STATEMENT, root, settings.SPACE_KEYWORD_AFTER, spacingBuilder, settings.META_LINEFEED,
                      settings.META_KEEPLINEBREAKS, settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_NARRATIVE, root, settings.SPACE_KEYWORD_AFTER, spacingBuilder, settings.NARRATIVE_LINEFEED,
                      settings.NARRATIVE_KEEPLINEBREAKS, settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_GIVEN_STORIES, root, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.GIVENSTORIES_LINEFEED, settings.GIVENSTORIES_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_LIFECYCLE, root, settings.SPACE_KEYWORD_AFTER, spacingBuilder, settings.LIFECYCLE_LINEFEED,
                      settings.LIFECYCLE_KEEPLINEBREAKS, settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_LIFECYCLE_AFTER, JB_LIFECYCLE, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.LIFECYCLE_AFTER_LINEFEED, settings.LIFECYCLE_AFTER_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_LIFECYCLE_BEFORE, JB_LIFECYCLE, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.LIFECYCLE_BEFORE_LINEFEED, settings.LIFECYCLE_BEFORE_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_SCENARIO, root, settings.SPACE_KEYWORD_AFTER, spacingBuilder, settings.SCENARIO_LINEFEED,
                      settings.SCENARIO_KEEPLINEBREAKS, settings.ALL_KEEPEMPTYLINES);
        //
        //
        //
        spacingTokens(JB_TOKEN_META, JB_META_STATEMENT, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_META_LINEFEED, settings.TOKEN_META_KEEPLINEBREAKS, settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_TOKEN_NARRATIVE, JB_NARRATIVE, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_NARRATIVE_LINEFEED, settings.TOKEN_NARRATIVE_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_TOKEN_GIVEN_STORIES, JB_GIVEN_STORIES, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_GIVENSTORIES_LINEFEED, settings.TOKEN_GIVENSTORIES_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_TOKEN_LIFECYCLE, JB_LIFECYCLE, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_LIFECYCLE_LINEFEED, settings.TOKEN_LIFECYCLE_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_TOKEN_AFTER, JB_LIFECYCLE, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_LIFECYCLE_AFTER_LINEFEED, settings.TOKEN_LIFECYCLE_AFTER_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_TOKEN_BEFORE, JB_LIFECYCLE, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_LIFECYCLE_BEFORE_LINEFEED, settings.TOKEN_LIFECYCLE_BEFORE_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_TOKEN_SCENARIO, JB_SCENARIO, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.TOKEN_SCENARIO_LINEFEED, settings.TOKEN_SCENARIO_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingTokens(JB_SCENARIO_TITLE, JB_SCENARIO, settings.SPACE_KEYWORD_AFTER, spacingBuilder,
                      settings.SCENARIO_TITLE_LINEFEED, settings.SCENARIO_TITLE_KEEPLINEBREAKS,
                      settings.ALL_KEEPEMPTYLINES);
        //
        spacingBuilder.afterInside(JB_STORY_PATH, JB_STORY_PATHS).spacing(0, 0, 0, false, 0);
        spacingBuilder.afterInside(JB_TOKEN_COMMA, JB_STORY_PATHS).spacing(0, 0, 1, true, 0);
        //
        spacingBuilder.beforeInside(JB_STEP, JB_SCENARIO).spacing(0, 0, 1, true, 1);
        spacingBuilder.afterInside(JB_STEP, JB_SCENARIO).spacing(0, 0, 1, true, 1);
        spacingBuilder.beforeInside(JB_EXAMPLES, JB_SCENARIO).spacing(0, 0, 2, false, 0);
        //
        spacingBuilder.after(TokenSet.create(JB_STEP_PAR, JB_TOKEN_GIVEN_STORIES))
                      .spacing(settings.SPACE_KEYWORD_AFTER, settings.SPACE_KEYWORD_AFTER, 0, false, 0);
        spacingBuilder.before(JB_STORY_PATHS).spacing(1, 1, 0, false, 0);
        spacingBuilder.before(JB_TABLE).spacing(0, 0, 1, false, 0);
        //
        spacingBuilder.after(JB_TOKEN_COMMENT).spacing(0, 0, 0, true, 20);
        spacingBuilder.before(JB_TOKEN_COMMENT).spacing(0, 0, 0, true, 20);
        return spacingBuilder;
    }

    private static IndentingMappings createIndents(JBehaveCodeStyleSettings settings) {
        IndentingMappings indentingMappings = new IndentingMappings(settings.INDENT_DEFAULT);
        if (settings.ALIGN_META_KEY) indentingMappings.putIndent(JB_META_ELEMENT, settings.INDENT_META_ELEMENT);
        if (settings.ALIGN_STORY_PATH) indentingMappings.putIndent(JB_STORY_PATH, settings.INDENT_STORY_PATH);
        return indentingMappings;
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
