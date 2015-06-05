package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.utility.TokenMap;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by DeBritoD on 13.04.2015.
 */
class TokenMapManager {
    private TokenMap tokenMap = new TokenMap();
    private AtomicBoolean needsUpdate = new AtomicBoolean(true);
}
