package com.github.kumaraman21.intellijbehave.runner.internal;

import com.github.kumaraman21.intellijbehave.runner.Arguments;
import org.jbehave.core.embedder.Embedder;

public interface EmbedderFactory {

    /**
     * Creates an instance of {@link Embedder} class and configures it using provided arguments.
     *
     * @param arguments JBehave story runner arguments which contains embedder settings
     * @return an instance of {@link Embedder} class
     */
    Embedder createEmbedder(Arguments arguments);
}
