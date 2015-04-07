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

import com.intellij.codeInsight.completion.CompletionUtilCore;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JBehavePathPsiReference implements PsiPolyVariantReference {
    private final JBehaveStoryPath myElement;
    private final TextRange myRange;

    public JBehavePathPsiReference(@NotNull JBehaveStoryPath element, @NotNull TextRange range) {
        myElement = element;
        myRange = range;
    }

    @Override
    public JBehaveStoryPath getElement() {
        return myElement;
    }

    @Override
    public TextRange getRangeInElement() {
        return myRange;
    }

    @Override
    public PsiElement resolve() {
        ResolveResult[] result = multiResolve(true);
        return result.length == 1 ? result[0].getElement() : null;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return myElement.getText();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return myElement;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return myElement;
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        ResolveResult[] resolvedResults = multiResolve(false);

        for (ResolveResult resolveResult : resolvedResults) {
            if (getElement().getManager().areElementsEquivalent(resolveResult.getElement(), element)) {
                return true;
            }
        }

        return false;
    }

    private List<PsiFile> getAllFiles(PsiDirectory dir) {
        final List<PsiFile> result = new ArrayList<PsiFile>();
        Collections.addAll(result, dir.getFiles());
        for (PsiDirectory directory : dir.getSubdirectories()) {
            result.addAll(getAllFiles(directory));
        }
        return result;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        PsiFile file = myElement.getContainingFile();

        final List<PsiFile> storyFiles = new ArrayList<PsiFile>();
        final PsiDirectory storyRootDir = myElement.getStoriesRootDirectories(file);
        if (storyRootDir != null) {
            storyFiles.addAll(getAllFiles(storyRootDir));
            final String matcher = myElement.getValue().replace(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED, "");
            final String storyRoot = String.format("%s/", storyRootDir.getVirtualFile().getCanonicalPath());
            final List<String> variants = new ArrayList<String>(storyFiles.size());
            for (PsiFile psiFile : storyFiles) {
                final String fullFileName = psiFile.getVirtualFile().getCanonicalPath();
                if (fullFileName != null && fullFileName.lastIndexOf(storyRoot) >= 0) {
                    final String capedFileName = fullFileName.substring(storyRoot.length());
                    if (capedFileName.startsWith(matcher)) {
                        variants.add(capedFileName);
                    }
                }

            }
            return variants.toArray(new String[variants.size()]);
        }
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<ResolveResult> result = new ArrayList<ResolveResult>();
        for (final PsiFile psiFile : myElement.getFiles()) {
            result.add(new ResolveResult() {
                public PsiElement getElement() {
                    return psiFile;
                }

                public boolean isValidResult() {
                    return true;
                }
            });
        }
        return result.toArray(new ResolveResult[result.size()]);
    }
}
