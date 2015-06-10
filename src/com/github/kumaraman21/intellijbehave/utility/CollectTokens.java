package com.github.kumaraman21.intellijbehave.utility;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by DeBritoD on 01.06.2015.
 */
public class CollectTokens<V> implements ITokenMapVisitor<V> {
    private Stack<ParametrizedToken> tokens = new Stack<ParametrizedToken>();

    @Override
    public void found(List<V> concerned) {

    }

    @Override
    public void found(V leafToken) {

    }

    @Override
    public void pushParameter(ParametrizedToken parameter) {
        parameter.setParameter();
        tokens.push(parameter);
    }

    @Override
    public void popParameter() {
        tokens.pop();
    }

    @Override
    public void pushToken(ParametrizedToken token) {
        token.unsetParameter();
        tokens.push(token);
    }

    @Override
    public void popToken() {
        tokens.pop();
    }

    public Iterator<ParametrizedToken> result() {
        return tokens.iterator();
    }

}
