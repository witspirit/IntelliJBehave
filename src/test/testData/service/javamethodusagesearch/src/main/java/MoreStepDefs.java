import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

public class MoreStepDefs extends Steps {

    @Given("Open url '$url'")
    public void openAUrl(@Named("url") String url) {
    }

    @When("")
    public void searchFo<caret>rText(@Named("string") String string) {
    }

    @Given(value = "result list size is $size", priority = 2)
    public void setResultListSize(@Named("size") int size) {
    }
}
