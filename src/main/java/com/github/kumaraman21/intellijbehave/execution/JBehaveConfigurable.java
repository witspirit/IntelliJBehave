package com.github.kumaraman21.intellijbehave.execution;

import com.github.kumaraman21.intellijbehave.JBehaveBundle;
import com.github.kumaraman21.intellijbehave.service.JBehaveUtil;
import com.github.kumaraman21.intellijbehave.utility.PathUtils;
import com.intellij.application.options.ModuleDescriptionsComboBox;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ui.ClassBrowser;
import com.intellij.execution.ui.CommonJavaParametersPanel;
import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.execution.ui.DefaultJreSelector;
import com.intellij.execution.ui.JrePathEditor;
import com.intellij.execution.ui.ShortenCommandLineModeCombo;
import com.intellij.ide.util.ClassFilter;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.psi.JavaCodeFragment.VisibilityChecker;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.EditorTextFieldWithBrowseButton;
import com.intellij.ui.PanelWithAnchor;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.util.ui.UIUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Objects;

public class JBehaveConfigurable extends SettingsEditor<JBehaveRunConfiguration> implements PanelWithAnchor {

    private final JBPanel<?> myPanel;
    private final LabeledComponent<EditorTextFieldWithBrowseButton> myEmbedderClass;
    private final EditorTextFieldWithBrowseButton myEmbedderClassTextField;
    private final LabeledComponent<TextFieldWithBrowseButton> myStoryOrFolder;
    private final TextFieldWithBrowseButton myStoryOrFolderTextField;
    private final LabeledComponent<JBTextField> myMetaFilters;
    private final JBTextField myMetaFiltersTextField;
    private final LabeledComponent<JBTextField> myInclusions;
    private final JBTextField myInclusionsTextField;
    private final LabeledComponent<JBTextField> myExclusions;
    private final JBTextField myExclusionsTextField;
    private final LabeledComponent<EditorTextFieldWithBrowseButton> myStoryFinderClass;
    private final EditorTextFieldWithBrowseButton myStoryFinderClassTextField;
    private final ConfigurationModuleSelector myModuleSelector;
    private final LabeledComponent<ModuleDescriptionsComboBox> myModule;
    private final CommonJavaParametersPanel myProgramParameters;
    private final JrePathEditor myJrePathEditor;
    private final ShortenCommandLineModeCombo myShortenClasspathModeCombo;

    private JComponent myAnchor;

    public JBehaveConfigurable(Project project) {
        myPanel = new JBPanel<>();
        myPanel.setLayout(new VerticalLayout(5));

        VisibilityChecker embedderClassVisibilityChecker = (declaration, place) ->
            JBehaveUtil.isEmbedderClassOrEmbedderProvider(declaration)
            ? VisibilityChecker.Visibility.VISIBLE
            : VisibilityChecker.Visibility.NOT_VISIBLE;

        myEmbedderClassTextField = new EditorTextFieldWithBrowseButton(project, true, embedderClassVisibilityChecker);

        myEmbedderClass = new LabeledComponent<>();
        myEmbedderClass.setLabelLocation(BorderLayout.WEST);
        myEmbedderClass.setText(JBehaveBundle.message("configuration.label.embedder.class"));
        myEmbedderClass.setComponent(myEmbedderClassTextField);
        myPanel.add(myEmbedderClass);

        myStoryOrFolderTextField = new TextFieldWithBrowseButton();
        myStoryOrFolderTextField.addDocumentListener(new DocumentAdapter() {

            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                String storyOrFolder = myStoryOrFolderTextField.getText();
                if (StringUtils.isEmpty(storyOrFolder)) {
                    setEnableStoriesSectionFields(false);
                } else {
                    setEnableStoriesSectionFields(PathUtils.isDirectoryPath(storyOrFolder));
                }
            }
        });

        myStoryOrFolder = new LabeledComponent<>();
        myStoryOrFolder.setLabelLocation(BorderLayout.WEST);
        myStoryOrFolder.setText(JBehaveBundle.message("configuration.label.story.or.folder.path"));
        myStoryOrFolder.setComponent(myStoryOrFolderTextField);
        myPanel.add(myStoryOrFolder);

        myMetaFiltersTextField = new JBTextField();
        myMetaFiltersTextField.getEmptyText().setText(JBehaveBundle.message("configuration.placeholder.meta.filters"));

