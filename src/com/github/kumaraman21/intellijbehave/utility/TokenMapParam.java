package com.github.kumaraman21.intellijbehave.utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by DeBritoD on 28.05.2015.
 */
public class TokenMapParam<V> extends TokenMap<V> {
    protected List<V> get(final List<String> split, final int count, final boolean strict) {
        int currentCount = count;
        while (currentCount < split.size()) {
            final String next = unwrapInject(split.get(currentCount));
            TokenMap<V> tokenMap = getNextTokens().get(next);
            if (tokenMap != null) {
                final List<V> concerned = tokenMap.get(split, currentCount + 1, strict);
                if (!concerned.isEmpty()) {
                    return concerned;
                }
            }
            ++currentCount;
        }
        final V leafToken = getLeafToken();
        if (currentCount >= split.size() && strict && leafToken != null) {
            return Collections.singletonList(leafToken);
        }
        return Collections.emptyList();
    }

}
