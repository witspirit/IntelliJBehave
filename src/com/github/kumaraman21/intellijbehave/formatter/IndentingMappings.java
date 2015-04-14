package com.github.kumaraman21.intellijbehave.formatter;

import com.intellij.psi.tree.IElementType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeBritoD on 14.04.2015.
 */
public class IndentingMappings {
    private Map<IElementType, Integer> indents = new HashMap<IElementType, Integer>();
    private int defaultIndent;

    public IndentingMappings(int defaultIndent) {
        this.defaultIndent = defaultIndent;
    }

    public void putIndent(IElementType child, int indent) {
        indents.put(child, indent);
    }

    public int getIndent(IElementType child) {
        Integer integer = indents.get(child);
        return integer == null ? defaultIndent : integer;
    }
}

