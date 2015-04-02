package com.github.kumaraman21.intellijbehave.utility;

import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import org.jbehave.core.steps.StepType;

import java.util.*;

/**
 * Created by DeBritoD on 27.03.2015.
 */
public class TokenMap {
    private Map<String, TokenMap> nextTokens = new HashMap<String, TokenMap>();
    private List<JavaStepDefinition> leafTokens = new ArrayList<JavaStepDefinition>();


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

    public void put(JavaStepDefinition def) {
        Set<ParametrizedString> parametrizedStrings = def.toPString();
        for (ParametrizedString parametrizedString : parametrizedStrings) {
            StepType annotationType = def.getAnnotationType();
            StringTokenizer stringTokenizer = new StringTokenizer(
                    String.format("%s %s", toType(annotationType), parametrizedString.toStringWithoutIdentifiers()));
            put(stringTokenizer, def);
        }
    }

    public void put(StringTokenizer tokens, JavaStepDefinition def) {
        leafTokens.add(def);
        if (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            TokenMap tokenMap = nextTokens.get(token);
            if (tokenMap == null) {
                tokenMap = new TokenMap();
                nextTokens.put(token, tokenMap);
            }
            tokenMap.put(tokens, def);
        }
    }

    public List<JavaStepDefinition> getConcerned(String toFind) {
        String reallyFing = toFind;
        int rulezzz = reallyFing.indexOf("IntellijIdeaRulezzz");
        if (rulezzz >= 0) {
            reallyFing = reallyFing.substring(0, rulezzz);
        }
        String[] split = reallyFing.split("[ \t\f]+");
        return getConcerned(split, 0);
    }

    private List<JavaStepDefinition> getConcerned(String[] split, int count) {
        final List<JavaStepDefinition> result = new ArrayList<JavaStepDefinition>();
        int it = count;
        while (it < split.length) {
            String next = split[it];
            TokenMap tokenMap = nextTokens.get(next);
            if (tokenMap != null) {
                result.addAll(tokenMap.getConcerned(split, it));
                return result;
            }
            ++it;
        }
        if (it >= split.length) {
            result.addAll(leafTokens);
            return result;
        }
        return result;
    }

    public Collection<JavaStepDefinition> getAll() {
        return leafTokens;
    }

    public boolean isEmpty() {
        return nextTokens.isEmpty() && leafTokens.isEmpty();
    }
}
