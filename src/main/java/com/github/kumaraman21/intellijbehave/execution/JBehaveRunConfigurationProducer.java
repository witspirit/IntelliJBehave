package com.github.kumaraman21.intellijbehave.execution;

import com.github.kumaraman21.intellijbehave.JBehaveBundle;
import com.github.kumaraman21.intellijbehave.service.JBehaveUtil;
import com.intellij.execution.JavaExecutionUtil;
import com.intellij.execution.JavaRunConfigurationExtensionManager;
import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.junit.JavaRunConfigurationProducerBase;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class JBehaveRunConfigurationProducer extends JavaRunConfigurationProducerBase<JBehaveRunConfiguration> {

    private static final Logger LOGGER = Logger.getInstance(JBehaveRunConfigurationProducer.class);

    @Override
    public @NotNull ConfigurationFactory getConfigurationFactory() {
        return JBehaveConfigurationType.getInstance().getConfigurationFactories()[0];
    }

    @Override
    protected boolean setupConfigurationFromContext(
        @NotNull JBehaveRunConfiguration configuration,
        @NotNull ConfigurationContext context,
        @NotNull Ref<PsiElement> sourceElement
    ) {
        VirtualFile file = getFileToRun(context);
        if (file == null) {
            LOGGER.warn("File to run not found");
            return false;
        }

        Module module = ModuleUtilCore.findModuleForFile(file, context.getProject());
        if (module == null) {
            LOGGER.warn("Module not found");
            return false;
        }

        Location<PsiElement> location = context.getLocation();
        if (location == null) {
            LOGGER.warn("Source code location is not found");
            return false;
        }

        // It is better to use lazy loading provider here instead of direct assign to speed up context menu opening
        configuration.setEmbedderClassProvider(getEmbedderClassNameProvider(module));
        configuration.setFilePath(file.getPath());
        configuration.setName(getConfigurationName(context, configuration.suggestedName()));
        setupConfigurationModule(context, configuration);

        JavaRunConfigurationExtensionManager.getInstance().extendCreatedConfiguration(configuration, location);
        return true;
    }

    @Nullable
    private static VirtualFile getFileToRun(ConfigurationContext context) {
        PsiElement element = context.getPsiLocation();
        if (element == null) {
            return null;
        }

        if (JBehaveUtil.isStory(element) || JBehaveUtil.isStoryFile(element)) {
            return element.getContainingFile().getVirtualFile();
        }

        if (JBehaveUtil.isDirectoryWithStories(element)) {
            return ((PsiDirectory) element).getVirtualFile();
        }
        return null;
    }

    private static NotNullLazyValue<String> getEmbedderClassNameProvider(Module module) {
        return NotNullLazyValue.lazy(() -> Optional.ofNullable(JBehaveUtil.findEmbedderClassOrEmbedderProvider(module))
            .map(psiClass -> ReadAction.compute(psiClass::getQualifiedName))
            .orElse(JBehaveConfigurationOptions.DEFAULT_EMBEDDER_CLASS_NAME));
    }

    private static String getConfigurationName(@NotNull ConfigurationContext context, @Nullable String defaultName) {
        PsiElement psiLocation = context.getPsiLocation();
        return switch (psiLocation) {
            case null -> defaultName;
            case PsiFile psiFile -> getConfigurationFileName(psiFile);
            case PsiDirectory psiDirectory -> getConfigurationDirectoryName(psiDirectory);
            default -> getConfigurationDefaultName(psiLocation, defaultName);
        };
    }

    private static String getConfigurationFileName(@NotNull PsiFile psiFile) {
        return psiFile.getVirtualFile().getNameWithoutExtension();
    }

    private static String getConfigurationDirectoryName(@NotNull PsiDirectory psiDirectory) {
        // Package name is preferable here to avoid configuration name collisions as much as possible
        PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(psiDirectory);
        String name = psiPackage == null ? psiDirectory.getName() : psiPackage.getQualifiedName();
        return JBehaveBundle.message("configuration.run.directory.name", name);
    }

    private static String getConfigurationDefaultName(@NotNull PsiElement element, @Nullable String defaultName) {
        PsiFile file = element.getContainingFile();
        return (file != null) ? file.getVirtualFile().getNameWithoutExtension() : defaultName;
    }

    @Override
    public boolean isConfigurationFromContext(
        @NotNull JBehaveRunConfiguration configuration,
        @NotNull ConfigurationContext context
    ) {
        Location<?> location = context.getLocation();
        if (location == null) {
            return false;
        }

        Location<?> classLocation = JavaExecutionUtil.stepIntoSingleClass(location);
        if (classLocation == null) {
            return false;
        }

        VirtualFile file = getFileToRun(context);
        if (file == null) {
            return false;
        }

        if (!Objects.equals(file.getPath(), configuration.getFilePath())) {
            return false;
        }

        Module module = configuration.getConfigurationModule().getModule();
        return Objects.equals(module, classLocation.getModule());
    }
}
