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
package com.github.kumaraman21.intellijbehave.language;

import com.github.kumaraman21.intellijbehave.highlighter.StorySyntaxHighlighter;
import com.intellij.lang.CompositeLanguage;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class StoryLanguage extends CompositeLanguage {
    public static final StoryLanguage STORY_LANGUAGE = new StoryLanguage();

    private StoryLanguage() {
        super("Story", "text/story");
        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this,
                new SingleLazyInstanceSyntaxHighlighterFactory() {
                    @NotNull
                    protected SyntaxHighlighter createHighlighter() {
                        return new StorySyntaxHighlighter();
                    }
                });
    }
}
