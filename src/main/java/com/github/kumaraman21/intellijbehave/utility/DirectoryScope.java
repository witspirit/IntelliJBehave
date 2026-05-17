package com.github.kumaraman21.intellijbehave.utility;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

public class DirectoryScope extends GlobalSearchScope {

    private final VirtualFile directory;

    private DirectoryScope(Project project, VirtualFile directory) {
        super(project);
        this.directory = directory;
    }

    @Override
    public boolean isSearchInModuleContent(@NotNull Module module) {
        return true;
    }

    @Override
    public boolean isSearchInLibraries() {
        return false;
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        return VfsUtilCore.isAncestor(directory, file, false);
    }

    public static GlobalSearchScope directoryScope(@NotNull Project project, @NotNull VirtualFile directory) {
        Validate.isTrue(directory.isDirectory(), "Provided virtual file must be a directory");
        return new DirectoryScope(project, directory);
    }
}