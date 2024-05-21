package com.github.kumaraman21.intellijbehave.startup;

import com.github.kumaraman21.intellijbehave.JBehaveStepDefClassPsiChangeListener;
import com.github.kumaraman21.intellijbehave.service.JBehaveStepsIndex;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.psi.PsiManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Performs actions on project startup.
 */
public class ProjectStartupActivity implements ProjectActivity {

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        PsiManager.getInstance(project).addPsiTreeChangeListener(new JBehaveStepDefClassPsiChangeListener(project), JBehaveStepsIndex.getInstance(project));
        return Unit.INSTANCE;
    }
}
