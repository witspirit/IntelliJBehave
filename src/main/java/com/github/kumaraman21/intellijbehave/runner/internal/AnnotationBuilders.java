package com.github.kumaraman21.intellijbehave.runner.internal;

import org.apache.commons.lang3.Validate;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.configuration.AnnotationBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Helper class for {@link AnnotationBuilder}s which provides to load expected builder without storing the rest
 * of them on classpath of an opened project.
 */
public final class AnnotationBuilders {

    private static final BuilderFactoryImpl DEFAULT_FACTORY = new BuilderFactoryImpl(
        ClassNames.DEFAULT_ANNOTATION_CLASS_NAME,
        ClassNames.DEFAULT_BUILDER_CLASS_NAME
    );

    private static final List<BuilderFactoryImpl> FACTORIES = List.of(
        new BuilderFactoryImpl(ClassNames.SPRING_ANNOTATION_CLASS_NAME, ClassNames.SPRING_BUILDER_CLASS_NAME),
        new BuilderFactoryImpl(ClassNames.NEEDLE_ANNOTATION_CLASS_NAME, ClassNames.NEEDLE_BUILDER_CLASS_NAME),
        new BuilderFactoryImpl(ClassNames.GUICE_ANNOTATION_CLASS_NAME, ClassNames.GUICE_BUILDER_CLASS_NAME),
        new BuilderFactoryImpl(ClassNames.WELD_ANNOTATION_CLASS_NAME, ClassNames.WELD_BUILDER_CLASS_NAME),
        new BuilderFactoryImpl(ClassNames.PICO_ANNOTATION_CLASS_NAME, ClassNames.PICO_BUILDER_CLASS_NAME)
    );

    private AnnotationBuilders() {
        // utility class
    }

    /**
     * Finds the proper builder factory by the annotation on the given class.
     *
     * <p>This method considers that the correct annotated class contains at least one
     * {@link org.jbehave.core.annotations.UsingEmbedder} annotation and also may contain additional extensional
     * annotations, for example {@code @UsingSpring}, {@code @UsingGuice}, etc.</p>
     *
     * <p>If class contains multiple different frameworks annotations, this method will return a factory for the
     * first match</p>
     *
     * @param annotatedClass class to get builder for
     * @return {@link AnnotationBuilder} instance
     * @throws IllegalArgumentException if the given class does not mark with @UsingEmbedder annotation
     */
    public static AnnotationBuilderFactory getFactory(@NotNull Class<?> annotatedClass) {
        Validate.isTrue(annotatedClass.isAnnotationPresent(UsingEmbedder.class),
            "Provided class must be annotated with @UsingEmbedder annotation");

        Set<String> annotationsList = Arrays.stream(annotatedClass.getAnnotations())
            .map(annotation -> annotation.annotationType().getName())
            .collect(Collectors.toSet());

        for (BuilderFactoryImpl factory : FACTORIES) {
            if (annotationsList.contains(factory.annotationClassName())) {
                return factory;
            }
        }
        return DEFAULT_FACTORY;
    }

    /**
     * Creates an instance of {@link AnnotationBuilder} for the given class using the specified class loader.
     *
     * @param annotatedClass class to get builder for
     * @param classLoader    class loader to load the builder class
     * @return {@link AnnotationBuilder} instance
     */
    public static AnnotationBuilder create(@NotNull Class<?> annotatedClass, @NotNull ClassLoader classLoader) {
        return getFactory(annotatedClass).createBuilder(annotatedClass, classLoader);
    }

    public interface AnnotationBuilderFactory {

        /**
         * Creates an instance of {@link AnnotationBuilder} for the given class with annotations which is supported by
         * this factory.
         *
         * @param annotatedClass class to get builder for
         * @param classLoader    class loader to load the builder class
         * @return {@link AnnotationBuilder} instance
         */
        AnnotationBuilder createBuilder(@NotNull Class<?> annotatedClass, @NotNull ClassLoader classLoader);
    }

    private record BuilderFactoryImpl(String annotationClassName, String builderClassName)
        implements AnnotationBuilderFactory {

        @Override
        public AnnotationBuilder createBuilder(@NotNull Class<?> annotatedClass, @NotNull ClassLoader classLoader) {
            Thread.currentThread().setContextClassLoader(classLoader);
            try {
                return classLoader.loadClass(builderClassName)
                    .asSubclass(AnnotationBuilder.class)
                    .getConstructor(Class.class)
                    .newInstance(annotatedClass);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to load annotation builder for " + builderClassName, e);
            }
        }
    }

    private static final class ClassNames {

        // Annotations
        static final String DEFAULT_ANNOTATION_CLASS_NAME = "org.jbehave.core.annotations.UsingEmbedder";
        static final String SPRING_ANNOTATION_CLASS_NAME = "org.jbehave.core.annotations.spring.UsingSpring";
        static final String NEEDLE_ANNOTATION_CLASS_NAME = "org.jbehave.core.annotations.needle.UsingNeedle";
        static final String GUICE_ANNOTATION_CLASS_NAME = "org.jbehave.core.annotations.guice.UsingGuice";
        static final String WELD_ANNOTATION_CLASS_NAME = "org.jbehave.core.annotations.weld.UsingWeld";
        static final String PICO_ANNOTATION_CLASS_NAME = "org.jbehave.core.annotations.pico.UsingPico";

        // Builders
        static final String DEFAULT_BUILDER_CLASS_NAME = "org.jbehave.core.configuration.AnnotationBuilder";
        static final String SPRING_BUILDER_CLASS_NAME = "org.jbehave.core.configuration.spring.SpringAnnotationBuilder";
        static final String NEEDLE_BUILDER_CLASS_NAME = "org.jbehave.core.configuration.needle.NeedleAnnotationBuilder";
        static final String GUICE_BUILDER_CLASS_NAME = "org.jbehave.core.configuration.guice.GuiceAnnotationBuilder";
        static final String WELD_BUILDER_CLASS_NAME = "org.jbehave.core.configuration.weld.WeldAnnotationBuilder";
        static final String PICO_BUILDER_CLASS_NAME = "org.jbehave.core.configuration.pico.PicoAnnotationBuilder";
    }
}