        myMetaFilters = new LabeledComponent<>();
        myMetaFilters.setLabelLocation(BorderLayout.WEST);
        myMetaFilters.setText(JBehaveBundle.message("configuration.label.meta.filters"));
        myMetaFilters.setComponent(myMetaFiltersTextField);
        myPanel.add(myMetaFilters);
        myPanel.add(new TitledSeparator(JBehaveBundle.message("configuration.label.stories.header")));

        myInclusionsTextField = new JBTextField();
        myInclusionsTextField.setEnabled(false);

        myInclusions = new LabeledComponent<>();
        myInclusions.setLabelLocation(BorderLayout.WEST);
        myInclusions.setText(JBehaveBundle.message("configuration.label.included.patterns"));
        myInclusions.setComponent(myInclusionsTextField);
        myPanel.add(myInclusions);

        myExclusionsTextField = new JBTextField();
        myExclusionsTextField.setEnabled(false);

        myExclusions = new LabeledComponent<>();
        myExclusions.setLabelLocation(BorderLayout.WEST);
        myExclusions.setText(JBehaveBundle.message("configuration.label.excluded.patterns"));
        myExclusions.setComponent(myExclusionsTextField);
        myPanel.add(myExclusions);

        VisibilityChecker storyFinderClassVisibilityChecker = (declaration, place) ->
            JBehaveUtil.isStoryFinderClass(declaration)
            ? VisibilityChecker.Visibility.VISIBLE
            : VisibilityChecker.Visibility.NOT_VISIBLE;

        myStoryFinderClassTextField =
            new EditorTextFieldWithBrowseButton(project, true, storyFinderClassVisibilityChecker);

        myStoryFinderClass = new LabeledComponent<>();
        myStoryFinderClass.setLabelLocation(BorderLayout.WEST);
        myStoryFinderClass.setText(JBehaveBundle.message("configuration.label.story.finder.class"));
        myStoryFinderClass.setComponent(myStoryFinderClassTextField);
        myStoryFinderClass.setEnabled(false);
        myPanel.add(myStoryFinderClass);
        myPanel.add(new TitledSeparator()); // Empty title separator is here because it contains intents

        myModule = new LabeledComponent<>();
        myModule.setComponent(new ModuleDescriptionsComboBox());
        myModule.setLabelLocation(BorderLayout.WEST);
        myModule.setText(ExecutionBundle.message("application.configuration.use.classpath.and.jdk.of.module.label"));

        ModuleDescriptionsComboBox moduleComponent = myModule.getComponent();
        myModuleSelector = new ConfigurationModuleSelector(project, moduleComponent);

        ClassBrowserBuilder.<EditorTextField>newBuilder(project)
            .moduleSelector(myModuleSelector)
            .classFilter(JBehaveUtil::isEmbedderClassOrEmbedderProvider)
            .title(JBehaveBundle.message("dialog.title.choose.embeddable.class"))
            .build()
            .setField(myEmbedderClass.getComponent());

        ClassBrowserBuilder.<EditorTextField>newBuilder(project)
            .moduleSelector(myModuleSelector)
            .classFilter(JBehaveUtil::isStoryFinderClass)
            .title(JBehaveBundle.message("dialog.title.choose.story.finder.class"))
            .build()
            .setField(myStoryFinderClass.getComponent());

        FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor();
        fileChooserDescriptor.setTitle(JBehaveBundle.message("configuration.select.folder.or.story"));
        fileChooserDescriptor.putUserData(LangDataKeys.MODULE_CONTEXT, myModuleSelector.getModule());
        myStoryOrFolderTextField.addBrowseFolderListener(project, fileChooserDescriptor);

        myProgramParameters = new CommonJavaParametersPanel();
        myPanel.add(myProgramParameters);

        myAnchor = createAnchor();

        myJrePathEditor = new JrePathEditor();
        myJrePathEditor.setDefaultJreSelector(DefaultJreSelector.fromModuleDependencies(moduleComponent, false));
        myJrePathEditor.setAnchor(myModule.getLabel());

        myShortenClasspathModeCombo = new ShortenCommandLineModeCombo(project, myJrePathEditor, moduleComponent);

        LabeledComponent<ShortenCommandLineModeCombo> myShortenClasspathMode = new LabeledComponent<>();
        myShortenClasspathMode.setLabelLocation(BorderLayout.WEST);
        myShortenClasspathMode.setText(ExecutionBundle.message("application.configuration.shorten.command.line.label"));
        myShortenClasspathMode.setComponent(myShortenClasspathModeCombo);
        myShortenClasspathMode.setAnchor(myModule.getLabel());

