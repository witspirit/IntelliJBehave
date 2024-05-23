import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

public class StepDefs extends Steps {

    @Given("Open url '$url'")
    public void openAUrl(@Named("url") String url) {
    }

    @When("search for $string")
    public void <warning descr="Step 'searchForText' is never used">searchForText</warning>(@Named("string") String string) {
    }

    @Given(value = "result list size is $size", priority = 2)
    public void <warning descr="Step 'setResultListSize' is never used">setResultListSize</warning>(@Named("size") int size) {
    }
}
