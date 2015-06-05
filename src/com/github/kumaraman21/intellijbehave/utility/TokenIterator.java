package com.github.kumaraman21.intellijbehave.utility;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DeBritoD on 27.05.2015.
 */
public class TokenIterator implements Iterator<String> {
    private final Matcher matcher;
    private boolean matching = false;
    private boolean hasMatch = false;
    private final String value;

    public TokenIterator(String value, Pattern tokenizer) {
        this.value = value;
        this.matcher = tokenizer.matcher(value);
    }

    @Override
    public boolean hasNext() {
        if (!matching) {
            hasMatch = matcher.find();
            matching = true;
        }
        return hasMatch;
    }

    @Override
    public String next() {
        if (hasNext()) {
            matching = false;
            return value.substring(matcher.start(), matcher.end());
        }
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
