package com.github.kumaraman21.intellijbehave.utility;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.util.PathUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public final class PathUtils {

    public static String toUrlString(String path) {
        try {
            return new File(path).toURI().toURL().toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to convert file path to URL: " + path, e);
        }
    }

    public static String getJarPathForClassName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return PathUtil.getJarPathForClass(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to get class path elements for " + className, e);
        }
    }

    public static List<String> getModuleDependenciesPaths(Module module) {
        return OrderEnumerator.orderEntries(module)
            .recursively()
            .getPathsList()
            .getPathList();
    }

    public static List<String> getProjectDependenciesPaths(Project project) {
        return OrderEnumerator.orderEntries(project)
            .recursively()
            .getPathsList()
            .getPathList();
    }

    public static List<String> getJarPathsForClasses(Class<?>... classes) {
        return Arrays.stream(classes)
            .map(PathUtil::getJarPathForClass)
            .toList();
    }

    public static boolean isDirectoryPath(@Nullable String storyOrFolderPath) {
        if (StringUtils.isEmpty(storyOrFolderPath)) {
            return false;
        }
        Path path = Paths.get(storyOrFolderPath);
        return Files.isDirectory(path);
    }

    private PathUtils() {
        // Utility class, prevent instantiation
    }
}
