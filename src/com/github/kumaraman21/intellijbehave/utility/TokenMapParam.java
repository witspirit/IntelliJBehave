package com.github.kumaraman21.intellijbehave.utility;

import java.util.List;

/**
 * Created by DeBritoD on 28.05.2015.
 */
public class TokenMapParam<V> extends TokenMap<V> {
    public TokenMapParam() {
    }

    protected boolean get(final List<ParametrizedToken> split, final int count, final ITokenMapVisitor<V> visitor,
                          final boolean strict) {
        visitor.pushParameter(split.get(count - 1));
        if (count < split.size()) {
            ParametrizedToken nextToken = split.get(count);
            final String next = unwrapInject(nextToken.getValue());
            TokenMap<V> tokenMap = getNextTokens().get(next);
            visitor.pushToken(nextToken);
            if (tokenMap != null && tokenMap.get(split, count + 1, visitor, strict)) {
                return true;
            }
            visitor.popToken();
            if (get(split, count + 1, visitor, strict)) {
                return true;
            } else {
                visitor.popParameter();
                return false;
            }
        }
        if (strict && getLeafToken() != null && count >= split.size()) {
            visitor.found(getLeafToken());
            return true;
        }
        visitor.popParameter();
        return false;
    }
}
