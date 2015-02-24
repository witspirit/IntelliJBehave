/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.highlighter.StoryTokenType;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class StoryParser implements PsiParser {

    private static final boolean DEBUG = false;

    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();
        builder.setDebugMode(true);
        parseStory(builder);
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    @SuppressWarnings("UnnecessaryLabelOnContinueStatement")
    private void parseStory(PsiBuilder builder) {
        final PsiBuilder.Marker storyMarker = builder.mark();

        ParserState state = new ParserState(builder);

        whileLoop:
        while (!builder.eof()) {
            IElementType tokenType = builder.getTokenType();

            // Comment and whitespace are not returned by default

            if (isComment(tokenType)) {
                state.enterComment();
                builder.advanceLexer();
                continue whileLoop;
            } else {
                state.leaveComment();
            }

            if (isWhitespace(tokenType)) {
                if (isCrlf(builder.getTokenText())) {
                    // this is never called unfortunately
                    state.leaveTableRow();
                }

                state.enterWhitespace();
                builder.advanceLexer();
                continue whileLoop;
            } else {
                state.leaveWhitespace();
            }


            if (isStoryDescription(tokenType)) {
                state.enterStoryDescription();
                builder.advanceLexer();
                continue whileLoop;
            } else {
                state.leaveStoryDescription();
            }

            if (isScenario(tokenType)) {
                state.enterScenario();
                builder.advanceLexer();
                continue whileLoop;
            } else if (!belongsToScenario(tokenType)) {
                state.leaveScenario();
            }

            if (isScenarioText(tokenType)) {
                builder.advanceLexer();
                continue whileLoop;
            }

            if (isMeta(tokenType)) {
                state.enterMeta();
                builder.advanceLexer();
                continue whileLoop;
            }

            if (isGivenStories(tokenType)) {
                state.enterGivenStories();
                builder.advanceLexer();
                continue whileLoop;
            } else if (!belongsToGivenStories(tokenType)) {
                state.leaveGivenStories();
            }

            if (isGivenStoriesText(tokenType)) {
                state.enterGivenStoriesText();
                builder.advanceLexer();
                continue whileLoop;
            } else if (!belongsToGivenStoriesText(tokenType)) {
                state.leaveGivenStoriesText();
            }


            if (isStepType(tokenType)) {
                state.enterStepType(tokenType);
                builder.advanceLexer();
                continue whileLoop;
            }

            if (isStepText(tokenType)) {
                builder.advanceLexer();
                continue whileLoop;
            }

            if (isExampleTable(tokenType)) {
                state.enterExampleTable();
                builder.advanceLexer();
                continue whileLoop;
            } else if (!belongsToTable(tokenType)) {
                state.leaveExampleTable();
            }

            if (isTableRow(tokenType)) {
                state.enterTableRow();
                builder.advanceLexer();
                continue whileLoop;
            }

            // unknown
            PsiBuilder.Marker unknwonMark = builder.mark();
            builder.advanceLexer();
            unknwonMark.done(StoryElementType.UNKNOWN_FRAGMENT);
        }
        state.leaveRemainings();
        storyMarker.done(StoryElementType.STORY);
    }

    private static boolean isCrlf(String text) {
        return text.contains("\n") || text.contains("\r");
    }

    private static class MarkerData {
        private final PsiBuilder.Marker marker;
        private final IElementType elementType;

        private MarkerData(PsiBuilder.Marker marker, IElementType elementType) {
            this.marker = marker;
            this.elementType = elementType;
        }

        public boolean matches(IElementType elementType) {
            return this.elementType == elementType;
        }

        public boolean matches(TokenSet tokenSet) {
            return tokenSet.contains(this.elementType);
        }

        public void applyMark() {
            marker.done(elementType);
        }
    }

    private static class ParserState {
        private final PsiBuilder builder;
        private final MarkerData[] markers = new MarkerData[10];
        private int markerIndex = -1;
        private StoryElementType previousStepElementType = null;


        public ParserState(PsiBuilder builder) {
            this.builder = builder;
        }

        private void matchesHeadOrPush(StoryElementType elementType) {
            if (markerIndex >= 0 && markers[markerIndex].matches(elementType)) {
                return;
            }
            markers[++markerIndex] = new MarkerData(builder.mark(), elementType);
            if (DEBUG) {
                System.out.println("StoryParser$ParserState: PUSH>> " + StringUtils.repeat("..", markerIndex) + elementType);
            }
        }

        private void popUntilOnlyIfPresent(StoryElementType elementType) {
            int newMarkerIndex = markerIndex;
            for (int i = markerIndex; i >= 0; i--) {
                if (markers[i].matches(elementType)) {
                    newMarkerIndex = i - 1;
                    break;
                }
            }

            for (int i = newMarkerIndex + 1; i <= markerIndex; i++) {
                if (DEBUG) {
                    System.out.println("StoryParser$ParserState: POP >> " + StringUtils.repeat("..", i) + markers[i].elementType);
                }
                markers[i].applyMark();
            }

            markerIndex = newMarkerIndex;
        }

        public void leaveRemainings() {
            while (markerIndex >= 0) {
                if (DEBUG) {
                    System.out.println("StoryParser$ParserState: POP >> " + StringUtils.repeat("..", markerIndex) + markers[markerIndex].elementType);
                }
                markers[markerIndex--].applyMark();
            }
        }


        private void popUntilOnlyIfPresent(TokenSet tokenSet) {
            int newMarkerIndex = markerIndex;
            for (int i = markerIndex; i >= 0; i--) {
                if (markers[i].matches(tokenSet)) {
                    newMarkerIndex = i - 1;
                    break;
                }
            }

            for (int i = newMarkerIndex + 1; i <= markerIndex; i++) {
                if (DEBUG) {
                    System.out.println("StoryParser$ParserState: POP >> " + StringUtils.repeat("..", i) + markers[i].elementType);
                }
                markers[i].applyMark();
            }

            markerIndex = newMarkerIndex;
        }


        public void enterComment() {
            matchesHeadOrPush(StoryElementType.COMMENT);
        }

        private void leaveComment() {
            popUntilOnlyIfPresent(StoryElementType.COMMENT);
        }

        public void enterWhitespace() {
        }

        public void leaveWhitespace() {
        }

        public void enterStoryDescription() {
            leaveRemainings();
            matchesHeadOrPush(StoryElementType.STORY_DESCRIPTION);
        }

        private void leaveStoryDescription() {
            popUntilOnlyIfPresent(StoryElementType.STORY_DESCRIPTION);
        }

        public void enterScenario() {
            leaveRemainings();
            previousStepElementType = null;
            matchesHeadOrPush(StoryElementType.SCENARIO);
        }

        private void leaveScenario() {
            leaveRemainings();
            previousStepElementType = null;
            popUntilOnlyIfPresent(StoryElementType.SCENARIO);
        }

        public void enterMeta() {
            leaveStoryDescription();
            leaveExampleTable();
            leaveTableRow();
            leaveStep();
            matchesHeadOrPush(StoryElementType.META);
        }

        private void leaveMeta() {
            popUntilOnlyIfPresent(StoryElementType.META);
        }

        public void enterGivenStories() {
            leaveStoryDescription();
            leaveExampleTable();
            leaveTableRow();
            leaveStep();
            matchesHeadOrPush(StoryElementType.GIVEN_STORIES);
        }

        private void leaveGivenStories() {
            popUntilOnlyIfPresent(StoryElementType.GIVEN_STORIES);
        }

        public void enterGivenStoriesText() {
            leaveStoryDescription();
            leaveExampleTable();
            leaveTableRow();
            leaveStep();
            matchesHeadOrPush(StoryElementType.GIVEN_STORIES_TEXT);
        }

        private void leaveGivenStoriesText() {
            popUntilOnlyIfPresent(StoryElementType.GIVEN_STORIES_TEXT);
        }


        public void enterStepType(IElementType tokenType) {
            leaveGivenStoriesText();
            leaveGivenStories();
            leaveExampleTable();
            leaveMeta();
            leaveStep();
            leaveStoryDescription();
            leaveTableRow();

            StoryElementType elementType = previousStepElementType;
            if (tokenType == StoryTokenType.STEP_TYPE_GIVEN) {
                elementType = StoryElementType.GIVEN_STEP;
            } else if (tokenType == StoryTokenType.STEP_TYPE_WHEN) {
                elementType = StoryElementType.WHEN_STEP;
            } else if (tokenType == StoryTokenType.STEP_TYPE_THEN) {
                elementType = StoryElementType.THEN_STEP;
            }

            if (elementType == null) { // yuk!
                elementType = StoryElementType.GIVEN_STEP;
            }
            previousStepElementType = elementType;
            matchesHeadOrPush(elementType);
        }

        private void leaveStep() {
            leaveTableRow();
            popUntilOnlyIfPresent(StoryElementType.STEPS_TOKEN_SET);
        }

        public void enterExampleTable() {
            leaveTableRow();
            leaveStep();
            leaveMeta();
            leaveExampleTable();
            leaveGivenStoriesText();
            leaveGivenStories();
            matchesHeadOrPush(StoryElementType.EXAMPLES);
        }

        public void leaveExampleTable() {
            leaveTableRow();
            popUntilOnlyIfPresent(StoryElementType.EXAMPLES);
        }

        public void enterTableRow() {
            matchesHeadOrPush(StoryElementType.TABLE_ROW);
        }

        public void leaveTableRow() {
            popUntilOnlyIfPresent(StoryElementType.TABLE_ROW);
        }
    }

    private static boolean belongsToScenario(IElementType tokenType) {
        return isWhitespace(tokenType)
                || isComment(tokenType)
                || isScenarioText(tokenType)
                || isStoryDescription(tokenType)
                || isStepType(tokenType)
                || isStepText(tokenType)
                || isExampleTable(tokenType)
                || isTableRow(tokenType)
                || isMeta(tokenType)
                || isGivenStories(tokenType)
                ;
    }

    private boolean belongsToTable(IElementType tokenType) {
        return isWhitespace(tokenType)
                || isComment(tokenType)
                || isTableRow(tokenType);
    }

    private static boolean isMeta(IElementType tokenType) {
        return tokenType == StoryTokenType.META
                || tokenType == StoryTokenType.META_KEY
                || tokenType == StoryTokenType.META_TEXT;
    }

    private static boolean isGivenStories(IElementType tokenType) {
        return tokenType == StoryTokenType.GIVEN_STORIES_TYPE;
    }

    private static boolean isGivenStoriesText(IElementType tokenType) {
        return tokenType == StoryTokenType.STORY_PATH;
    }

    private static boolean belongsToGivenStories(IElementType tokenType) {
        return isWhitespace(tokenType)
                || isComment(tokenType)
                || isGivenStories(tokenType)
                || isGivenStoriesText(tokenType);
    }

    private static boolean belongsToGivenStoriesText(IElementType tokenType) {
        return isWhitespace(tokenType)
                || isComment(tokenType)
                || isGivenStoriesText(tokenType);
    }

    private static boolean isExampleTable(IElementType tokenType) {
        return tokenType == StoryTokenType.EXAMPLE_TYPE;
    }

    private static boolean isTableRow(IElementType tokenType) {
        return tokenType == StoryTokenType.TABLE_CELL
                || tokenType == StoryTokenType.TABLE_DELIM;
    }

    private static boolean isStepType(IElementType tokenType) {
        return tokenType == StoryTokenType.STEP_TYPE_GIVEN
                || tokenType == StoryTokenType.STEP_TYPE_WHEN
                || tokenType == StoryTokenType.STEP_TYPE_THEN
                || tokenType == StoryTokenType.STEP_TYPE_AND;
    }

    private static boolean isStepText(IElementType tokenType) {
        return tokenType == StoryTokenType.STEP_TEXT;
    }

    private static boolean isScenario(IElementType tokenType) {
        return tokenType == StoryTokenType.SCENARIO_TYPE;
    }

    private static boolean isScenarioText(IElementType tokenType) {
        return tokenType == StoryTokenType.SCENARIO_TEXT;
    }

    private static boolean isWhitespace(IElementType tokenType) {
        return tokenType == StoryTokenType.WHITE_SPACE;
    }

    private static boolean isComment(IElementType tokenType) {
        return tokenType == StoryTokenType.COMMENT
                || tokenType == StoryTokenType.COMMENT_WITH_LOCALE;
    }

    private static boolean isStoryDescription(IElementType tokenType) {
        return tokenType == StoryTokenType.STORY_DESCRIPTION
                || tokenType == StoryTokenType.NARRATIVE_TYPE
                || tokenType == StoryTokenType.NARRATIVE_TEXT;
    }
}
