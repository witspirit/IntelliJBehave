package com.github.kumaraman21.intellijbehave.runner;

import com.github.kumaraman21.intellijbehave.execution.JBehaveConfigurationOptions;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.guice.UsingGuice;
import org.jbehave.core.annotations.needle.UsingNeedle;
import org.jbehave.core.annotations.pico.UsingPico;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.annotations.weld.UsingWeld;

import java.util.List;

public final class EmbedderSamples {

    public static Iterable<String> provideDefaultEmbedder() {
        return List.of(JBehaveConfigurationOptions.DEFAULT_EMBEDDER_CLASS_NAME);
    }

    public static Iterable<String> provideCustomEmbedders() {
        return List.of(
            MyInjectableEmbedder.class.getName(),
            MyConfigurableEmbedder.class.getName(),
            MyInjectableEmbedder.class.getName(),
            MyConfigurableEmbedder.class.getName(),
            MyDefaultAnnotatedEmbedder.class.getName(),
            MySpringAnnotatedEmbedder.class.getName(),
            MyGuiceAnnotatedEmbedder.class.getName(),
            MyNeedleAnnotatedEmbedder.class.getName(),
            MyPicoAnnotatedEmbedder.class.getName(),
            MyWeldAnnotatedEmbedder.class.getName()
        );
    }

    public static class MyInjectableEmbedder extends InjectableEmbedder {

        @Override
        public void run() {

        }
    }

    public static class MyConfigurableEmbedder extends ConfigurableEmbedder {

        @Override
        public void run() {

        }
    }

    @UsingEmbedder
    public static class MyDefaultAnnotatedEmbedder {

    }

    @UsingSpring
    @UsingEmbedder
    public static class MySpringAnnotatedEmbedder {

    }

    @UsingGuice
    @UsingEmbedder
    public static class MyGuiceAnnotatedEmbedder {

    }

    @UsingNeedle
    @UsingEmbedder
    public static class MyNeedleAnnotatedEmbedder {

    }

    @UsingPico
    @UsingEmbedder
    public static class MyPicoAnnotatedEmbedder {

    }

    @UsingWeld
    @UsingEmbedder
    public static class MyWeldAnnotatedEmbedder {

    }

    public class MyEmbedderWithMonitor extends ConfigurableEmbedder {

        @Override
        public void run() {

        }
    }
}
