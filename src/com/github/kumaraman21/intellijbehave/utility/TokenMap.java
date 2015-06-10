package com.github.kumaraman21.intellijbehave.utility;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap<V> {
    private final Map<String, TokenMap<V>> nextTokens = new HashMap<String, TokenMap<V>>();
    private V leafToken = null;
    private static final Pattern PutTokenizer = Pattern.compile("(\\S+)", Pattern.DOTALL);
    private static final Pattern GetTokenizer = Pattern.compile("(\\S+)", Pattern.DOTALL);

    public TokenMap() {
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

    public boolean get(final String toFind, final ITokenMapVisitor<V> visitor, final boolean strict) {
        List<ParametrizedToken> tokens = new ArrayList<ParametrizedToken>();

        TokenIterator tokenIterator = new TokenIterator(toFind, GetTokenizer);
        while (tokenIterator.hasNext()) {
            tokens.add(tokenIterator.next());
        }
        return get(tokens, 0, visitor, strict);
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

    private void put(final Iterator<ParametrizedToken> path, final V value) {
        if (path.hasNext()) {
            final String token = reduceIfParameter(path.next().getValue());
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

    private static final Pattern InjectPattern = Pattern.compile("<.+?\\|\\s*(.+?)\\s*>", Pattern.DOTALL);

    protected String unwrapInject(final String value) {
        final Matcher matcher = InjectPattern.matcher(value);

        return matcher.find() && matcher.groupCount() > 0 ? matcher.group(1) : value;
    }

    protected boolean get(final List<ParametrizedToken> split, final int count, final ITokenMapVisitor<V> visitor,
                          final boolean strict) {
        if ((strict && count < split.size()) || (!strict && count < split.size() - 1)) {
            final ParametrizedToken nextToken = split.get(count);
            final String next = unwrapInject(nextToken.getValue());
            TokenMap<V> tokenMap = getNextTokens().get("$");
            if (tokenMap != null && tokenMap.get(split, count + 1, visitor, strict)) {
                return true;
            }
            tokenMap = getNextTokens().get(next);
            if (tokenMap != null) {
                visitor.pushToken(nextToken);
                if (tokenMap.get(split, count + 1, visitor, strict)) {
                    return true;
                } else {
                    visitor.popToken();
                    return false;
                }
            } else {
                return !strict && getNextTokens().get("$").putAllInto(visitor);
            }
        }

        if (!strict && count == split.size() - 1) {
            boolean found = false;
            final ParametrizedToken nextToken = split.get(count);
            final String prefix = unwrapInject(nextToken.getValue());
            for (final Map.Entry<String, TokenMap<V>> entry : getNextTokens().entrySet()) {
                final String key = entry.getKey();
                if (key.equals("$") || key.startsWith(prefix)) {
                    found |= entry.getValue().putAllInto(visitor);
                }
            }
            if (found) visitor.pushToken(nextToken);
            return found;
        }
        if (strict && getLeafToken() != null && count >= split.size()) {
            visitor.found(getLeafToken());
            return true;
        }

        return false;
    }

    protected boolean putAllInto(final ITokenMapVisitor<V> visitor) {
        boolean found = false;
        if (leafToken != null) {
            visitor.found(leafToken);
            found = true;
        }
        for (TokenMap<V> tokenMap : nextTokens.values()) {
            found |= tokenMap.putAllInto(visitor);
        }
        return found;
    }
}
