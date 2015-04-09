package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
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
public class JBehaveRenameInjectProcessor extends RenamePsiElementProcessor {
    public JBehaveRenameInjectProcessor() {
        super();
    }

    @Override
    public boolean canProcessElement(PsiElement element) {
        ASTNode node = element.getNode();
        if (node != null) {
            IElementType type = node.getElementType();
            return type != null &&
                    (type == IJBehaveElementType.JB_TOKEN_USER_INJECT || type == IJBehaveElementType.JB_TOKEN_INJECT);
        }
        return false;
    }

    @Override
    public RenameDialog createRenameDialog(Project project, PsiElement element, PsiElement nameSuggestionContext,
                                           Editor editor) {
        if (canProcessElement(element)) {
            return new JBehaveAnnotationRenameDialog(project, element, new InjectSuggestionHolder(element), editor);
        }
        return null;
    }

    @Override
    public void renameElement(PsiElement element, String newName, UsageInfo[] usages,
                              RefactoringElementListener listener) throws IncorrectOperationException {
        IElementType type = element.getNode().getElementType();
        String format = null;
        if (type == IJBehaveElementType.JB_TOKEN_USER_INJECT) {
            format = "<<%s>>";
        } else {
            if (type == IJBehaveElementType.JB_TOKEN_INJECT) {
                format = "<%s>";
            }
        }
        if (format != null) {
            PsiFile psiFile = PsiFileFactory.getInstance(element.getProject())
                                            .createFileFromText("dummy.story", JBehaveFileType.JBEHAVE_FILE_TYPE,
                                                                String.format(format, newName));
            PsiElement[] injectElements = type == IJBehaveElementType.JB_TOKEN_INJECT ? getInjectElements(psiFile) :
                    getUserInjectElements(psiFile);
            if (injectElements.length > 0) {
                element.replace(injectElements[0]);
            }
        }
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
            IElementType type = element.getNode().getElementType();
            PsiFile containingFile = element.getContainingFile();
            PsiElement[] psiElements = type == IJBehaveElementType.JB_TOKEN_INJECT ? getInjectElements(containingFile) :
                    getUserInjectElements(containingFile);
            String text = element.getText();
            allRenames.clear();
            for (PsiElement found : psiElements) {
                if (found.getText().equals(text)) {
                    allRenames.put(new InjectSuggestionHolder(found), newName);
                }
            }
        }
    }

    private PsiElement[] getUserInjectElements(PsiFile containingFile) {
        return PsiTreeUtil.collectElements(containingFile, new PsiElementFilter() {
            @Override
            public boolean isAccepted(PsiElement element) {
                return element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_USER_INJECT;
            }
        });
    }

    private PsiElement[] getInjectElements(PsiFile containingFile) {
        return PsiTreeUtil.collectElements(containingFile, new PsiElementFilter() {
            @Override
            public boolean isAccepted(PsiElement element) {
                return element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_INJECT;
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
