package com.github.kumaraman21.intellijbehave.spellchecker;

import com.github.kumaraman21.intellijbehave.parser.JBehaveElementType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.spellchecker.quickfixes.AcceptWordAsCorrect;
import com.intellij.spellchecker.quickfixes.ChangeTo;
import com.intellij.spellchecker.quickfixes.SpellCheckerQuickFix;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

public class JBehaveSpellcheckerStrategy extends SpellcheckingStrategy {
    @NotNull
    public Tokenizer getTokenizer(PsiElement element) {
        if (element instanceof LeafElement) {
            ASTNode node = element.getNode();
            if (node != null && node.getElementType() instanceof JBehaveElementType) {
                return TEXT_TOKENIZER;
            }
        }

        return super.getTokenizer(element);
    }

    public SpellCheckerQuickFix[] getRegularFixes(PsiElement element, int offset, @NotNull TextRange textRange,
                                                  boolean useRename, String wordWithTypo) {
        return new SpellCheckerQuickFix[]{new ChangeTo(wordWithTypo), new AcceptWordAsCorrect(wordWithTypo)};
    }
}
