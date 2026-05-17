package com.github.kumaraman21.intellijbehave;

import com.intellij.DynamicBundle;

import java.util.function.Supplier;

public final class JBehaveBundle extends DynamicBundle {

    private static final String BUNDLE = "messages.JBehaveBundle";
    private static final JBehaveBundle INSTANCE = new JBehaveBundle();

    private JBehaveBundle() {
        super(BUNDLE);
    }

    public static String message(String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    public static Supplier<String> lazyMessage(String key, Object... params) {
        return INSTANCE.getLazyMessage(key, params);
    }
}