        myPanel.add(myModule);
        myPanel.add(myJrePathEditor);
        myPanel.add(myShortenClasspathMode);
    }

    private void setEnableStoriesSectionFields(boolean shouldEnable) {
        myInclusions.setEnabled(shouldEnable);
        myExclusions.setEnabled(shouldEnable);
        myStoryFinderClass.setEnabled(shouldEnable);
    }

    @Nullable
    private JComponent createAnchor() {
        return UIUtil.mergeComponentsWithAnchor(myEmbedderClass,
            myStoryOrFolder,
            myMetaFilters,
            myInclusions,
            myExclusions,
            myStoryFinderClass,
            myModule,
            myProgramParameters);
    }

    @Override
    public @Nullable JComponent getAnchor() {
        return myAnchor;
    }

    @Override
    public void setAnchor(@Nullable JComponent anchor) {
        myAnchor = anchor;
        myEmbedderClass.setAnchor(anchor);
        myMetaFilters.setAnchor(anchor);
        myStoryOrFolder.setAnchor(anchor);
        myInclusions.setAnchor(anchor);
        myExclusions.setAnchor(anchor);
        myStoryFinderClass.setAnchor(anchor);
        myModule.setAnchor(anchor);
        myProgramParameters.setAnchor(anchor);
    }

    @Override
    protected void resetEditorFrom(@NotNull JBehaveRunConfiguration configuration) {
        myModuleSelector.reset(configuration);
        myProgramParameters.reset(configuration);

        myEmbedderClassTextField.setText(configuration.computeEmbedderClass());
        myStoryOrFolderTextField.setText(configuration.getFilePath());
        myMetaFiltersTextField.setText(configuration.getMetaFilters());
        myInclusionsTextField.setText(configuration.getInclusions());
        myExclusionsTextField.setText(configuration.getExclusions());
        myStoryFinderClassTextField.setText(configuration.getStoryFinderClass());
        myJrePathEditor.setPathOrName(configuration.getAlternativeJrePath(),
            configuration.isAlternativeJrePathEnabled());
        myShortenClasspathModeCombo.setSelectedItem(configuration.getShortenCommandLine());
    }

    @Override
    protected void applyEditorTo(@NotNull JBehaveRunConfiguration configuration) {
        myModuleSelector.applyTo(configuration);
        myProgramParameters.applyTo(configuration);

        configuration.setEmbedderClass(myEmbedderClassTextField.getText());
        configuration.setFilePath(myStoryOrFolderTextField.getText());
        configuration.setMetaFilters(myMetaFiltersTextField.getText());
        configuration.setInclusions(myInclusionsTextField.getText());
        configuration.setExclusions(myExclusionsTextField.getText());
        configuration.setStoryFinderClass(myStoryFinderClassTextField.getText());
        configuration.setAlternativeJrePath(myJrePathEditor.getJrePathOrName());
        configuration.setAlternativeJrePathEnabled(myJrePathEditor.isAlternativeJreSelected());
        configuration.setShortenCommandLine(myShortenClasspathModeCombo.getSelectedItem());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return myPanel;
    }

    private static class ClassBrowserBuilder<T extends JComponent> {

        private final Project project;

        private ConfigurationModuleSelector moduleSelector;
        private ClassFilter classFilter;
        private String title;

        private ClassBrowserBuilder(Project project) {
            this.project = Objects.requireNonNull(project);
        }

        static <T extends JComponent> ClassBrowserBuilder<T> newBuilder(@NotNull Project project) {
            return new ClassBrowserBuilder<>(project);
        }

        ClassBrowserBuilder<T> moduleSelector(@NotNull ConfigurationModuleSelector moduleSelector) {
            this.moduleSelector = Objects.requireNonNull(moduleSelector);
            return this;
        }

        ClassBrowserBuilder<T> classFilter(@NotNull ClassFilter classFilter) {
            this.classFilter = Objects.requireNonNull(classFilter);
            return this;
        }

        ClassBrowserBuilder<T> title(@NlsContexts.DialogTitle String title) {
            this.title = title;
            return this;
        }

        ClassBrowser<T> build() {
            return new ClassBrowser.MainClassBrowser<>(project, moduleSelector, title) {

                @Override
                protected ClassFilter createFilter(Module module) {
                    return classFilter;
                }
            };
        }
    }
}
