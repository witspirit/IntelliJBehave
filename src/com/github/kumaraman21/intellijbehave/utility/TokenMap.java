package com.github.kumaraman21.intellijbehave.utility;

import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import org.jbehave.core.steps.StepType;

import java.util.*;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap {
    private Map<String, TokenMap> nextTokens = new HashMap<String, TokenMap>();
    private JavaStepDefinition leafToken;// = new ArrayList<JavaStepDefinition>();


    private String toType(StepType type) {
        switch (type) {
            case GIVEN:
                return "Given";
            case WHEN:
                return "When";
            case THEN:
                return "Then";
        }
        return "";
    }

    public void put(final JavaStepDefinition def) {
        final Set<ParametrizedString> parametrizedStrings = def.toPString();
        for (ParametrizedString parametrizedString : parametrizedStrings) {
            final StepType annotationType = def.getAnnotationType();
            final String stringWithoutIdentifiers = parametrizedString.toStringWithoutIdentifiers();
            final String[] split = String.format("%s %s", toType(annotationType), stringWithoutIdentifiers).split(
                    "[ \t\f]+");
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
        String reallyFing = toFind;
        int rulezzz = reallyFing.indexOf("IntellijIdeaRulezzz");
        if (rulezzz >= 0) {
            reallyFing = reallyFing.substring(0, rulezzz);
        }
        String[] split = reallyFing.split("[ \t\f]+");
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
        if (it >= split.length) {
            if (strict && leafToken != null) {
                return Collections.singletonList(leafToken);
            }
//            if (!strict) {
//                return getAll();
//            }
//
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
