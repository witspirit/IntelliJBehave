package com.github.kumaraman21.intellijbehave.utility;

import java.util.List;

/**
 * Created by DeBritoD on 01.06.2015.
 */
public interface ITokenMapVisitor<V> {
    void found(List<V> concerned);

    void found(V leafToken);

    void pushParameter(ParametrizedToken parameter);

    void popParameter();

    void pushToken(ParametrizedToken next);

    void popToken();

}
