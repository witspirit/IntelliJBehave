package com.github.kumaraman21.intellijbehave.structureView;

import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 03.04.2015.
 */
public class JBehaveStructureViewModel extends StructureViewModelBase {
    public JBehaveStructureViewModel(@NotNull PsiFile psiFile, @Nullable Editor editor) {
        super(psiFile, editor, new JBehaveStructureViewElement(psiFile));
        withSorters(Sorter.ALPHA_SORTER);
        withSuitableClasses(StoryDescription.class, StoryScenario.class, StoryStep.class, StoryMetaStatement.class,
                StoryNarrative.class);
    }

}
