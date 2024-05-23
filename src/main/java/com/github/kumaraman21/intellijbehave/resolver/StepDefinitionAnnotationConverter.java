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

import com.intellij.psi.*;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.steps.PatternVariantBuilder;
import org.jbehave.core.steps.StepType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static org.apache.commons.lang.StringUtils.*;

public final class StepDefinitionAnnotationConverter {

    public static Set<StepDefinitionAnnotation> convertFrom(PsiAnnotation[] annotations) {
        Set<StepDefinitionAnnotation> res = null;

        StepType stepType = null;

        for (PsiAnnotation annotation : annotations) {
            String annotationQualifiedName = annotation.getQualifiedName();
            // Given, When, Then
            final PsiNameValuePair[] attributes = annotation.getParameterList().getAttributes();

            // When there are no attributes for the annotation, we got nothing to do here
            if (attributes.length > 0) {
                if (ANNOTATION_TO_STEP_TYPE_MAPPING.containsKey(annotationQualifiedName)) {
                    stepType = ANNOTATION_TO_STEP_TYPE_MAPPING.get(annotationQualifiedName);
                    String annotationText = getTextFromValue(attributes[0].getValue());
                    if (res == null) {
                        res = new HashSet<>();
                    }
                    res.addAll(getPatternVariants(stepType, annotationText, annotation));
                } else if (annotationQualifiedName != null) {
                    if (annotationQualifiedName.equals(Alias.class.getName())) {
                        String annotationText = getTextFromValue(attributes[0].getValue());
                        if (res == null) {
                            res = new HashSet<>();
                        }
                        res.addAll(getPatternVariants(stepType, annotationText, annotation));
                    } else if (annotationQualifiedName.equals(Aliases.class.getName())) {
                        PsiAnnotationMemberValue attributeValue = attributes[0].getValue();
                        if (attributeValue != null) {
                            PsiElement[] values = attributeValue.getChildren();
                            for (PsiElement value : values) {
                                if (value instanceof PsiLiteral) {
                                    String annotationText = getTextFromValue(value);
                                    if (res == null) {
                                        res = new HashSet<>();
                                    }
                                    res.addAll(getPatternVariants(stepType, annotationText, annotation));
                                }
                            }
                        }
                    }
                }
            }
        }
        return res == null ? Collections.emptySet() : res;
    }

    private static Set<StepDefinitionAnnotation> getPatternVariants(final StepType stepType, String annotationText, final PsiAnnotation annotation) {
        return new PatternVariantBuilder(annotationText)
                .allVariants()
                .stream()
                .map(variant -> new StepDefinitionAnnotation(stepType, variant, annotation))
                .collect(Collectors.toSet());
    }

    private static String getTextFromValue(PsiElement value) {
        return remove(removeStart(removeEnd(value.getText(), "\""), "\""), "\\");
    }
}
