package com.github.kumaraman21.intellijbehave.utility;

import java.util.Iterator;

/**
 * Created by DeBritoD on 01.06.2015.
 *
 * If there is a list of tokens e.g.: [ab, cd, *ef, gh] (where * marks a parameter), then return a reduced version
 * e.g.: [abcd, *ef, gh].
 */
public class ReduceTokenIterator implements Iterator<ParametrizedToken> {
    private Iterator<ParametrizedToken> it;
    private ParametrizedToken savedNext = null;

    public ReduceTokenIterator(Iterator<ParametrizedToken> it) {
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return savedNext != null || it.hasNext();
    }

    @Override
    public ParametrizedToken next() {
        StringBuilder sb = new StringBuilder();
        ParametrizedToken next = savedNext != null ? savedNext : it.next();
        ParametrizedToken runToken = next;
        ParametrizedToken last = next;
        int startOffset = next.getStart();
        savedNext = null;
        boolean parameter = next.isParameter();
        sb.append(next.getValue());
        while (it.hasNext()) {
            next = it.next();
            if (next.isParameter() == parameter) {
                for (int i = runToken.getEnd(); i < next.getStart(); ++i) {
                    sb.append(" ");
                    runToken = next;
                }
                sb.append(next.getValue());
                last = next;
            } else {
                savedNext = next;
                break;
            }
        }
        return new ParametrizedToken(sb.toString(), startOffset, last.getEnd(), parameter);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
