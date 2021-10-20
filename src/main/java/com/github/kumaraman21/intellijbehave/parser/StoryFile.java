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

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;
import static com.github.kumaraman21.intellijbehave.parser.StoryElementType.*;

public class StoryFile extends PsiFileBase {

    public StoryFile(FileViewProvider fileViewProvider) {
        super(fileViewProvider, STORY_FILE_TYPE.getLanguage());
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return STORY_FILE_TYPE;
    }

    @NotNull
    public List<JBehaveStep> getSteps() {

        return getScenarios().stream()
                .map(scenario -> scenario.getNode().getChildren(STEPS_TOKEN_SET))
                .flatMap(Stream::of)
                .map(astNode -> (JBehaveStep) astNode.getPsi())
                .collect(Collectors.toList());
    }

    @NotNull
    private List<PsiElement> getScenarios() {
        PsiElement story = getStory();
        if (story == null) {
            return new ArrayList<>();
        }

        ASTNode[] scenarioNodes = story.getNode().getChildren(TokenSet.create(SCENARIO));
        return Stream.of(scenarioNodes).map(ASTNode::getPsi).collect(Collectors.toList());
    }

    private PsiElement getStory() {
        ASTNode[] storyNodes = this.getNode().getChildren(TokenSet.create(STORY));
        return storyNodes.length > 0 ? storyNodes[0].getPsi() : null;
    }
}
