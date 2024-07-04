package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.JBehaveSupportTestBase;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.junit5.RunInEdt;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link JavaStepDefinition}.
 */
@RunInEdt
class JavaStepDefinitionTest extends JBehaveSupportTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "";
    }

    //supportsStepAndMatches

    @Test
    void shouldMatchStepText() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @The<caret>n(value = "the price should be $price", priority = 500)
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        var storyFile = getFixture().configureByText("matches_step_text.story", """
            Scenario: Product price

            Then the <caret>price should be 200
            """);

        var step = (JBehaveStep) storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        assertThat(new JavaStepDefinition(annotation).supportsStepAndMatches(step, "the price should be 200")).isTrue();
    }

    @Test
    void shouldNotMatchStepText() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @The<caret>n(value = "the price should be $price", priority = 500)
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        var storyFile = getFixture().configureByText("matches_step_text.story", """
            Scenario: Product price

            Then the <caret>price should be 200
            """);

        var step = (JBehaveStep) storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        assertThat(new JavaStepDefinition(annotation).supportsStepAndMatches(step, "the price is 200")).isFalse();
    }

    @Test
    void shouldSupportStep() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @The<caret>n(value = "the price should be $price", priority = 500)
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        var storyFile = getFixture().configureByText("supports_step.story", """
            Scenario: Product price

            Then the <caret>price should be 200
            """);

        var step = (JBehaveStep) storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        assertThat(new JavaStepDefinition(annotation).supportsStepAndMatches(step, "the price should be 200")).isTrue();
    }

    @Test
    void shouldNotSupportStep() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Given;

            class JavaStepDefinition {
                @Giv<caret>en(value = "the price should be $price", priority = 500)
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        var storyFile = getFixture().configureByText("supports_step.story", """
            Scenario: Product price

            Then the <caret>price should be 200
            """);

        var step = (JBehaveStep) storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        assertThat(new JavaStepDefinition(annotation).supportsStepAndMatches(step, "the price should be 200")).isFalse();
    }

    @Test
    void shouldNotSupportStepForAliasAnnotation() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Aliases;

            class JavaStepDefinition {
                @Alia<caret>ses(values = {
                    "the price should be $price",
                    "the cost should be $price"
                })
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        var storyFile = getFixture().configureByText("supports_step.story", """
            Scenario: Product price

            Then the <caret>price should be 200
            """);

        var step = (JBehaveStep) storyFile.findElementAt(getFixture().getCaretOffset()).getParent();

        assertThat(new JavaStepDefinition(annotation).supportsStepAndMatches(step, "the price should be 200")).isFalse();
    }

    //getAnnotationTextFor

    @Test
    void shouldGetAnnotationTextWhenThereIsOneSuchText() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Alias;
                        
            class JavaStepDefinition {
                @Alia<caret>s("the price should be $price")
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotationTextFor("the price should be 200"))
            .isEqualTo("the price should be $price");
    }

    @Test
    void shouldGetFirstMatchingAnnotationTextFromMultiple() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Aliases;
                        
            class JavaStepDefinition {
                @Alia<caret>ses(values = {
                    "the price should be $price",
                    "the cost should be $price"
                })
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotationTextFor("the cost should be 200"))
            .isEqualTo("the cost should be $price");
    }

    @Test
    void shouldReturnNoAnnotationTextWhenNotMatching() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Alias;
                        
            class JavaStepDefinition {
                @Alia<caret>ses(values = {
                    "the price should be $price",
                    "the cost should be $price"
                })
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotationTextFor("non matching text")).isNull();
    }

    //getAnnotatedMethod

    @Test
    void shouldReturnTheAnnotatedMethod() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @Th<caret>en("the price should be $price")
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotatedMethod()).extracting(PsiMethod::getName).isEqualTo("steDefMethod");
    }

    @Test
    void shouldReturnNullWhenThereIsNoParentMethod() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @Th<caret>en("the price should be $price")
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotatedMethod()).isNull();
    }

    //getAnnotationType

    @Test
    void shouldGetAnnotationType() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @Th<caret>en("the price should be $price")
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotationType()).isEqualTo(StepType.THEN);
    }

    @Test
    void shouldReturnNullForNonMappedAnnotationType() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Alias;

            class JavaStepDefinition {
                @Ali<caret>as("the price should be $price")
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotationType()).isNull();
    }

//    @Test
//    void shouldReturnNullAnnotationTypeForNoAnnotation() {
//    }

    //getAnnotationPriority

    @Test
    void shouldGetAnnotationPriority() {
        var stepDefFile = getFixture().configureByText("JavaStepDefinition.java", """
            import org.jbehave.core.annotations.Then;

            class JavaStepDefinition {
                @The<caret>n(value = "the price should be $price", priority = 500)
                void steDefMethod(int price) {
                }
            }
            """);

        var annotation = (PsiAnnotation) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(new JavaStepDefinition(annotation).getAnnotationPriority()).isEqualTo(500);
    }

//    @Test
//    void shouldReturnFallbackValueForNoAnnotation() {
//    }
}
