import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

public class OtherStepDefs extends Steps {

    @T<caret>hen("result list size is $size")
    public void checkResultListSize(@Named("size") int size) {
    }
}
