package com.github.kumaraman21.intellijbehave.utility;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap<V> {
    private final String key;
    private final Map<String, TokenMap<V>> nextTokens = new HashMap<String, TokenMap<V>>();
    private V leafToken;
    private static final Pattern PutTokenizer = Pattern.compile("(\\S+)", Pattern.DOTALL);
    private static final Pattern GetTokenizer = Pattern.compile("(\\S+)", Pattern.DOTALL);

    public TokenMap() {
        key = "";
    }

    public TokenMap(String key) {
        this.key = key;
    }

    protected V getLeafToken() {
        return leafToken;
    }

    protected Map<String, TokenMap<V>> getNextTokens() {
        return nextTokens;
    }

    public void put(final V value, final Collection<String> paths) {
        for (final String path : paths) {
            put(new TokenIterator(path, PutTokenizer), value);
        }
    }

    public void put(final V value, final String... paths) {
        for (final String path : paths) {
            put(new TokenIterator(path, PutTokenizer), value);
        }
    }

    public List<V> get(final String toFind, final boolean strict) {
        List<String> tokens = new ArrayList<String>();

        TokenIterator tokenIterator = new TokenIterator(toFind, GetTokenizer);
        while (tokenIterator.hasNext()) {
            tokens.add(tokenIterator.next());
        }
        return get(tokens, 0, strict);
    }

    public boolean isEmpty() {
        return nextTokens.isEmpty() && leafToken == null;
    }

    private String reduceIfParameter(final String token) {
        final char firstChar = token.charAt(0);
        if (firstChar == '$' || firstChar == '<' && token.charAt(token.length() - 1) == '>') {
            return "$";
        }
        return token;
    }

    private void put(final Iterator<String> path, final V value) {
        if (path.hasNext()) {
            final String token = reduceIfParameter(path.next());
            TokenMap<V> tokenMap = nextTokens.get(token);
            if (tokenMap == null) {
                tokenMap = token.equals("$") ? new TokenMapParam<V>() : new TokenMap<V>();
                nextTokens.put(token, tokenMap);
            }
            tokenMap.put(path, value);
        } else {
            leafToken = value;
        }
    }

    protected String unwrapInject(final String value) {
        int start = value.indexOf('<');
        if (start < 0) return value;
        final StringBuilder sb = new StringBuilder();
        String newValue = value;
        while (start >= 0) {
            final boolean doubleStart = newValue.charAt(start + 1) == '<';
            final int end = newValue.indexOf('>', start);
            int secondEnd = end;
            if (end + 1 < newValue.length() && newValue.charAt(end + 1) == '>' && doubleStart) ++secondEnd;
            if (end >= 0) {
                final int pipe = newValue.indexOf("|", start + 1);
                if (pipe >= 0) {
                    sb.append(newValue.substring(0, start));
                    sb.append(newValue.substring(pipe + 1, end));
                    newValue = newValue.substring(secondEnd + 1, newValue.length());
                    start = newValue.indexOf('<');
                } else {
                    start = newValue.indexOf('<', start + 1);
                }
            } else {
                break;
            }
        }
        sb.append(newValue);
        return sb.toString();
    }

    protected List<V> get(final List<String> split, final int count, final boolean strict) {
        if ((strict && count < split.size()) || (!strict && count < split.size() - 1)) {
            TokenMap<V> tokenMap;
            final String next = unwrapInject(split.get(count));
            tokenMap = getNextTokens().get("$");
            if (tokenMap != null) {
                final List<V> concerned = tokenMap.get(split, count + 1, strict);
                if (!concerned.isEmpty()) {
                    return concerned;
                }
            }
            tokenMap = getNextTokens().get(next);
            if (tokenMap != null) {
                return tokenMap.get(split, count + 1, strict);
            } else {
                if (!strict) {
                    tokenMap = getNextTokens().get("$");
                    return tokenMap.getAll();
                }
                return Collections.emptyList();
            }
        }

        if (!strict && count == split.size() - 1) {
            final List<V> result = new ArrayList<V>();
            String prefix = unwrapInject(split.get(count));
            for (final Map.Entry<String, TokenMap<V>> entry : getNextTokens().entrySet()) {
                final String key = entry.getKey();
                if (key.equals("$") || key.startsWith(prefix)) {
                    result.addAll(entry.getValue().getAll());
                }
            }
            return result;
        }
        final V leafToken = getLeafToken();
        if (count >= split.size() && strict && leafToken != null) {
            return Collections.singletonList(leafToken);
        }

        return Collections.emptyList();
    }

    protected List<V> getAll() {
        final List<V> result = new ArrayList<V>();
        if (leafToken != null) {
            result.add(leafToken);
        }
        for (TokenMap<V> tokenMap : nextTokens.values()) {
            result.addAll(tokenMap.getAll());
        }
        return result;
    }

}
