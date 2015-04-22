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

import com.github.kumaraman21.intellijbehave.highlighter.JBehaveSyntaxHighlighter;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.icons.AllIcons;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ParserRule extends ASTWrapperPsiElement implements JBehaveElement {
    public static final Key<Boolean> isStepParameter = Key.create("JBehave.IsStepParameter");

    public ParserRule(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return getText().replaceAll("\\s+", " ");
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return AllIcons.Toolwindows.Documentation;
            }
        };

    }

    public void annotate(AnnotationHolder annotationHolder) {
        TextAttributesKey textAttribute = JBehaveSyntaxHighlighter.getTextAttribute(getNode().getElementType());
        if (textAttribute != null) {
            annotationHolder.createInfoAnnotation(this, null).setTextAttributes(textAttribute);
        }
    }

}
