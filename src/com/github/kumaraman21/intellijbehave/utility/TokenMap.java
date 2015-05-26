package com.github.kumaraman21.intellijbehave.utility;

import com.intellij.codeInsight.completion.CompletionUtilCore;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap<V> {
    private final Map<String, TokenMap<V>> nextTokens = new HashMap<String, TokenMap<V>>();
    private V leafToken;
    private static final Pattern pattern = Pattern.compile("((\\w|<|>|\\|)+|[\\W&&[^\\s]])", Pattern.DOTALL);

    public void put(final V value, final Collection<String> paths) {
        for (final String path : paths) {
            final List<String> split = splitPath(path);
            put(split.iterator(), value);
        }
    }

    public void put(final V value, final String... paths) {
        for (final String path : paths) {
            final List<String> split = splitPath(path);
            put(split.iterator(), value);
        }
    }

    public List<V> getConcerned(String toFind, boolean strict) {
        String reallyFind = toFind;
        int rulezzz = reallyFind.indexOf(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED);
        if (rulezzz >= 0) {
            reallyFind = reallyFind.substring(0, rulezzz);
        }
        return getConcerned(splitPath(reallyFind), 0, strict);
    }

    public boolean isEmpty() {
        return nextTokens.isEmpty() && leafToken == null;
    }

    private static List<String> splitPath(final String path) {
        final Matcher matcher = pattern.matcher(path);
        final List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(path.substring(matcher.start(), matcher.end()));
        }
        return result;
    }


    private void put(final Iterator<String> path, final V value) {
        if (path.hasNext()) {
            final String token = path.next();
            TokenMap<V> tokenMap = nextTokens.get(token);
            if (tokenMap == null) {
                tokenMap = new TokenMap<V>();
                nextTokens.put(token, tokenMap);
            }
            tokenMap.put(path, value);
        } else {
            leafToken = value;
        }
    }

    private String unwrapInject(String maybeInject) {
        if (maybeInject.startsWith("<") && maybeInject.endsWith(">")) {
            int start = maybeInject.indexOf("|") + 1;
            int end = maybeInject.indexOf(">", start);
            return maybeInject.substring(start, end);
        }
        return maybeInject;
    }

    private List<V> getConcerned(List<String> split, int count, boolean strict) {
        int it = count;
        while (it < split.size()) {
            String next = split.get(it);
            TokenMap<V> tokenMap = nextTokens.get(next);
            if (tokenMap == null) {
                //maybe it's a user inject
                String tryToken = unwrapInject(next);
                tokenMap = nextTokens.get(tryToken);
            }
            if (tokenMap != null) {
                List<V> concerned = tokenMap.getConcerned(split, it + 1, strict);
                if (!concerned.isEmpty()) {
                    return concerned;
                }
            }
            if (!strict && it + 1 == split.size()) {
                List<V> result = new ArrayList<V>();
                for (Map.Entry<String, TokenMap<V>> entry : nextTokens.entrySet()) {
                    if (entry.getKey().startsWith(next)) {
                        result.addAll(entry.getValue().getAll());
                    }
                }
                //maybe the last is a parameter
                if (result.isEmpty()) {
                    result.addAll(getAll());
                }
                return result;

            }
            ++it;
        }
        //The rest of the input could be a parameter. This will be solved in ParametrizedString.
        if (it >= split.size() && strict && leafToken != null) {
            return Collections.singletonList(leafToken);
        }

        return Collections.emptyList();
    }

    private List<V> getAll() {
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
