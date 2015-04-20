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

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.language.JBehaveFileType.JBEHAVE_FILE_TYPE;

public class JBehaveParserDefinition implements ParserDefinition {
    public static final TokenSet STEP_TYPES =
            TokenSet.create(IJBehaveElementType.JB_TOKEN_AND, IJBehaveElementType.JB_TOKEN_WHEN,
                            IJBehaveElementType.JB_TOKEN_THEN, IJBehaveElementType.JB_TOKEN_GIVEN);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new FlexAdapter(new _JBehaveLexer(null));
    }

    @Override
    public PsiParser createParser(Project project) {
        return new JBehaveParser();
    }

    public static final IFileElementType STORY_FILE = new IFileElementType(JBEHAVE_FILE_TYPE.getLanguage());

    @Override
    public IFileElementType getFileNodeType() {
        return STORY_FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        //return TokenSet.create(IStoryPegElementType.STORY_TOKEN_SPACE);
        return TokenSet.EMPTY;
        //return TokenSet.create(IJBehaveElementType.JB_TOKEN_SPACE, IJBehaveElementType.JB_TOKEN_NEWLINE);
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        //return TokenSet.create(IStoryPegElementType.STORY_TOKEN_COMMENT);
        //return TokenSet.create(IStoryPegElementType.STORY_STEP_COMMENT);
        return TokenSet.EMPTY;
        //return TokenSet.create(IJBehaveElementType.JB_TOKEN_COMMENT);
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
        //return TokenSet.create(IStoryPegElementType.STORY_TOKEN_WORD);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return IJBehaveElementType.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new JBehaveFile(fileViewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
