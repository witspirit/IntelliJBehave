package com.github.kumaraman21.intellijbehave.resolver;

import static com.intellij.openapi.application.ReadAction.compute;
import static com.intellij.openapi.application.ReadAction.run;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClassOwner;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Integration test for {@link StepDefinitionAnnotationConverter}.
 */
public class StepDefinitionAnnotationConverterTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/resolver/stepdefannotationconverter";
    }

    @BeforeEach
    void setUp() {
        copySrcDirectoryToProject();
    }

    @Test
    void convertsEmptyAnnotationsArrayToEmptySet() {
        //When
        var annotations = StepDefinitionAnnotationConverter.convertFrom(PsiAnnotation.EMPTY_ARRAY);

        //Then
        assertThat(annotations).isEmpty();
    }

    @Test
    void convertsSingleGivenAnnotationToSingleSet() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var givenStepDef = stepDefsClass.findMethodsByName("givenStepDefWithOneAnnotationAttribute", false)[0];
            return givenStepDef.getAnnotations();
        });

        //When
        var annotations = StepDefinitionAnnotationConverter.convertFrom(psiAnnotations);

        //Then
        var attribute = new StepDefinitionAnnotation(StepType.GIVEN, "one given $string", psiAnnotations[0]);
        assertThat(annotations).containsExactly(attribute);
    }

    @Test
    void convertsSingleWhenAnnotationToSingleSet() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var whenStepDef = stepDefsClass.findMethodsByName("whenStepDefWithOneAnnotationAttribute", false)[0];
            return whenStepDef.getAnnotations();
        });

        //When
        var annotations = StepDefinitionAnnotationConverter.convertFrom(psiAnnotations);

        //Then
        var attribute = new StepDefinitionAnnotation(StepType.WHEN, "one when $string", psiAnnotations[0]);
        assertThat(annotations).containsExactly(attribute);
    }

    @Test
    void convertsSingleThenAnnotationToSingleSet() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var thenStepDef = stepDefsClass.findMethodsByName("thenStepDefWithOneAnnotationAttribute", false)[0];
            return thenStepDef.getAnnotations();
        });

        //When
        var annotations = StepDefinitionAnnotationConverter.convertFrom(psiAnnotations);

        //Then
        var attribute = new StepDefinitionAnnotation(StepType.THEN, "one then $string", psiAnnotations[0]);
        assertThat(annotations).containsExactly(attribute);
    }

    @Test
    void convertsSingleAliasAnnotationToSingleSet() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var aliasStepDef = stepDefsClass.findMethodsByName("aliasStepDefWithOneAnnotationAttribute", false)[0];
            return aliasStepDef.getAnnotations();
        });

        //When
        var annotations = StepDefinitionAnnotationConverter.convertFrom(psiAnnotations);

        //Then
        var attribute = new StepDefinitionAnnotation(null, "one alias $string", psiAnnotations[0]);
        assertThat(annotations).containsExactly(attribute);
    }

    @Test
    void convertsSingleAliasesAnnotationWithSingleAttributeToSingleSet() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var aliasesStepDef = stepDefsClass.findMethodsByName("aliasesStepDefWithOneAnnotationAttribute", false)[0];
            return aliasesStepDef.getAnnotations();
        });

        //When
        var annotations = compute(() -> StepDefinitionAnnotationConverter.convertFrom(psiAnnotations));

        //Then
        var attribute = new StepDefinitionAnnotation(null, "one aliases $string", psiAnnotations[0]);
        assertThat(annotations).containsExactly(attribute);
    }

    @Test
    void convertsSingleAliasesAnnotationWithMultipleAttributesToMultiSet() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var aliasesStepDef = stepDefsClass.findMethodsByName("aliasesStepDefWithMultipleAnnotationAttributes", false)[0];
            return aliasesStepDef.getAnnotations();
        });

        //When
        var annotations = compute(() -> StepDefinitionAnnotationConverter.convertFrom(psiAnnotations));

        //Then
        var firstAttribute = new StepDefinitionAnnotation(null, "multiple aliases $string", psiAnnotations[0]);
        var secondAttribute = new StepDefinitionAnnotation(null, "aliases something else", psiAnnotations[0]);
        assertThat(annotations).containsExactlyInAnyOrder(firstAttribute, secondAttribute);
    }

//    @Test
//    void convertsMixedStepTypedAndAliasesAnnotationsWhereTheStepTypeAnnotationComesFirst() {
//        //Given
//        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
//        var psiAnnotations = compute(() -> {
//            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
//            var whenAliasesStepDef = stepDefsClass.findMethodsByName("whenAndAliasesStepDef", false)[0];
//            return whenAliasesStepDef.getAnnotations();
//        });
//
//        //When
//        var annotations = compute(() -> StepDefinitionAnnotationConverter.convertFrom(psiAnnotations));
//
//        //Then
//        var whenAttribute = new StepDefinitionAnnotation(StepType.WHEN, "mixed when aliases $string", psiAnnotations[0]);
//        var aliasOneAttribute = new StepDefinitionAnnotation(null, "mixed when aliases one $string", psiAnnotations[1]);
//        var aliasTwoAttribute = new StepDefinitionAnnotation(null, "mixed when aliases two", psiAnnotations[1]);
//
//        assertThat(annotations).containsExactlyInAnyOrder(whenAttribute, aliasOneAttribute, aliasTwoAttribute);
//    }

    @Test
    void convertsMixedAliasesAndStepTypedAnnotationsWhereTheAliasesAnnotationComesFirst() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = compute(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var aliasesWhenStepDef = stepDefsClass.findMethodsByName("aliasesAndWhenStepDef", false)[0];
            return aliasesWhenStepDef.getAnnotations();
        });

        //When
        var annotations = compute(() -> StepDefinitionAnnotationConverter.convertFrom(psiAnnotations));

        //Then
        var aliasOneAttribute = new StepDefinitionAnnotation(null, "mixed aliases when one $string", psiAnnotations[0]);
        var aliasTwoAttribute = new StepDefinitionAnnotation(null, "mixed aliases when two", psiAnnotations[0]);
        var whenAttribute = new StepDefinitionAnnotation(StepType.WHEN, "mixed aliases when $string", psiAnnotations[1]);

        assertThat(annotations).containsExactlyInAnyOrder(whenAttribute, aliasOneAttribute, aliasTwoAttribute);
    }

