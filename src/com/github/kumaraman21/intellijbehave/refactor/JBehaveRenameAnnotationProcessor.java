package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.github.kumaraman21.intellijbehave.resolver.ScenarioStepReference;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.refactoring.rename.RenameDialog;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by DeBritoD on 18.03.2015.
 */
public class JBehaveRenameAnnotationProcessor extends RenamePsiElementProcessor {
    public JBehaveRenameAnnotationProcessor() {
        super();
    }

    @Override
    public boolean canProcessElement(@NotNull PsiElement element) {
        PsiElement help = element;
        while (!(help instanceof PsiLiteralExpression) && !(help instanceof PsiFile) && help != null) {
            help = help.getParent();
        }
        return (help != null && !(help instanceof PsiFile)) || element instanceof AnnotationSuggestionHolder;
    }

    @Override
    public RenameDialog createRenameDialog(Project project, PsiElement element, PsiElement nameSuggestionContext,
                                           Editor editor) {
        return new JBehaveAnnotationRenameDialog(project, element, new AnnotationSuggestionHolder(element), editor);
    }

    @Override
    public void renameElement(PsiElement element, String newName, UsageInfo[] usages,
                              RefactoringElementListener listener) throws IncorrectOperationException {
        if (element instanceof PsiLiteralExpression || element instanceof AnnotationSuggestionHolder) {
            super.renameElement(element, newName, usages, listener);
            PsiElement firstChild = element.getFirstChild();
            if (firstChild instanceof PsiJavaTokenImpl) {
                ((PsiJavaTokenImpl) firstChild).replaceWithText(String.format("\"%s\"", newName));
            }
        }
    }

    @NotNull
    @Override
    public Collection<PsiReference> findReferences(PsiElement element) {
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        if (method == null) return Collections.emptyList();
        PsiAnnotation annotation = PsiTreeUtil.getParentOfType(element, PsiAnnotation.class);
        GlobalSearchScope scopeRestrictedByFileTypes = GlobalSearchScope
                .getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(element.getProject()),
                                               JBehaveFileType.JBEHAVE_FILE_TYPE);
        Collection<PsiReference> all = ReferencesSearch.search(method, scopeRestrictedByFileTypes).findAll();
        Collection<PsiReference> retVal = new ArrayList<PsiReference>();
        for (PsiReference psiReference : all) {
            if (psiReference instanceof ScenarioStepReference) {
                ScenarioStepReference scenarioStepReference = (ScenarioStepReference) psiReference;
                if (scenarioStepReference.containsAnnotation(annotation)) {
                    retVal.add(psiReference);
                }
            }
        }
        return retVal;
    }

    @Override
    public void prepareRenaming(PsiElement element, String newName, Map<PsiElement, String> allRenames) {
        prepareRenaming(element, newName, allRenames, GlobalSearchScope
                .getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(element.getProject()),
                                               JBehaveFileType.JBEHAVE_FILE_TYPE));
    }

    @Override
    public void prepareRenaming(PsiElement element, String newName, Map<PsiElement, String> allRenames,
                                SearchScope scope) {

        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        if (method != null) {
            Map<PsiElement, String> buffer = new HashMap<PsiElement, String>();
            buffer.putAll(allRenames);
            allRenames.clear();
            for (Map.Entry<PsiElement, String> entry : buffer.entrySet()) {
                PsiElement key = entry.getKey();
                if (key instanceof PsiLiteralExpression) {
                    allRenames.put(new AnnotationSuggestionHolder(key), entry.getValue());
                } else {
                    allRenames.put(key, entry.getValue());
                }
            }
        }
    }

    @Nullable
    @Override
    public Runnable getPostRenameCallback(PsiElement element, String newName,
                                          RefactoringElementListener elementListener) {
        if (element instanceof PsiNamedElement) return super.getPostRenameCallback(element, newName, elementListener);
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
