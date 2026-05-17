package com.github.kumaraman21.intellijbehave.runner;

public class JBehaveRunException extends RuntimeException {

    public JBehaveRunException(String message) {
        super(message);
    }

    public JBehaveRunException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
