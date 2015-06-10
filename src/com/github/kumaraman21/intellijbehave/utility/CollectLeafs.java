package com.github.kumaraman21.intellijbehave.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DeBritoD on 01.06.2015.
 */
public class CollectLeafs<V> implements ITokenMapVisitor<V> {
    private Set<V> result = new HashSet<V>();

    @Override
    public void found(List<V> concerned) {
        result.addAll(concerned);
    }

    @Override
    public void found(V leafToken) {
        result.add(leafToken);
    }

    @Override
    public void pushParameter(ParametrizedToken parameter) {

    }

    @Override
    public void popParameter() {

    }

    @Override
    public void pushToken(ParametrizedToken next) {

    }

    @Override
    public void popToken() {

    }

    public List<V> getResult() {
        List<V> retval = new ArrayList<V>(result.size());
        retval.addAll(result);
        return retval;
    }
}
