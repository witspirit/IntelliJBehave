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
package com.github.kumaraman21.intellijbehave.utility;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public final class ScanUtils {

    public static boolean iterateInContextOf(PsiElement storyRef, ContentIterator iterator) {
        Module module = ModuleUtil.findModuleForPsiElement(storyRef);

        boolean shouldContinue = (module != null) && ModuleRootManager.getInstance(module).getFileIndex().iterateContent(iterator);

        if (shouldContinue) {
            HashSet<Module> dependencies = new HashSet<>();
            ModuleUtil.getDependencies(module, dependencies);

            for (Module dependency : dependencies) {
                shouldContinue = ModuleRootManager.getInstance(dependency).getFileIndex().iterateContent(iterator);
                if (!shouldContinue) {
                    break;
                }
            }
        }

        return shouldContinue;
    }


    @Nullable
    public static Module findModuleByClassName(@NotNull Project project, @NotNull String qualifiedName) {
        JavaPsiFacade facade = JavaPsiFacade.getInstance(project);
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        return ReadAction.compute(() -> {
            PsiClass psiClass = facade.findClass(qualifiedName, scope);
            return psiClass == null ? null : ModuleUtilCore.findModuleForPsiElement(psiClass);
        });
    }

    private ScanUtils() {
    }
}
