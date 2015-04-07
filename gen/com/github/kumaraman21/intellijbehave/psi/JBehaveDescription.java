// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface JBehaveDescription extends PsiElement {

    @NotNull
    List<JBehaveIpAddress> getIpAddressList();

    @NotNull
    List<JBehaveStoryPath> getStoryPathList();

    @NotNull
    List<JBehaveUri> getUriList();

}
