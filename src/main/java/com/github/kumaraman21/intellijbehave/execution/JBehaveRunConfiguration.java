package com.github.kumaraman21.intellijbehave.execution;

import com.beust.jcommander.JCommander;
import com.github.kumaraman21.intellijbehave.JBehaveBundle;
import com.github.kumaraman21.intellijbehave.runner.Arguments;
import com.github.kumaraman21.intellijbehave.runner.JBehaveStoryRunner;
import com.github.kumaraman21.intellijbehave.utility.PathUtils;
import com.github.kumaraman21.intellijbehave.utility.ScanUtils;
import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.JavaRunConfigurationExtensionManager;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.JavaRunConfigurationModule;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.target.TargetEnvironmentConfigurations;
import com.intellij.execution.testframework.JavaTestLocator;
import com.intellij.execution.testframework.TestConsoleProperties;
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.execution.testframework.sm.runner.SMTestLocator;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.util.EnvFilesUtilKt;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.execution.util.ProgramParametersUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.Strings;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ui.UIUtil;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.serialization.PathMacroUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class JBehaveRunConfiguration extends ApplicationConfiguration {

    private Supplier<String> embedderClassProvider;

    protected JBehaveRunConfiguration(String name, @NotNull Project project, @NotNull ConfigurationFactory factory) {
        super(name, project, factory);
        setWorkingDirectory(PathMacroUtil.MODULE_WORKING_DIR);
    }

    @Override
    protected @NotNull JBehaveConfigurationOptions getOptions() {
        return (JBehaveConfigurationOptions) super.getOptions();
    }

    public String getEmbedderClass() {
        return getOptions().getEmbedderClass();
    }

    public void setEmbedderClass(String className) {
        getOptions().setEmbedderClass(className);
    }

    public void setEmbedderClassProvider(Supplier<String> embedderClassProvider) {
        this.embedderClassProvider = embedderClassProvider;
    }

    @Nullable
    public Supplier<String> getEmbedderClassProvider() {
        return embedderClassProvider;
    }

    @Nullable
    public String computeEmbedderClass() {
        String embedderClass = getEmbedderClass();
        if (Strings.isEmpty(embedderClass) || JBehaveConfigurationOptions.isDefaultEmbedderClass(embedderClass)) {
            // An attempt to re-compute embedder class name in case of null/empty or default value.
            // Re-computation result also may return default embedder and that is ok in this case.
            Supplier<String> provider = getEmbedderClassProvider();
            if (provider != null) {
                embedderClass = provider.get();
            }
        }
        getOptions().setEmbedderClass(embedderClass);
        return embedderClass;
    }

    public String getFilePath() {
        return getOptions().getFilePath();
    }

    public void setFilePath(String path) {
        getOptions().setFilePath(path);
    }

    public String getMetaFilters() {
        return getOptions().getMetaFilters();
    }

    public void setMetaFilters(String filters) {
        getOptions().setMetaFilters(filters);
    }

    public String getInclusions() {
        return getOptions().getInclusions();
    }

    public void setInclusions(String patterns) {
        getOptions().setInclusions(patterns);
    }

    public String getExclusions() {
        return getOptions().getExclusions();
    }

    public void setExclusions(String patterns) {
        getOptions().setExclusions(patterns);
    }

    public String getStoryFinderClass() {
        return getOptions().getStoryFinderClass();
    }

    public void setStoryFinderClass(String className) {
        getOptions().setStoryFinderClass(className);
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        String embedderClass = getEmbedderClass();
        if (Strings.isEmpty(embedderClass)) {
            throw new RuntimeConfigurationException("Embedder class is not specified");
        }

        String filePath = getFilePath();
        if (Strings.isEmpty(filePath)) {
            throw new RuntimeConfigurationException("Story or folder path must be specified");
        }

        if (TargetEnvironmentConfigurations.getEffectiveTargetName(this, getProject()) == null) {
            JavaParametersUtil.checkAlternativeJRE(this);
        }

        JavaRunConfigurationModule configurationModule = getConfigurationModule();
        ProgramParametersUtil.checkWorkingDirectoryExist(this, getProject(), configurationModule.getModule());
        EnvFilesUtilKt.checkEnvFiles(this);
        JavaRunConfigurationExtensionManager.checkConfigurationIsValid(this);
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        SettingsEditorGroup<JBehaveRunConfiguration> group = new SettingsEditorGroup<>();
        group.addEditor(ExecutionBundle.message("run.configuration.configuration.tab.title"),
            new JBehaveConfigurable(getProject()));
        JavaRunConfigurationExtensionManager.getInstance().appendEditors(this, group);
        group.addEditor(ExecutionBundle.message("logs.tab.title"), new LogConfigurationPanel<>());
        return group;
    }

    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) {
        return new JavaApplicationCommandLineState<>(this, env) {

            private static final String FRAMEWORK_NAME = "JBehave";

            @Override
            protected JavaParameters createJavaParameters() throws ExecutionException {
                JavaParameters params = new JavaParameters();
                ParametersList parametersList = params.getProgramParametersList();

                String embedderClass = Validate.notNull(computeEmbedderClass(),
                    JBehaveBundle.message("configuration.error.no.embedder.class"));
                parametersList.add(Arguments.EMBEDDER_CLASS_PARAMETER_NAME, embedderClass);

                String filePath = Validate.notNull(getFilePath(),
                    JBehaveBundle.message("configuration.error.no.story.or.folder.path"));
                parametersList.add(Arguments.STORY_PATH_PARAMETER_NAME, filePath);

                List<String> classPathElements = findClassPathElements(getProject(), embedderClass);
                parametersList.add(Arguments.CLASS_PATH_ELEMENTS_PARAMETER_NAME, Strings.join(classPathElements, ","));

                String metaFilters = getMetaFilters();
                parametersList.add(Arguments.META_FILTERS_PARAMETER_NAME, Strings.notNullize(metaFilters));

                String inclusions = getInclusions();
                parametersList.add(Arguments.INCLUDES_PARAMETER_NAME, Strings.notNullize(inclusions));

                String exclusions = getExclusions();
                parametersList.add(Arguments.EXCLUDES_PARAMETER_NAME, Strings.notNullize(exclusions));

                String storyFinderClass = getStoryFinderClass();
                parametersList.add(Arguments.STORY_FINDER_CLASS_PARAMETER_NAME, Strings.notNullize(storyFinderClass));

                params.setMainClass(getMainClassName());
                params.getClassPath().addAll(classPathElements);

                int classPathType = JavaParameters.JDK_AND_CLASSES_AND_TESTS;
                String jreHome = getJreHome();
                JavaRunConfigurationModule module = getConfigurationModule();
                JBehaveRunConfiguration runConfiguration = getConfiguration();
                RunnerSettings settings = getRunnerSettings();

                ReadAction.run(() -> {
                    JavaParametersUtil.configureModule(module, params, classPathType, jreHome);
                    JavaParametersUtil.configureConfiguration(params, runConfiguration);
                    JavaRunConfigurationExtensionManager.getInstance()
                        .updateJavaParameters(runConfiguration, params, settings, executor);
                });
                return params;
            }

            @Nullable
            private String getJreHome() {
                return getTargetEnvironmentRequest() == null && isAlternativeJrePathEnabled()
                       ? getAlternativeJrePath()
                       : null;
            }

            @Override
            public @NotNull ExecutionResult execute(@NotNull Executor executor, @NotNull ProgramRunner<?> runner)
                throws ExecutionException {
                ProcessHandler processHandler = startProcess();
                ConsoleView console = createConsole(executor, processHandler);
                AnAction[] actions = createActions(console, processHandler, executor);
                return new DefaultExecutionResult(console, processHandler, actions);
            }

            private @NotNull ConsoleView createConsole(@NotNull Executor executor, ProcessHandler processHandler) {
                JBehaveRunConfiguration configuration = getConfiguration();
                TestConsoleProperties properties = createConsoleProperties(configuration, FRAMEWORK_NAME, executor);
                ConsoleView console = createConsoleView(FRAMEWORK_NAME, properties);
                console.attachToProcess(processHandler);
                return console;
            }
        };
    }

    @Override
    public String getMainClassName() {
        return JBehaveStoryRunner.class.getName();
    }

    private static List<String> findClassPathElements(@NotNull Project project, @NotNull String embedderClassName) {
        Module module = ScanUtils.findModuleByClassName(project, embedderClassName);
        List<String> result = new ArrayList<>();
        if (module != null) {
            result.addAll(PathUtils.getModuleDependenciesPaths(module));
        } else {
            result.addAll(PathUtils.getProjectDependenciesPaths(project));
        }
        result.addAll(PathUtils.getJarPathsForClasses(JBehaveStoryRunner.class, JCommander.class));
        return result;
    }

    private static TestConsoleProperties createConsoleProperties(
        RunConfiguration config,
        String frameworkName,
        Executor executor
    ) {
        return new SMTRunnerConsoleProperties(config, frameworkName, executor) {
            @Override
            public @NotNull SMTestLocator getTestLocator() {
                return JavaTestLocator.INSTANCE;
            }
        };
    }

    private static ConsoleView createConsoleView(String frameworkName, TestConsoleProperties properties) {
        return UIUtil.invokeAndWaitIfNeeded(() -> SMTestRunnerConnectionUtil.createConsole(frameworkName, properties));
    }
}
