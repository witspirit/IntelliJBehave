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
import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StoryPath extends ParserRule {

    private final Set<PsiFile> myFiles = new TreeSet<PsiFile>();

    public StoryPath(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    private String unwrapInjects(String text) {
        List<ParametrizedString.ContentToken> contentTokens = ParametrizedString.splitOnInject(text);
        StringBuilder sb = new StringBuilder();
        for (ParametrizedString.ContentToken token : contentTokens) {
            sb.append(ParametrizedString.unwrapInject(token.value()));
        }
        return sb.toString();
    }

    public String getValue() {
        String text = unwrapInjects(getNode().getText().trim().replace('\\', '/'));
        int i = text.lastIndexOf(',');
        if (i >= 0) {
            return text.substring(0, i);
        }
        return text;
    }

    private String getFileName() {
        String[] fileNames = getFileNames();
        return fileNames[fileNames.length - 1];
    }

    private String[] getFileNames() {
        String text = getValue();
        String[] split = text.split("/");
        int lastIdx = split.length - 1;
        split[lastIdx] = split[lastIdx].trim();
        return split;
    }

    private boolean isMyFilesValid() {
        if (myFiles.isEmpty()) return false;
        for (PsiFile psiFile : myFiles) {
            if (!psiFile.isValid() || !hasSameFileNamePart(psiFile)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasSameFileNamePart(PsiFile psiFile) {
        String psiFileName = psiFile.getVirtualFile().getCanonicalPath();
        return psiFileName != null && psiFileName.endsWith(getValue());
    }

    public PsiFile[] getFiles() {
        if (!isMyFilesValid()) {
            myFiles.clear();
            Project project = getProject();
            GlobalSearchScope scopeRestrictedByFileTypes = GlobalSearchScope
                    .getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(project),
                                                   JBehaveFileType.JBEHAVE_FILE_TYPE);
            PsiFile[] filesByName = FilenameIndex.getFilesByName(project, getFileName(), scopeRestrictedByFileTypes);
            for (final PsiFile psiFile : filesByName) {
                if (hasSameFileNamePart(psiFile)) myFiles.add(psiFile);
            }
        }
        return myFiles.toArray(new PsiFile[myFiles.size()]);
    }

    @Nullable
    public PsiDirectory getStoriesRootDirectories(PsiFile file) {
        try {
            String myRoot = new File(getText()).toPath().getName(0).toString();

            PsiDirectory parent = file.getOriginalFile().getParent();
            while (parent != null && !parent.getName().equals(myRoot)) {
                parent = parent.getParent();
            }
            return parent != null ? parent.getParent() : null;
        } catch (InvalidPathException ignored) {

        }
        return null;
    }

    @Override
    public void annotate(AnnotationHolder annotationHolder) {
        PsiReference[] references = getReferences();
        if (references.length != 1 || !(references[0] instanceof StoryPathPsiReference)) {
            return;
        }
        StoryPathPsiReference reference = (StoryPathPsiReference) references[0];

        if (reference.multiResolve(false).length == 0) {
            PsiElement parent1 = getParent();
            if (parent1 != null) {
                PsiElement parent = parent1.getParent();
                if (parent != null && parent.getNode().getElementType() == IJBehaveElementType.JB_GIVEN_STORIES) {
                    Annotation errorAnnotation = annotationHolder.createErrorAnnotation(this, "File not found");
                    errorAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_ERROR_FILE_NOT_FOUND);
                }
            }
        } else {
            Annotation infoAnnotation = annotationHolder.createInfoAnnotation(this, null);
            infoAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_STORY_PATH);
        }

    }
}
