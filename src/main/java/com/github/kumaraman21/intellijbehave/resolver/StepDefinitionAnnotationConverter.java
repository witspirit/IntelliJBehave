/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.resolver;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static com.intellij.openapi.application.ReadAction.compute;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

import com.github.kumaraman21.intellijbehave.jbehave.core.steps.PatternVariantBuilder;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteral;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class StepDefinitionAnnotationConverter {

    /**
     * Converts each of the input annotations to a {@link StepDefinitionAnnotation} by extracting
     * information (the step type and annotation text) from them.
     *
     * <h3>Limitations</h3>
     * Currently, there are a couple of cases that are either not handled or handled incorrectly:
     * <ul>
     *     <li>When converting an @Alias or @Aliases annotation, the order of the input annotations matters.
     *     Having the step annotation first, i.e. {@code @When -> @Alias}, the {@code @Alias} annotation
     *     is created with the proper WHEN step type. If the order is switched ({@code @Alias -> @When}),
     *     the step type becomes null, and that step candidate is not code completed by
     *     {@link com.github.kumaraman21.intellijbehave.completion.StoryCompletionContributor}.</li>
     *     <li>The case when multiple of the {@code @Given/@When/@Then/@Alias/@Aliases} annotations
     *     are added to the same step definition method is not handled.</li>
     * </ul>
     *
     * @param annotations the annotations on a given step definition method
     */
    public static Set<StepDefinitionAnnotation> convertFrom(PsiAnnotation[] annotations) {
        //Refs and AnnotationsHolders are not created in this case
        if (annotations.length == 0) return Collections.emptySet();

        //Variable init

        final var result = new AnnotationsHolder();
        StepType stepType = null;
        var annotationQualifiedName = new Ref<String>();

        //Annotation processing

        for (PsiAnnotation annotation : annotations) {
            var attributes = compute(() -> {
                annotationQualifiedName.set(annotation.getQualifiedName());
                return annotation.getParameterList().getAttributes();
            });

            // When there are no attributes for the annotation, don't process this annotation
            if (attributes.length == 0) continue;

            //When the processed annotation is @Given, @When or @Then
            if (ANNOTATION_TO_STEP_TYPE_MAPPING.containsKey(annotationQualifiedName.get())) {
                stepType = ANNOTATION_TO_STEP_TYPE_MAPPING.get(annotationQualifiedName.get());
                String annotationText = getTextFromValue(() -> attributes[0].getValue());
                result.add(stepType, annotationText, annotation);
            } else if (!annotationQualifiedName.isNull()) {

                //When the processed annotation is @Alias
                if (annotationQualifiedName.get().equals(Alias.class.getName())) {
                    String annotationText = getTextFromValue(() -> attributes[0].getValue());
                    result.add(stepType, annotationText, annotation);
                }

                //When the processed annotation is @Aliases
                else if (annotationQualifiedName.get().equals(Aliases.class.getName())) {
                    PsiAnnotationMemberValue attributeValue = compute(() -> attributes[0].getValue());
                    if (attributeValue != null) {
                        PsiElement[] values = attributeValue.getChildren();
                        //Processes all specified alias values in the annotation attribute
                        for (PsiElement value : values) {
                            if (value instanceof PsiLiteral) {
                                String annotationText = getTextFromValue(() -> value);
                                result.add(stepType, annotationText, annotation);
                            }
                        }
                    }
                }
            }
        }

        return result.getAnnotations();
    }

    private static Set<StepDefinitionAnnotation> getPatternVariants(final StepType stepType, String annotationText, final PsiAnnotation annotation) {
        return new PatternVariantBuilder(annotationText)
            .allVariants()
            .stream()
            .map(variant -> new StepDefinitionAnnotation(stepType, variant, annotation))
            .collect(Collectors.toSet());
    }

    /**
     * By using a Supplier here, {@code compute()} calls can be deduplicated with call sites of this method.
     */
    private static String getTextFromValue(Supplier<PsiElement> value) {
        return remove(removeStart(removeEnd(compute(() -> value.get().getText()), "\""), "\""), "\\");
    }

    private static final class AnnotationsHolder {
        @Nullable
        private Set<StepDefinitionAnnotation> annotations;

        private Set<StepDefinitionAnnotation> getAnnotations() {
            return annotations == null ? Collections.emptySet() : annotations;
        }

        private void add(StepType stepType, String annotationText, PsiAnnotation annotation) {
            if (annotations == null) annotations = new HashSet<>();
            annotations.addAll(getPatternVariants(stepType, annotationText, annotation));
        }
    }
}
