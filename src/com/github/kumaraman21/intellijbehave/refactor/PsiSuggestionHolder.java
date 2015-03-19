package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.FakePsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by DeBritoD on 19.03.2015.
 */
public class PsiSuggestionHolder extends FakePsiElement {
    private PsiElement psiElement;

    public PsiSuggestionHolder(PsiElement value) {
        this.psiElement = value;
    }

    @NonNls
    @Nullable
    @Override
    public String getText() {
        return psiElement.getText();
    }

    @Override
    @NotNull
    public char[] textToCharArray() {
        return psiElement.textToCharArray();
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        return psiElement.getNavigationElement();
    }

    @Override
    public PsiElement getOriginalElement() {
        return psiElement.getOriginalElement();
    }

    @Override
    public boolean textMatches(CharSequence text) {
        return psiElement.textMatches(text);
    }

    @Override
    public boolean textMatches(PsiElement element) {
        return psiElement.textMatches(element);
    }

    @Override
    public boolean textContains(char c) {
        return psiElement.textContains(c);
    }

    @Override
    public void accept(PsiElementVisitor visitor) {
        psiElement.accept(visitor);
    }

    @Override
    public void acceptChildren(PsiElementVisitor visitor) {
        psiElement.acceptChildren(visitor);
    }

    @Override
    public PsiElement copy() {
        return psiElement.copy();
    }

    @Override
    public PsiElement add(PsiElement element) throws IncorrectOperationException {
        return psiElement.add(element);
    }

    @Override
    public PsiElement addBefore(PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        return psiElement.addBefore(element, anchor);
    }

    @Override
    public PsiElement addAfter(PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        return psiElement.addAfter(element, anchor);
    }

    @Override
    public void checkAdd(PsiElement element) throws IncorrectOperationException {
        psiElement.checkAdd(element);
    }

    @Override
    public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        return psiElement.addRange(first, last);
    }

    @Override
    public PsiElement addRangeBefore(PsiElement first, PsiElement last,
                                     PsiElement anchor) throws IncorrectOperationException {
        return psiElement.addRangeBefore(first, last, anchor);
    }

    @Override
    public PsiElement addRangeAfter(PsiElement first, PsiElement last,
                                    PsiElement anchor) throws IncorrectOperationException {
        return psiElement.addRangeAfter(first, last, anchor);
    }

    @Override
    public void delete() throws IncorrectOperationException {
        psiElement.delete();
    }

    @Override
    public void checkDelete() throws IncorrectOperationException {
        psiElement.checkDelete();
    }

    @Override
    public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        psiElement.deleteChildRange(first, last);
    }

    @Override
    public PsiElement replace(PsiElement newElement) throws IncorrectOperationException {
        return psiElement.replace(newElement);
    }

    @Override
    public boolean isValid() {
        return psiElement.isValid();
    }

    @Override
    public boolean isWritable() {
        return psiElement.isWritable();
    }

    @Override
    @Nullable
    public PsiReference getReference() {
        return psiElement.getReference();
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return psiElement.getReferences();
    }

    @Override
    @Nullable
    public <T> T getCopyableUserData(Key<T> key) {
        return psiElement.getCopyableUserData(key);
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, @Nullable T value) {
        psiElement.putCopyableUserData(key, value);
    }

    @Override
    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, @Nullable PsiElement lastParent,
                                       PsiElement place) {
        return psiElement.processDeclarations(processor, state, lastParent, place);
    }

    @Override
    @Nullable
    public PsiElement getContext() {
        return psiElement.getContext();
    }

    @Override
    public boolean isPhysical() {
        return psiElement.isPhysical();
    }

    @Override
    @NotNull
    public GlobalSearchScope getResolveScope() {
        return psiElement.getResolveScope();
    }

    @Override
    @NotNull
    public SearchScope getUseScope() {
        return psiElement.getUseScope();
    }

    @Nullable
    @Override
    public ASTNode getNode() {
        return psiElement.getNode();
    }

    @Override
    @NonNls
    public String toString() {
        return psiElement.toString();
    }

    @Override
    public boolean isEquivalentTo(PsiElement another) {
        return psiElement.isEquivalentTo(another);
    }

    @Override
    @Nullable
    public <T> T getUserData(Key<T> key) {
        return psiElement.getUserData(key);
    }

    @Override
    public <T> void putUserData(Key<T> key, @Nullable T value) {
        psiElement.putUserData(key, value);
    }

    @Override
    public PsiElement setName(String name) throws IncorrectOperationException {
        return this;
    }

    @Override
    public String getName() {
        return getText();
    }

    @Override
    public PsiElement getParent() {
        return psiElement.getParent();
    }

    @Nullable
    @Override
    public PsiElement getFirstChild() {
        return psiElement.getFirstChild();
    }

    @Nullable
    @Override
    public PsiElement getLastChild() {
        return psiElement.getLastChild();
    }

    @Nullable
    @Override
    public PsiElement getNextSibling() {
        return psiElement.getNextSibling();
    }

    @Nullable
    @Override
    public PsiElement getPrevSibling() {
        return psiElement.getPrevSibling();
    }

    @Override
    public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        return psiElement.getContainingFile();
    }

    @Nullable
    @Override
    public TextRange getTextRange() {
        return psiElement.getTextRange();
    }

    @Override
    public int getStartOffsetInParent() {
        return psiElement.getStartOffsetInParent();
    }

    @Override
    public int getTextLength() {
        return psiElement.getTextLength();
    }

    @Override
    @Nullable
    public PsiElement findElementAt(int offset) {
        return psiElement.findElementAt(offset);
    }

    @Override
    @Nullable
    public PsiReference findReferenceAt(int offset) {
        return psiElement.findReferenceAt(offset);
    }

    @Override
    public int getTextOffset() {
        return psiElement.getTextOffset();
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return JBehaveIcons.JB;
    }


    @Override
    @NotNull
    public Project getProject() throws PsiInvalidElementAccessException {
        return psiElement.getProject();
    }

    @Override
    @NotNull
    public Language getLanguage() {
        return psiElement.getLanguage();
    }

    @Override
    public PsiManager getManager() {
        return psiElement.getManager();
    }

    @Override
    @NotNull
    public PsiElement[] getChildren() {
        return psiElement.getChildren();
    }

    @Override
    public ItemPresentation getPresentation() {
        return this;
    }

    @Override
    public String getPresentableText() {
        return getText();
    }
}