//    @Test
//    void convertsMultipleVariousAnnotations() {
//        //Given
//        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
//        var psiAnnotations = new ArrayList<PsiAnnotation>();
//        run(() -> {
//            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
//            var givenStepDef = stepDefsClass.findMethodsByName("givenStepDefWithOneAnnotationAttribute", false)[0];
//            var whenStepDef = stepDefsClass.findMethodsByName("whenStepDefWithOneAnnotationAttribute", false)[0];
//            var thenStepDef = stepDefsClass.findMethodsByName("thenStepDefWithOneAnnotationAttribute", false)[0];
//            var aliasStepDef = stepDefsClass.findMethodsByName("aliasStepDefWithOneAnnotationAttribute", false)[0];
//            var aliasesOneStepDef = stepDefsClass.findMethodsByName("aliasesStepDefWithOneAnnotationAttribute", false)[0];
//            var aliasesMultiStepDef = stepDefsClass.findMethodsByName("aliasesStepDefWithMultipleAnnotationAttributes", false)[0];
//
//            psiAnnotations.addAll(Arrays.asList(givenStepDef.getAnnotations()));
//            psiAnnotations.addAll(Arrays.asList(whenStepDef.getAnnotations()));
//            psiAnnotations.addAll(Arrays.asList(thenStepDef.getAnnotations()));
//            psiAnnotations.addAll(Arrays.asList(aliasStepDef.getAnnotations()));
//            psiAnnotations.addAll(Arrays.asList(aliasesOneStepDef.getAnnotations()));
//            psiAnnotations.addAll(Arrays.asList(aliasesMultiStepDef.getAnnotations()));
//        });
//
//        //When
//        var annotations = compute(() -> StepDefinitionAnnotationConverter.convertFrom(psiAnnotations.toArray(PsiAnnotation.EMPTY_ARRAY)));
//
//        //Then
//        var givenAttribute = new StepDefinitionAnnotation(StepType.GIVEN, "one given $string", psiAnnotations.get(0));
//        var whenAttribute = new StepDefinitionAnnotation(StepType.WHEN, "one when $string", psiAnnotations.get(1));
//        var thenAttribute = new StepDefinitionAnnotation(StepType.THEN, "one then $string", psiAnnotations.get(2));
//        var aliasAttribute = new StepDefinitionAnnotation(null, "one alias $string", psiAnnotations.get(3));
//        var aliasesOneAttribute = new StepDefinitionAnnotation(null, "one aliases $string", psiAnnotations.get(4));
//        var aliasesMultiFirstAttribute = new StepDefinitionAnnotation(null, "multiple aliases $string", psiAnnotations.get(5));
//        var aliasesMultiSecondAttribute = new StepDefinitionAnnotation(null, "aliases something else", psiAnnotations.get(5));
//
//        assertThat(annotations).containsExactlyInAnyOrder(
//            givenAttribute,
//            whenAttribute,
//            thenAttribute,
//            aliasAttribute,
//            aliasesOneAttribute,
//            aliasesMultiFirstAttribute,
//            aliasesMultiSecondAttribute
//        );
//    }

    @Test
    void doesntConvertAnnotationsHavingNoAttributes() {
        //Given
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var psiAnnotations = new ArrayList<PsiAnnotation>();
        run(() -> {
            var stepDefsClass = ((PsiClassOwner) stepDefFile).getClasses()[0];
            var givenStepDef = stepDefsClass.findMethodsByName("givenStepDefWithoutAnnotationAttribute", false)[0];
            var whenStepDef = stepDefsClass.findMethodsByName("whenStepDefWithoutAnnotationAttribute", false)[0];
            var thenStepDef = stepDefsClass.findMethodsByName("thenStepDefWithoutAnnotationAttribute", false)[0];
            var aliasStepDef = stepDefsClass.findMethodsByName("aliasStepDefWithoutAnnotationAttribute", false)[0];
            var aliasesStepDef = stepDefsClass.findMethodsByName("aliasesStepDefWithoutAnnotationAttribute", false)[0];

            psiAnnotations.addAll(Arrays.asList(givenStepDef.getAnnotations()));
            psiAnnotations.addAll(Arrays.asList(whenStepDef.getAnnotations()));
            psiAnnotations.addAll(Arrays.asList(thenStepDef.getAnnotations()));
            psiAnnotations.addAll(Arrays.asList(aliasStepDef.getAnnotations()));
            psiAnnotations.addAll(Arrays.asList(aliasesStepDef.getAnnotations()));
        });

        //When
        var annotations = compute(() -> StepDefinitionAnnotationConverter.convertFrom(psiAnnotations.toArray(PsiAnnotation.EMPTY_ARRAY)));

        //Then
        assertThat(annotations).isEmpty();
    }
}
