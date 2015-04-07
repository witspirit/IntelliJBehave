// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface JBehaveUriIdentifier extends PsiElement {

    @Nullable
    JBehaveIpAddress getIpAddress();

    @NotNull
    List<JBehaveUriWord> getUriWordList();

}
