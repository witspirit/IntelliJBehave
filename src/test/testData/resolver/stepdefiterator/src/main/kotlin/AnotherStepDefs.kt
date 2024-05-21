import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.Named

class AnotherStepDefs {
    @Then("check result size is \$size")
    fun checkSize(@Named("size") size: Int) {
    }

    @Then("result ends with \$text")
    fun checkEnding(@Named("text") text: Int) {
    }
}