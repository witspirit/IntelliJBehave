// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface StoryStepLine extends PsiElement {

  @NotNull
  List<StoryInject> getInjectList();

  @NotNull
  List<StoryIpAddress> getIpAddressList();

  @NotNull
  List<StoryUri> getUriList();

  @NotNull
  List<StoryUserInject> getUserInjectList();

}
