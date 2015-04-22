package com.github.kumaraman21.intellijbehave.utility;

import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.codeInsight.completion.CompletionUtilCore;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap {
    private final Map<String, TokenMap> nextTokens = new HashMap<String, TokenMap>();
    private JavaStepDefinition leafToken;// = new ArrayList<JavaStepDefinition>();
    private static final Pattern pattern = Pattern.compile("((\\w|<|>|\\|)+|[\\W&&[^\\s]])", Pattern.DOTALL);

    private static List<String> split(final String text) {
        final Matcher matcher = pattern.matcher(text);

        List<String> result2 = new ArrayList<String>();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            result2.add(text.substring(start, end));
        }
        return result2;
    }

    public void put(final JavaStepDefinition def) {
        final Set<ParametrizedString> parametrizedStrings = def.toPString();
        for (ParametrizedString parametrizedString : parametrizedStrings) {
            final String stringWithoutIdentifiers = parametrizedString.toStringWithoutIdentifiers();
            final List<String> split =
                    split(String.format("%s %s", def.getAnnotationTypeAsString(), stringWithoutIdentifiers));
            put(split, 0, def);
        }
    }

    private void put(final List<String> split, final int count, final JavaStepDefinition def) {
        if (count < split.size()) {
            String token = split.get(count);
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
        int rulezzz = reallyFind.indexOf(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED);
        if (rulezzz >= 0) {
            reallyFind = reallyFind.substring(0, rulezzz);
        }
        List<String> split = split(reallyFind);
        return getConcerned(split, 0, strict);
    }

    private String unwrapInject(String maybeInject) {
        if (maybeInject.startsWith("<") && maybeInject.endsWith(">")) {
            int start = maybeInject.indexOf("|") + 1;
            int end = maybeInject.indexOf(">", start);
            return maybeInject.substring(start, end);
        }
        return maybeInject;
    }

    private List<JavaStepDefinition> getConcerned(List<String> split, int count, boolean strict) {
        int it = count;
        while (it < split.size()) {
            String next = split.get(it);
            TokenMap tokenMap = nextTokens.get(next);
            if (tokenMap == null) {
                //maybe it's a user inject
                String tryToken = unwrapInject(next);
                tokenMap = nextTokens.get(tryToken);
            }
            if (tokenMap != null) {
                List<JavaStepDefinition> concerned = tokenMap.getConcerned(split, it + 1, strict);
                if (!concerned.isEmpty()) {
                    return concerned;
                }
            }
            if (!strict && it + 1 == split.size()) {
                List<JavaStepDefinition> result = new ArrayList<JavaStepDefinition>();
                for (Map.Entry<String, TokenMap> entry : nextTokens.entrySet()) {
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

    private List<JavaStepDefinition> getAll() {
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
