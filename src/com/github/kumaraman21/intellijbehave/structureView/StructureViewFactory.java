package com.github.kumaraman21.intellijbehave.structureView;

import com.github.kumaraman21.intellijbehave.parser.JBehaveFile;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 03.04.2015.
 */
public class StructureViewFactory implements PsiStructureViewFactory {
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile) {
        if (!(psiFile instanceof JBehaveFile)) return null;
        return new MyTreeBasedStructureViewBuilder(psiFile);

    }

    private static class MyTreeBasedStructureViewBuilder extends TreeBasedStructureViewBuilder {
        private final PsiFile psiFile;

        public MyTreeBasedStructureViewBuilder(PsiFile psiFile) {
            this.psiFile = psiFile;
        }

        @NotNull
        @Override
        public com.intellij.ide.structureView.StructureViewModel createStructureViewModel(@Nullable Editor editor) {
            return new StructureViewModel(psiFile, editor);
        }

        @Override
        public boolean isRootNodeShown() {
            return false;
        }
    }
}
