import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.steps.Steps;

public class StepDefs extends Steps {

    public void methodWithoutStepAnnotation() {
    }

    //Given

    @Given()
    public void givenStepDefWithoutAnnotationAttribute() {
    }

    @Given("one given $string")
    public void givenStepDefWithOneAnnotationAttribute() {
    }

    @Given("multiple given $string", "given multiple something else")
    public void givenStepDefWithMultipleAnnotationAttributes() {
    }

    //When

    @When()
    public void whenStepDefWithoutAnnotationAttribute() {
    }

    @When("one when $string")
    public void whenStepDefWithOneAnnotationAttribute() {
    }

    @When("multiple when $string", "when multiple something else")
    public void whenStepDefWithMultipleAnnotationAttributes() {
    }

    //Then

    @Then()
    public void thenStepDefWithoutAnnotationAttribute() {
    }

    @Then("one then $string")
    public void thenStepDefWithOneAnnotationAttribute() {
    }

    @Then("multiple then $string", "then multiple something else")
    public void thenStepDefWithMultipleAnnotationAttributes() {
    }

    //Alias

    @Alias()
    public void aliasStepDefWithoutAnnotationAttribute() {
    }

    @Alias("one alias $string")
    public void aliasStepDefWithOneAnnotationAttribute() {
    }

    @Alias("multiple alias $string", "alias something else")
    public void aliasStepDefWithMultipleAnnotationAttributes() {
    }

    //Aliases

    @Aliases()
    public void aliasesStepDefWithoutAnnotationAttribute() {
    }

    @Aliases(values = {"one aliases $string"})
    public void aliasesStepDefWithOneAnnotationAttribute() {
    }

    @Aliases(values = {"multiple aliases $string", "aliases something else"})
    public void aliasesStepDefWithMultipleAnnotationAttributes() {
    }

    //Mixed

    @When("mixed when aliases $string")
    @Aliases(values = {"mixed when aliases one $string", "mixed when aliases two"})
    public void whenAndAliasesStepDef() {
    }

    @Aliases(values = {"mixed aliases when one $string", "mixed aliases when two"})
    @When("mixed aliases when $string")
    public void aliasesAndWhenStepDef() {
    }
}
