package com.github.kumaraman21.intellijbehave.ui;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.ui.EditorTextFieldWithBrowseButton;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.AsyncProcessIcon;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class LoadingEditorTextFieldWithBrowseButton extends JBPanel<LoadingEditorTextFieldWithBrowseButton> {

    private final EditorTextFieldWithBrowseButton delegate;
    private final AsyncProcessIcon loadingIcon;

    public LoadingEditorTextFieldWithBrowseButton(
        @NotNull Project project,
        boolean isClassAccepted,
        @NotNull JavaCodeFragment.VisibilityChecker visibilityChecker
    ) {
        super(new BorderLayout(4, 0));

        delegate = new EditorTextFieldWithBrowseButton(project, isClassAccepted, visibilityChecker);
        loadingIcon = new AsyncProcessIcon("loading");
        loadingIcon.setOpaque(false);
        loadingIcon.setVisible(false);
        loadingIcon.suspend();

        add(delegate, BorderLayout.CENTER);
        add(loadingIcon, BorderLayout.EAST);
    }

    public void setLoading(boolean loading) {
        loadingIcon.setVisible(loading);
        if (loading) {
            loadingIcon.resume();
        } else {
            loadingIcon.suspend();
        }
    }

    public void setText(String text) {
        delegate.setText(text);
    }

    public String getText() {
        return delegate.getText();
    }
}
