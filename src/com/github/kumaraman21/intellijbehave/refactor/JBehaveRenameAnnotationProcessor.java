package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.Pass;
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
import com.intellij.util.containers.MultiMap;
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
    public boolean canProcessElement(PsiElement element) {
        PsiElement help = element;
        while (!(help instanceof PsiLiteralExpression) && help != null) {
            help = help.getParent();
        }
        return help != null || element instanceof PsiSuggestionHolder;
    }

    @Override
    public RenameDialog createRenameDialog(Project project, PsiElement element, PsiElement nameSuggestionContext,
                                           Editor editor) {
        String text = element.getText();
        return new JBehaveAnnotationRenameDialog(project, element, new PsiSuggestionHolder(element), editor);
    }

    @Override
    public void renameElement(PsiElement element, String newName, UsageInfo[] usages,
                              RefactoringElementListener listener) throws IncorrectOperationException {
        if (element instanceof PsiLiteralExpression || element instanceof PsiSuggestionHolder) {
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
        PsiAnnotation annotation = PsiTreeUtil.getParentOfType(element, PsiAnnotation.class);
        GlobalSearchScope scopeRestrictedByFileTypes = GlobalSearchScope.getScopeRestrictedByFileTypes(
                GlobalSearchScope.projectScope(element.getProject()), StoryFileType.STORY_FILE_TYPE);
        Collection<PsiReference> all = ReferencesSearch.search(method, scopeRestrictedByFileTypes).findAll();
        Collection<PsiReference> retVal = new ArrayList<PsiReference>();
        for (PsiReference psiReference : all) {
            if (psiReference instanceof StepPsiReference) {
                StepPsiReference stepPsiReference = (StepPsiReference) psiReference;
                if (stepPsiReference.containsAnnotation(annotation)) {
                    retVal.add(psiReference);
                }
            }
        }
        return retVal;
    }

    @Nullable
    @Override
    public Pair<String, String> getTextOccurrenceSearchStrings(PsiElement element, String newName) {
        return super.getTextOccurrenceSearchStrings(element, newName);
    }

    @Nullable
    @Override
    public String getQualifiedNameAfterRename(PsiElement element, String newName, boolean nonJava) {
        return super.getQualifiedNameAfterRename(element, newName, nonJava);
    }

    @Override
    public void prepareRenaming(PsiElement element, String newName, Map<PsiElement, String> allRenames) {
        prepareRenaming(element, newName, allRenames,
                GlobalSearchScope.getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(element.getProject()),
                        StoryFileType.STORY_FILE_TYPE));

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
                    allRenames.put(new PsiSuggestionHolder(key), entry.getValue());
                } else {
                    allRenames.put(key, entry.getValue());
                }
            }
            String f = "";
        }
    }

    @Override
    public void findExistingNameConflicts(PsiElement element, String newName, MultiMap<PsiElement, String> conflicts) {
        super.findExistingNameConflicts(element, newName, conflicts);
    }

    @Override
    public void findExistingNameConflicts(PsiElement element, String newName, MultiMap<PsiElement, String> conflicts,
                                          Map<PsiElement, String> allRenames) {
        super.findExistingNameConflicts(element, newName, conflicts, allRenames);
    }

    @Override
    public boolean isInplaceRenameSupported() {
        return super.isInplaceRenameSupported();
    }

    @Nullable
    @Override
    public Runnable getPostRenameCallback(PsiElement element, String newName,
                                          RefactoringElementListener elementListener) {
        if (element instanceof PsiNamedElement) return super.getPostRenameCallback(element, newName, elementListener);
        return null;
    }

    @Nullable
    @Override
    public String getHelpID(PsiElement element) {
        return super.getHelpID(element);
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

    @Nullable
    public PsiElement subssdtituteElementToRename(PsiElement element, Editor editor) {
        return super.substituteElementToRename(element, editor);
    }

    @Override
    public void substituteElementToRename(PsiElement element, Editor editor, Pass<PsiElement> renameCallback) {
        super.substituteElementToRename(element, editor, renameCallback);
    }

    @Override
    public void findCollisions(PsiElement element, String newName, Map<? extends PsiElement, String> allRenames,
                               List<UsageInfo> result) {
        super.findCollisions(element, newName, allRenames, result);
    }

    @Override
    public boolean forcesShowPreview() {
        return true;
    }

    @Nullable
    @Override
    public PsiElement getElementToSearchInStringsAndComments(PsiElement element) {
        return super.getElementToSearchInStringsAndComments(element);
    }
}
