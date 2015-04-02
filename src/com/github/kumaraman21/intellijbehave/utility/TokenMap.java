package com.github.kumaraman21.intellijbehave.utility;

import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;

import java.util.*;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap {
    private Map<String, TokenMap> nextTokens = new HashMap<String, TokenMap>();
    private JavaStepDefinition leafToken;// = new ArrayList<JavaStepDefinition>();


    public void put(final JavaStepDefinition def) {
        final Set<ParametrizedString> parametrizedStrings = def.toPString();
        for (ParametrizedString parametrizedString : parametrizedStrings) {
            final String stringWithoutIdentifiers = parametrizedString.toStringWithoutIdentifiers();
            final String[] split = String.format("%s %s", def.getAnnotationTypeAsString(),
                    stringWithoutIdentifiers).split("[ \t\f]+");
            put(split, 0, def);
        }
    }

    private void put(final String[] split, final int count, final JavaStepDefinition def) {
        if (count < split.length) {
            String token = split[count];
            TokenMap tokenMap = nextTokens.get(token);
            if (tokenMap == null) {
                tokenMap = new TokenMap();
                nextTokens.put(token, tokenMap);
            }
            tokenMap.put(split, count + 1, def);
        } else {
            leafToken = def;
        }
    }

    public List<JavaStepDefinition> getConcerned(String toFind, boolean strict) {
        String reallyFind = toFind;
        int rulezzz = reallyFind.indexOf("IntellijIdeaRulezzz");
        if (rulezzz >= 0) {
            reallyFind = reallyFind.substring(0, rulezzz);
        }
        String[] split = reallyFind.split("[ \t\f]+");
        return getConcerned(split, 0, strict);
    }

    private List<JavaStepDefinition> getConcerned(String[] split, int count, boolean strict) {
        int it = count;
        while (it < split.length) {
            String next = split[it];
            TokenMap tokenMap = nextTokens.get(next);
            if (tokenMap != null) {
                List<JavaStepDefinition> concerned = tokenMap.getConcerned(split, it + 1, strict);
                if (!concerned.isEmpty()) {
                    return concerned;
                }
            }
            if (!strict && it + 1 == split.length) {
                List<JavaStepDefinition> result = new ArrayList<JavaStepDefinition>();
                for (Map.Entry<String, TokenMap> entry : nextTokens.entrySet()) {
                    if (entry.getKey().startsWith(next)) {
                        result.addAll(entry.getValue().getAll());
                    }
                }
                return result;

            }
            ++it;
        }
        if (it >= split.length && strict && leafToken != null) {
            return Collections.singletonList(leafToken);
        }

        return Collections.emptyList();
    }

    public List<JavaStepDefinition> getAll() {
        final List<JavaStepDefinition> result = new ArrayList<JavaStepDefinition>();
        if (leafToken != null) {
            result.add(leafToken);
        }
        for (TokenMap tokenMap : nextTokens.values()) {
            result.addAll(tokenMap.getAll());
        }
        return result;
    }

    public boolean isEmpty() {
        return nextTokens.isEmpty() && leafToken == null;
    }
}
