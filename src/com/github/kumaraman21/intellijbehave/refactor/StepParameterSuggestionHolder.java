package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

/**
 * Created by DeBritoD on 08.04.2015.
 */
public class StepParameterSuggestionHolder extends SuggestionHolder {
    private final Deque<PsiElement> siblings;

    public StepParameterSuggestionHolder(PsiElement value) {
        super(value);
        this.siblings = getSiblings(getPsiElement());
    }

    @Nullable
    @Override
    public TextRange getTextRange() {
        return new TextRange(siblings.getFirst().getTextRange().getStartOffset(),
                             siblings.getLast().getTextRange().getEndOffset());
    }

    public boolean isSame(StepParameterSuggestionHolder other) {
        if (siblings.size() == other.siblings.size()) {
            Iterator<PsiElement> meIt = siblings.iterator();
            Iterator<PsiElement> otherIt = other.siblings.iterator();
            while (meIt.hasNext()) {
                if (!meIt.next().getText().equals(otherIt.next().getText())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public String getText() {
        Collection<PsiElement> siblings = getSiblings(getPsiElement());
        StringBuilder sb = new StringBuilder();
        for (PsiElement element : siblings) {
            sb.append(element.getText());
        }
        return sb.toString();
    }

    private static Deque<PsiElement> getSiblings(PsiElement element) {
        Deque<PsiElement> elements = new LinkedList<PsiElement>();
        elements.add(element);
        addTillFirstSibling(element, elements);
        addTillLastSibling(element, elements);
        return elements;
    }

    private static void addTillFirstSibling(PsiElement start, Deque<PsiElement> elements) {
        PsiElement help = start.getPrevSibling();
        int pivot = 0;
        while (help != null) {
            IElementType type = help.getNode().getElementType();
            if (type == IJBehaveElementType.JB_TOKEN_WORD) {
                Boolean userData = help.getUserData(ParserRule.isStepParameter);
                if (userData == null || !userData) {
                    break;
                } else {
                    pivot = 0;
                }
            } else {
                ++pivot;
            }
            elements.addFirst(help);
            help = help.getPrevSibling();
        }
        for (int i = 0; i < pivot; ++i) {
            elements.removeFirst();
        }
    }

    private static void addTillLastSibling(PsiElement start, Deque<PsiElement> elements) {
        PsiElement help = start.getNextSibling();
        int pivot = 0;
        while (help != null) {
            IElementType type = help.getNode().getElementType();
            if (type == IJBehaveElementType.JB_TOKEN_WORD) {
                Boolean userData = help.getUserData(ParserRule.isStepParameter);
                if (userData == null || !userData) {
                    break;
                } else {
                    pivot = 0;
                }
            } else {
                ++pivot;
            }
            elements.add(help);
            help = help.getNextSibling();
        }
        for (int i = 0; i < pivot; ++i) {
            elements.removeLast();
        }
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return null;
    }

    public PsiElement replace(List<PsiElement> newElement) throws IncorrectOperationException {
        Iterator<PsiElement> newIt = newElement.iterator();
        Iterator<PsiElement> myIt = siblings.iterator();
        PsiElement parent = getParent();
        PsiElement last = null;
        PsiElement siblingFirst = siblings.getFirst();
        while (newIt.hasNext()) {
            last = parent.addBefore(newIt.next(), siblingFirst);
        }
        while (myIt.hasNext()) {
            myIt.next().delete();
        }

        return last;
    }
}
