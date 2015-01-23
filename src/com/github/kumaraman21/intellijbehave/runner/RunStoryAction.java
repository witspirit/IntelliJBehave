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
package com.github.kumaraman21.intellijbehave.runner;

import com.github.kumaraman21.intellijbehave.settings.JBehaveSettings;
import com.intellij.execution.*;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.runner.StoryRunnerConfigurationType.JBEHAVE_STORY_RUNNER;
import static com.intellij.openapi.ui.Messages.getErrorIcon;
import static com.intellij.openapi.ui.Messages.showMessageDialog;
import static org.apache.commons.lang.StringUtils.isBlank;

public class RunStoryAction extends AnAction {

    public void actionPerformed(@NotNull AnActionEvent e) {
        Application application = ApplicationManager.getApplication();
        JBehaveSettings component = application.getComponent(JBehaveSettings.class);

        String storyRunnerName = component.getStoryRunner();
        if (isBlank(storyRunnerName)) {
            showMessageDialog("In order to run a story file you need to first set a main class in the JBehave settings.",
                    "No Main Class Found to Run the Story",
                    getErrorIcon());
            return;
        }

        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (file == null) {
            showMessageDialog("Select a file or focus on the story file in the editor to run it.",
                    "Story File not Selected",
                    getErrorIcon());
            return;
        }

        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) return;
        final PsiClass storyRunnerClass = JavaPsiFacade.getInstance(project).findClass(storyRunnerName,
                GlobalSearchScope.allScope(project));
        if (storyRunnerClass == null) {
            showMessageDialog("Could not find the specified main class ''" + storyRunnerName + "'.",
                    "Main Class not Found",
                    getErrorIcon());
            return;
        }
        Module module = ProjectRootManager.getInstance(project).getFileIndex()
                .getModuleForFile(storyRunnerClass.getContainingFile().getVirtualFile());
        if (module == null) {
            showMessageDialog("Could not find the module in which main class to run stories was defined.'" +
                            "/n Resetting the main class in the JBehave settings might fix this issue.",
                    "Module not Found For Main Class",
                    getErrorIcon()
            );
            return;
        }
        RunManagerImpl runManager = (RunManagerImpl) RunManager.getInstance(project);
        RunnerAndConfigurationSettingsImpl runnerAndConfigurationSettings = findConfigurationByName(JBEHAVE_STORY_RUNNER, runManager);
        ApplicationConfiguration conf;

        if (runnerAndConfigurationSettings != null) {
            conf = (ApplicationConfiguration) runnerAndConfigurationSettings.getConfiguration();
            updateConfiguration(storyRunnerName, file, module, conf);
        } else {
            StoryRunnerConfigurationType type = application.getComponent(StoryRunnerConfigurationType.class);
            runnerAndConfigurationSettings =
                    (RunnerAndConfigurationSettingsImpl) runManager.createRunConfiguration(JBEHAVE_STORY_RUNNER, type.getConfigurationFactories()[0]);
            conf = (ApplicationConfiguration) runnerAndConfigurationSettings.getConfiguration();
            updateConfiguration(storyRunnerName, file, module, conf);
            runManager.addConfiguration(runnerAndConfigurationSettings, true);
        }

        runManager.setSelectedConfiguration(runnerAndConfigurationSettings);

        Executor executor = DefaultRunExecutor.getRunExecutorInstance();
        ProgramRunner runner = RunnerRegistry.getInstance().getRunner(executor.getId(), conf);

        if (runner != null) {
            ExecutionEnvironment environment = new ExecutionEnvironment(executor, runner, runnerAndConfigurationSettings, project);
            try {
                runner.execute(environment);
            } catch (ExecutionException e1) {
                JavaExecutionUtil.showExecutionErrorMessage(e1, "Error", project);
            }
        }
    }

    private RunnerAndConfigurationSettingsImpl findConfigurationByName(String name, RunManagerImpl runManager) {
        for (RunnerAndConfigurationSettings settings : runManager.getSortedConfigurations()) {
            if (settings.getName().equals(name)) return (RunnerAndConfigurationSettingsImpl) settings;
        }
        return null;
    }

    private void updateConfiguration(String mainClassName, VirtualFile file, Module module, ApplicationConfiguration conf) {
        conf.setMainClassName(mainClassName);
        conf.setProgramParameters(file.getPath());
        conf.setModule(module);
    }
}
