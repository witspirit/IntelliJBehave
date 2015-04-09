package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.refactoring.rename.RenameDialog;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by DeBritoD on 18.03.2015.
 */
public class JBehaveRenameStepParameterProcessor extends RenamePsiElementProcessor {
    public JBehaveRenameStepParameterProcessor() {
        super();
    }

    @Override
    public boolean canProcessElement(PsiElement element) {
        ASTNode node = element.getNode();
        if (node != null) {
            IElementType type = node.getElementType();
            if (type != null && type == IJBehaveElementType.JB_TOKEN_WORD) {
                Boolean userData = element.getUserData(ParserRule.isStepParameter);
                return userData != null && userData;
            }
        }
        return false;
    }


    @Override
    public RenameDialog createRenameDialog(Project project, PsiElement element, PsiElement nameSuggestionContext,
                                           Editor editor) {
        if (canProcessElement(element)) {
            return new JBehaveAnnotationRenameDialog(project, element, new StepParameterSuggestionHolder(element),
                                                     editor);
        }
        return null;
    }

    @Override
    public void renameElement(PsiElement element, String newName, UsageInfo[] usages,
                              RefactoringElementListener listener) throws IncorrectOperationException {
        PsiFile psiFile = PsiFileFactory.getInstance(element.getProject())
                                        .createFileFromText("dummy.story", JBehaveFileType.JBEHAVE_FILE_TYPE, newName);
        PsiElement[] stepParameters = getAll(psiFile);
        if (element instanceof StepParameterSuggestionHolder) {
            ((StepParameterSuggestionHolder) element).replace(stepParameters);
        }
    }

    private PsiElement[] getAll(PsiFile containingFile) {
        return PsiTreeUtil.collectElements(containingFile, new PsiElementFilter() {
            @Override
            public boolean isAccepted(PsiElement element) {
                return element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_WORD ||
                        element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_SPACE;
            }
        });
    }

    @NotNull
    @Override
    public Collection<PsiReference> findReferences(PsiElement element) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void prepareRenaming(PsiElement element, String newName, Map<PsiElement, String> allRenames) {
        if (canProcessElement(element)) {
            prepareRenaming(element, newName, allRenames, new LocalSearchScope(element.getContainingFile()));
        }
    }

    @Override
    public void prepareRenaming(PsiElement element, String newName, Map<PsiElement, String> allRenames,
                                SearchScope scope) {
        if (canProcessElement(element)) {
            PsiFile containingFile = element.getContainingFile();
            PsiElement[] psiElements = getStepParameters(containingFile);
            String text = element.getText();
            StepParameterSuggestionHolder toFind = new StepParameterSuggestionHolder(element);
            allRenames.clear();
            for (PsiElement found : psiElements) {
                Boolean userData = element.getUserData(ParserRule.isStepParameter);
                if (found.getText().equals(text) && userData != null && userData) {
                    StepParameterSuggestionHolder holder = new StepParameterSuggestionHolder(found);
                    if (toFind.isSame(holder)) {
                        allRenames.put(holder, newName);
                    }
                }
            }
        }
    }

    private PsiElement[] getStepParameters(PsiFile containingFile) {
        return PsiTreeUtil.collectElements(containingFile, new PsiElementFilter() {
            @Override
            public boolean isAccepted(PsiElement element) {
                return element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_WORD;
            }
        });
    }

    @Nullable
    @Override
    public Runnable getPostRenameCallback(PsiElement element, String newName,
                                          RefactoringElementListener elementListener) {
        return null;
    }

    @Override
    public boolean isToSearchInComments(PsiElement element) {
        return false;
    }

    @Override
    public void setToSearchInComments(PsiElement element, boolean enabled) {
        //super.setToSearchInComments(element, enabled);
    }

    @Override
    public boolean isToSearchForTextOccurrences(PsiElement element) {
        return false;
    }

    @Override
    public void setToSearchForTextOccurrences(PsiElement element, boolean enabled) {
        //super.setToSearchForTextOccurrences(element, enabled);
    }

    @Override
    public boolean showRenamePreviewButton(PsiElement psiElement) {
        return true;
    }

    @Override
    public boolean forcesShowPreview() {
        return true;
    }
}
