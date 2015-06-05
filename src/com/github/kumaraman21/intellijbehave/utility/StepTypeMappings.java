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
package com.github.kumaraman21.intellijbehave.utility;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.StepType;

import java.util.HashMap;
import java.util.Map;

public enum StepTypeMappings {
    ;

    private static final Map<StepType, String> STEP_TYPE_TO_ANNOTATION_MAPPING = new HashMap<StepType, String>();

    static {
        STEP_TYPE_TO_ANNOTATION_MAPPING.put(StepType.GIVEN, Given.class.getName());
        STEP_TYPE_TO_ANNOTATION_MAPPING.put(StepType.WHEN, When.class.getName());
        STEP_TYPE_TO_ANNOTATION_MAPPING.put(StepType.THEN, Then.class.getName());
    }

    public static final Map<String, StepType> ANNOTATION_TO_STEP_TYPE_MAPPING = new HashMap<String, StepType>();

    static {
        ANNOTATION_TO_STEP_TYPE_MAPPING.put(Given.class.getName(), StepType.GIVEN);
        ANNOTATION_TO_STEP_TYPE_MAPPING.put(When.class.getName(), StepType.WHEN);
        ANNOTATION_TO_STEP_TYPE_MAPPING.put(Then.class.getName(), StepType.THEN);
    }

}
