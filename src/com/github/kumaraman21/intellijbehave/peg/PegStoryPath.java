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
package com.github.kumaraman21.intellijbehave.peg;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;

public class PegStoryPath extends ASTWrapperPsiElement {

    public PegStoryPath(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    public String getValue() {
        String text = getNode().getText().trim();
        int i = text.lastIndexOf(',');
        if (i >= 0) {
            return text.substring(0, i);
        }
        return text;
    }

    public String getFileName() {
        String[] fileNames = getFileNames();
        return fileNames[fileNames.length - 1];
    }

    public String[] getFileNames() {
        String text = getValue();
        String[] split = text.split("/");
        int lastIdx = split.length - 1;
        split[lastIdx] = split[lastIdx].trim();
        return split;
    }

    private Set<PsiFile> myFiles = new TreeSet<PsiFile>();

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
            GlobalSearchScope scopeRestrictedByFileTypes = GlobalSearchScope.getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(project), StoryFileType.STORY_FILE_TYPE);
            PsiFile[] filesByName = FilenameIndex.getFilesByName(project, getFileName(), scopeRestrictedByFileTypes);
            for (final PsiFile psiFile : filesByName) {
                if (hasSameFileNamePart(psiFile))
                    myFiles.add(psiFile);
            }
        }
        return myFiles.toArray(new PsiFile[myFiles.size()]);
    }

    private boolean hasStory(PsiDirectory aDir) {
        PsiFile[] files = aDir.getFiles();
        if (files.length == 0) return true;
        for (PsiFile file : files) {
            if (file.getFileType() == StoryFileType.STORY_FILE_TYPE)
                return true;
        }
        return false;
    }

    private static final Key<Boolean> hasStories = new Key<Boolean>("hasStories");

    private boolean hasDirTreeOnlyStories(PsiDirectory aDir) {
        Boolean userData = aDir.getUserData(hasStories);
        if (userData != null) {
            return userData;
        }
        PsiDirectory[] subdirectories = aDir.getSubdirectories();
        userData = hasStory(aDir);
        if (userData) {
            for (PsiDirectory subDir : subdirectories) {
                userData = hasDirTreeOnlyStories(subDir);
                if (!userData) {
                    break;
                }
            }
        }
        aDir.putUserData(hasStories, userData);
        return userData;
    }

    @Nullable
    private PsiDirectory getBiggestStoryTree(PsiDirectory start) {
        if (hasDirTreeOnlyStories(start)) {
            PsiDirectory parentDirectory = start.getParentDirectory();
            PsiDirectory tree = getBiggestStoryTree(parentDirectory);
            if (tree != null)
                return getBiggestStoryTree(tree);
            else
                return start;
        }
        return null;
    }

    @Nullable
    public PsiDirectory getStoriesRootDirectories(PsiFile file) {
        return getBiggestStoryTree(file.getOriginalFile().getParent());
    }

}
