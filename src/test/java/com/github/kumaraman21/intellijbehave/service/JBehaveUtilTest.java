package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.JBehaveSupportTestBase;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.junit5.RunInEdt;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Integration test for {@link JBehaveUtil}.
 */
@RunInEdt
class JBehaveUtilTest extends JBehaveSupportTestBase {
    @Nullable
    @Override
    protected String getTestDataPath() {
        return ""; //empty because it is not needed, but without overriding the method, the test setup fails
    }

    //isJBehaveStepAnnotation

    @Test
    void shouldBeJBehaveStepAnnotation() {
        var stepDefFile = getFixture().configureByText("JBehaveStepAnnotation.java", """
            import org.jbehave.core.annotations.Given;

            class JBehaveStepAnnotation {
                @Give<caret>n("")
                void stepDefMethod() {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.isJBehaveStepAnnotation((PsiAnnotation) annotation)).isTrue();
    }

    @Test
    void shouldNotBeJBehaveStepAnnotationForNonStepAnnotation() {
        var stepDefFile = getFixture().configureByText("JBehaveStepAnnotation.java", """
            import org.jbehave.core.annotations.Alias;

            class JBehaveStepAnnotation {
                @Ali<caret>as("")
                void stepDefMethod() {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.isJBehaveStepAnnotation((PsiAnnotation) annotation)).isFalse();
    }

    @Test
    void shouldNotBeJBehaveStepAnnotationForUnresolvedAnnotation() {
        var stepDefFile = getFixture().configureByText("JBehaveStepAnnotation.java", """
            class JBehaveStepAnnotation {
                @<caret>("")
                void stepDefMethod() {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();

        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.isJBehaveStepAnnotation((PsiAnnotation) annotation)).isFalse();
    }

    //isAnnotationOfClass

    @Test
    void shouldBeAnnotationOfClass() {
        var stepDefFile = getFixture().configureByText("JBehaveAnnotationOfClass.java", """
            import org.jbehave.core.annotations.Given;

            class JBehaveAnnotationOfClass {
                @Giv<caret>en("")
                void stepDefMethod() {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.isAnnotationOfClass((PsiAnnotation) annotation, Given.class)).isTrue();
    }

    @Test
    void shouldNotBeAnnotationOfClass() {
        var stepDefFile = getFixture().configureByText("JBehaveAnnotationOfClass.java", """
            import org.jbehave.core.annotations.Given;

            class JBehaveAnnotationOfClass {
                @Giv<caret>en("")
                void stepDefMethod() {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.isAnnotationOfClass((PsiAnnotation) annotation, When.class)).isFalse();
    }

    @Test
    void shouldNotBeAnnotationOfClassForUnresolvedAnnotation() {
        var stepDefFile = getFixture().configureByText("JBehaveAnnotationOfClass.java", """
            class JBehaveAnnotationOfClass {
                @<caret>("")
                void stepDefMethod() {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.isAnnotationOfClass((PsiAnnotation) annotation, When.class)).isFalse();
    }

    //isStepDefinition

    @Test
    void shouldBeStepDefinitionMethod() {
        var stepDefFile = getFixture().configureByText("StepDefinitionMethod.java", """
            import org.jbehave.core.annotations.Given;

            class StepDefinitionMethod {
                @Given("")
                void ste<caret>pDefMethod() {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.isStepDefinition((PsiMethod) method)).isTrue();
    }

    @Test
    void shouldNotBeStepDefinitionMethodWithoutStepAnnotation() {
        var stepDefFile = getFixture().configureByText("StepDefinitionMethod.java", """
            class StepDefinitionMethod {
                void ste<caret>pDefMethod() {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.isStepDefinition((PsiMethod) method)).isFalse();
    }

    @Test
    void shouldNotBeStepDefinitionMethodWithAllStepAnnotationsWithNullValue() {
        var stepDefFile = getFixture().configureByText("StepDefinitionMethod.java", """
            import org.jbehave.core.annotations.Given;

            class StepDefinitionMethod {
                @Given
                void ste<caret>pDefMethod() {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.isStepDefinition((PsiMethod) method)).isFalse();
    }

    //getAnnotationTexts(PsiAnnotation)

    @Test
    void shouldGetTextsWithoutAliases() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;

            class AnnotationTexts {
                @The<caret>n("the {price|cost} of the product should be $price")
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiAnnotation) annotation)).containsExactlyInAnyOrder(
            "the price of the product should be $price",
            "the cost of the product should be $price");
    }

    @Test
    void shouldGetTextsWithAlias() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;
            import org.jbehave.core.annotations.Alias;

            class AnnotationTexts {
                @The<caret>n("the {price|cost} of the product should be $price")
                @Alias("the product should {cost|be sold for} $price")
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiAnnotation) annotation)).containsExactlyInAnyOrder(
            "the price of the product should be $price",
            "the product should cost $price",
            "the cost of the product should be $price",
            "the product should be sold for $price"
        );
    }

    @Test
    void shouldGetTextsWithAliases() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;
            import org.jbehave.core.annotations.Aliases;

            class AnnotationTexts {
                @The<caret>n("the {price|cost} of the product should be $price")
                @Aliases(values = {"the product should cost $price",
                                   "the product should be sold for $price"})
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiAnnotation) annotation)).containsExactlyInAnyOrder(
            "the price of the product should be $price",
            "the product should cost $price",
            "the cost of the product should be $price",
            "the product should be sold for $price"
        );
    }

    @Test
    void shouldGetTextsWithAliasAndAliases() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;
            import org.jbehave.core.annotations.Alias;
            import org.jbehave.core.annotations.Aliases;

            class AnnotationTexts {
                @The<caret>n("the {price|cost} of the product should be $price")
                @Alias("the product should {cost|be sold for} $price")
                @Aliases(values = {"the product should worth $price",
                                   "the product should be sold for $price"})
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiAnnotation) annotation)).containsExactlyInAnyOrder(
            "the product should worth $price",
            "the price of the product should be $price",
            "the product should cost $price",
            "the cost of the product should be $price",
            "the product should be sold for $price"
        );
    }

    //getAnnotationTexts(PsiMethod)

    @Test
    void shouldGetTextsForMethodWithoutAliases() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;

            class AnnotationTexts {
                @Then("the {price|cost} of the product should be $price")
                void stepDef<caret>Method(int price) {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiMethod) method)).containsExactlyInAnyOrder(
            "the price of the product should be $price",
            "the cost of the product should be $price");
    }

    @Test
    void shouldGetTextsForMethodWithAlias() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;
            import org.jbehave.core.annotations.Alias;

            class AnnotationTexts {
                @Then("the {price|cost} of the product should be $price")
                @Alias("the product should {cost|be sold for} $price")
                void stepDe<caret>fMethod(int price) {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiMethod) method)).containsExactlyInAnyOrder(
            "the price of the product should be $price",
            "the product should cost $price",
            "the cost of the product should be $price",
            "the product should be sold for $price"
        );
    }

    @Test
    void shouldGetTextsForMethodWithAliases() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;
            import org.jbehave.core.annotations.Aliases;

            class AnnotationTexts {
                @Then("the {price|cost} of the product should be $price")
                @Aliases(values = {"the product should cost $price",
                                   "the product should be sold for $price"})
                void stepDef<caret>Method(int price) {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiMethod) method)).containsExactlyInAnyOrder(
            "the price of the product should be $price",
            "the product should cost $price",
            "the cost of the product should be $price",
            "the product should be sold for $price"
        );
    }

    @Test
    void shouldGetTextsForMethodWithAliasAndAliases() {
        var stepDefFile = getFixture().configureByText("AnnotationTexts.java", """
            import org.jbehave.core.annotations.Then;
            import org.jbehave.core.annotations.Alias;
            import org.jbehave.core.annotations.Aliases;

            class AnnotationTexts {
                @Then("the {price|cost} of the product should be $price")
                @Alias("the product should {cost|be sold for} $price")
                @Aliases(values = {"the product should worth $price",
                                   "the product should be sold for $price"})
                void stepDe<caret>fMethod(int price) {
                }
            }
            """);

        var method = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        assertThat(method).isInstanceOf(PsiMethod.class);
        assertThat(JBehaveUtil.getAnnotationTexts((PsiMethod) method)).containsExactlyInAnyOrder(
            "the product should worth $price",
            "the price of the product should be $price",
            "the product should cost $price",
            "the cost of the product should be $price",
            "the product should be sold for $price"
        );
    }

    //getAnnotationPriority

    @Test
    void shouldGetDefaultPriority() {
        var stepDefFile = getFixture().configureByText("AnnotationPriority.java", """
            import org.jbehave.core.annotations.Then;

            class AnnotationPriority {
                @The<caret>n("the {price|cost} of the product should be $price")
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationPriority((PsiAnnotation) annotation)).isEqualTo(0);
    }

    @Test
    void shouldGetCustomPriority() {
        var stepDefFile = getFixture().configureByText("AnnotationPriority.java", """
            import org.jbehave.core.annotations.Then;

            class AnnotationPriority {
                @The<caret>n(value = "the {price|cost} of the product should be $price", priority = 30)
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationPriority((PsiAnnotation) annotation)).isEqualTo(30);
    }

    @Test
    void shouldGetFallbackPriorityWhenConfiguredWithInvalidValue() {
        var stepDefFile = getFixture().configureByText("AnnotationPriority.java", """
            import org.jbehave.core.annotations.Then;

            class AnnotationPriority {
                @The<caret>n(value = "the {price|cost} of the product should be $price", priority = "invalid")
                void stepDefMethod(int price) {
                }
            }
            """);

        var annotation = stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent().getParent();
        assertThat(annotation).isInstanceOf(PsiAnnotation.class);
        assertThat(JBehaveUtil.getAnnotationPriority((PsiAnnotation) annotation)).isEqualTo(-1);
    }

    //getTheBiggestWordToSearchByIndex

    @ParameterizedTest
    @CsvSource({
        //Empty string for empty string
        "'',''",
        //Empty string for blank string
        "'   ',''",
        //Without placeholder
        "the {price|cost} of the product should be 100, product",
        //With placeholder
        "the {price|cost} of the product should be $price, product",
        //First of multiple of the same-length words
        "the {price|cost} of the product shouldd be $price, product",
        //Empty string for placeholder-only,
        "$price $cost, ''"
    })
    void shouldReturnBiggestWord(String stepText, String biggestWord) {
        assertThat(JBehaveUtil.getTheBiggestWordToSearchByIndex(stepText)).isEqualTo(biggestWord);
    }
}
