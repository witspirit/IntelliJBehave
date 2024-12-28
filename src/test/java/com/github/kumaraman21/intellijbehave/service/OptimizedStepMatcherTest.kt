package com.github.kumaraman21.intellijbehave.service

import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser
import org.jbehave.core.parsers.StepPatternParser
import org.jbehave.core.steps.StepType.GIVEN
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

/**
 * Unit test for [OptimizedStepMatcher].
 */
class OptimizedStepMatcherTest {

    private val stepPatternParser: StepPatternParser = RegexPrefixCapturingPatternParser()

    @TestFactory
    fun validatesStepMatching(): Stream<DynamicTest> {
        return Stream.of(
            //This is an edge case, whether it returns false or true doesn't seem to make a difference
            dynamicTest("empty texts", {
                assertThat(matches("", "")).isTrue()
            }),

            //No parameter - match

            dynamicTest("one word - no parameter") {
                assertThat(matches("opened", "opened")).isTrue()
            },
            dynamicTest("two words - no parameter") {
                assertThat(matches("I opened", "I opened")).isTrue()
            },
            dynamicTest("two-plus words - no parameter") {
                assertThat(matches("I opened the HU homepage", "I opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple words - multiple spaces in step text - no parameter") {
                assertThat(matches("I opened the HU homepage", "I   opened   the HU homepage")).isTrue()
            },
            dynamicTest("multiple words - multiple spaces in annotation text - no parameter") {
                assertThat(matches("I   opened   the HU homepage", "I opened the HU homepage")).isTrue()
            },

            //No parameter - mismatch

            dynamicTest("one word - no parameter - mismatch") {
                assertThat(matches("opened", "closed")).isFalse()
            },
            dynamicTest("two words - no parameter - mismatch") {
                assertThat(matches("I opened", "I closed")).isFalse()
            },
            dynamicTest("two-plus words - no parameter - mismatch") {
                assertThat(matches("I opened the HU homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - multiple spaces in step text - no parameter - mismatch") {
                assertThat(matches("I opened the HU homepage", "I   closed   the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - multiple spaces in annotation text - no parameter - mismatch") {
                assertThat(matches("I   opened   the HU homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - spaces before annotation text - no parameter - mismatch trim") {
                assertThat(matches("   I opened the HU homepage", "I opened the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - spaces after annotation text - no parameter - mismatch trim") {
                assertThat(matches("I opened the HU homepage   ", "I opened the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - spaces before annotation text - no parameter - mismatch") {
                assertThat(matches("   I opened the HU homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - spaces after annotation text - no parameter - mismatch") {
                assertThat(matches("I opened the HU homepage   ", "I closed the HU homepage")).isFalse()
            },

            //Parameters - match

            dynamicTest("one parameter only - blank") {
                assertThat(matches("\$thing", "   ")).isTrue()
            },
            dynamicTest("one parameter only") {
                assertThat(matches("\$thing", "opened")).isTrue()
            },
            dynamicTest("one parameter only - empty") {
                assertThat(matches("\$thing", "")).isTrue()
            },
            dynamicTest("two parameters only") {
                assertThat(matches("\$some \$thing", "I opened")).isTrue()
            },
            dynamicTest("two parameters only - empty") {
                assertThat(matches("\$some \$thing", " ")).isTrue()
            },
            dynamicTest("two parameters without whitespace - empty") {
                assertThat(matches("\$some\$thing", "")).isTrue()
            },

            dynamicTest("one word - parameter") {
                assertThat(matches("opened \$thing", "opened homepage")).isTrue()
            },
            dynamicTest("two words - parameter") {
                assertThat(matches("I \$indeed opened", "I really opened")).isTrue()
            },
            dynamicTest("two-plus words - parameter") {
                assertThat(matches("I opened the \$locale homepage", "I opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple words - multiple spaces in step text - parameter") {
                assertThat(matches("I opened the \$locale homepage", "I   opened   the HU homepage")).isTrue()
            },
            dynamicTest("multiple words - multiple spaces in annotation text - parameter") {
                assertThat(matches("I   opened   the \$locale homepage", "I opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple words - spaces before annotation text - parameter") {
                assertThat(matches("   I opened the \$locale homepage", "   I opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple words - spaces after annotation text - parameter") {
                assertThat(matches("I opened the \$locale homepage   ", "I opened the HU homepage   ")).isTrue()
            },

            dynamicTest("parameter - empty - beginning of annotation text") {
                assertThat(matches("\$person opened the HU homepage", " opened the HU homepage")).isTrue()
            },
            dynamicTest("parameter - empty and trimmed - beginning of annotation text") {
                assertThat(matches("\$person opened the HU homepage", " opened the HU homepage")).isTrue()
            },
            dynamicTest("parameter - non-empty - beginning of annotation text") {
                assertThat(matches("\$person opened the HU homepage", "I opened the HU homepage")).isTrue()
            },
            //FIXME: this should match because it matches in JBehave
            dynamicTest("parameter - empty - middle of annotation text") {
                assertThat(matches("I opened the \$locale homepage", "I opened the  homepage")).isFalse()
            },
            dynamicTest("parameter - non-empty - middle of annotation text") {
                assertThat(matches("I opened the \$locale homepage", "I opened the HU homepage")).isTrue()
            },
            dynamicTest("parameter - empty - end of annotation text") {
                assertThat(matches("I opened the HU \$page", "I opened the HU ")).isTrue()
            },
            dynamicTest("parameter - empty and trimmed - end of annotation text") {
                assertThat(matches("I opened the HU \$page", "I opened the HU ")).isTrue()
            },
            dynamicTest("parameter - non-empty - end of annotation text") {
                assertThat(matches("I opened the HU \$page", "I opened the HU homepage")).isTrue()
            },

            dynamicTest("multiple parameters - empty - beginning of annotation text") {
                assertThat(matches("\$person opened the \$locale homepage", " opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple parameters - empty and trimmed - beginning of annotation text") {
                assertThat(matches("\$person opened the \$locale homepage", " opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple parameters - non-empty - beginning of annotation text") {
                assertThat(matches("\$person opened the \$locale homepage", "I opened the HU homepage")).isTrue()
            },
            //FIXME: this should match because it matches in JBehave
            dynamicTest("multiple parameters - empty - middle of annotation text") {
                assertThat(matches("\$person opened the \$locale homepage", "I opened the  homepage")).isFalse()
            },
            //FIXME: this should match because it matches in JBehave
            dynamicTest("multiple parameters - empty and trimmed - middle of annotation text") {
                assertThat(matches("\$person opened the \$locale homepage", " opened the homepage")).isFalse()
            },
            dynamicTest("multiple parameters - non-empty - middle of annotation text") {
                assertThat(matches("\$person opened the \$locale homepage", "I opened the HU homepage")).isTrue()
            },
            dynamicTest("multiple parameters - empty - end of annotation text") {
                assertThat(matches("\$person opened the HU \$page", "I opened the HU ")).isTrue()
            },
            dynamicTest("multiple parameters - empty and trimmed - end of annotation text") {
                assertThat(matches("\$person opened the HU \$page", "I opened the HU ")).isTrue()
            },
            dynamicTest("multiple parameters - non-empty - end of annotation text") {
                assertThat(matches("\$person opened the HU \$page", "I opened the HU homepage")).isTrue()
            },

            //Parameters - mismatch

            dynamicTest("two parameters - no whitespace at all- mismatch") {
                assertThat(matches("\$some\$thing", "opened")).isFalse()
            },
            dynamicTest("two parameters - no whitespace in annotation text - mismatch") {
                assertThat(matches("\$some\$thing", "I opened the homepage")).isFalse()
            },

            dynamicTest("one word - parameter - mismatch") {
                assertThat(matches("opened \$thing", "closed homepage")).isFalse()
            },
            dynamicTest("two words - parameter - mismatch") {
                assertThat(matches("I \$indeed opened", "I really closed")).isFalse()
            },
            dynamicTest("two-plus words - parameter - mismatch") {
                assertThat(matches("I opened the \$locale homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - multiple spaces in step text - parameter - mismatch") {
                assertThat(matches("I opened the \$locale homepage", "I   closed   the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - multiple spaces in annotation text - parameter - mismatch") {
                assertThat(matches("I   opened   the \$locale homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - spaces before annotation text - parameter - mismatch") {
                assertThat(matches("   I opened the \$locale homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple words - spaces after annotation text - parameter - mismatch") {
                assertThat(matches("I opened the \$locale homepage   ", "I closed the HU homepage")).isFalse()
            },

            dynamicTest("parameter - empty - beginning of annotation text - mismatch") {
                assertThat(matches("\$person opened the HU homepage", " closed the HU homepage")).isFalse()
            },
            dynamicTest("parameter - empty and trimmed - beginning of annotation text - mismatch") {
                assertThat(matches("\$person opened the HU homepage", "closed the HU homepage")).isFalse()
            },
            dynamicTest("parameter - non-empty - beginning of annotation text - mismatch") {
                assertThat(matches("\$person opened the HU homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("parameter - empty - middle of annotation text - mismatch") {
                assertThat(matches("I opened the \$locale homepage", "I closed the  homepage")).isFalse()
            },
            dynamicTest("parameter - empty and trimmed - middle of annotation text - mismatch") {
                assertThat(matches("I opened the \$locale homepage", "I opened the homepage")).isFalse()
            },
            dynamicTest("parameter - non-empty - middle of annotation text - mismatch") {
                assertThat(matches("I opened the \$locale homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("parameter - empty - end of annotation text - mismatch") {
                assertThat(matches("I opened the HU \$page", "I closed the HU ")).isFalse()
            },
            dynamicTest("parameter - empty and trimmed - end of annotation text - mismatch") {
                assertThat(matches("I opened the HU \$page", "I closed the HU")).isFalse()
            },
            dynamicTest("parameter - non-empty - end of annotation text - mismatch") {
                assertThat(matches("I opened the HU \$page", "I closed the HU homepage")).isFalse()
            },

            dynamicTest("multiple parameters - empty - beginning of annotation text - mismatch") {
                assertThat(matches("\$person opened the \$locale homepage", " closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple parameters - empty and trimmed - beginning of annotation text - mismatch") {
                assertThat(matches("\$person opened the \$locale homepage", "opened the HU homepage")).isFalse()
            },
            dynamicTest("multiple parameters - non-empty - beginning of annotation text - mismatch") {
                assertThat(matches("\$person opened the \$locale homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple parameters - empty - middle of annotation text - mismatch") {
                assertThat(matches("\$person opened the \$locale homepage", "I closed the  homepage")).isFalse()
            },
            dynamicTest("multiple parameters - empty and trimmed - middle of annotation text - mismatch") {
                assertThat(matches("\$person opened the \$locale homepage", "opened the homepage")).isFalse()
            },
            dynamicTest("multiple parameters - non-empty - middle of annotation text - mismatch") {
                assertThat(matches("\$person opened the \$locale homepage", "I closed the HU homepage")).isFalse()
            },
            dynamicTest("multiple parameters - empty - end of annotation text - mismatch") {
                assertThat(matches("\$person opened the HU \$page", "I closed the HU ")).isFalse()
            },
            dynamicTest("multiple parameters - empty and trimmed - end of annotation text - mismatch") {
                assertThat(matches("\$person opened the HU \$page", "I opened the HU")).isFalse()
            },
            dynamicTest("multiple parameters - non-empty - end of annotation text - mismatch") {
                assertThat(matches("\$person opened the HU \$page", "I closed the HU homepage")).isFalse()
            }
        )
    }

    private fun matches(annotationText: String, stepText: String): Boolean {
        return OptimizedStepMatcher(stepPatternParser.parseStep(GIVEN, annotationText)).matches(stepText)
    }
}
