@file:Suppress("NOTHING_TO_INLINE")

package com.github.kumaraman21.intellijbehave.service

import org.jbehave.core.parsers.StepMatcher

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
private object StepPatterns {
    object Plain {
        const val VARIABLE = "(.*)"
    }

    object Regex {
        val SPACE = Regex("\\s+")
    }
}

internal class OptimizedStepMatcher(stepMatcher: StepMatcher) : StepMatcher by stepMatcher {

    private val matchesRegexes: List<Regex>

    init {
        val patterns = stepMatcher.pattern().resolved().split(StepPatterns.Plain.VARIABLE)
        fun List<String>.toRegexes() = asSequence()
            //Replaces sequences of spaces with single \s symbols. This is in line with the trimming
            // of space in the 'matches()' function below.
            .map { it.replace(StepPatterns.Regex.SPACE, "\\s") }
            .map(::Regex)
            .toList()

        matchesRegexes = patterns.toRegexes().mapIndexed { i, regex ->
            when (i) {
                0 -> Regex("^${regex.pattern}")
                patterns.lastIndex -> Regex("${regex.pattern}$")
                else -> regex
            }
        }
    }

    //TODO implement catching optimization strategy

    fun matches(text: String): Boolean = text.trimSpaces().find()

    private fun String.find(textIndex: Int = 0, regexIndex: Int = 0): Boolean {
        return if (regexIndex == matchesRegexes.size) true
        else if (textIndex < length) doFind(textIndex, regexIndex)
        // This helps to solve the case when the parameter is right at the end of the step pattern,
        // e.g. '@When("a user$thing")' and the parameter is passed an empty value.
        // In this case the remaining regex pattern would be a single $ sign which has to be matched against an empty string.
        else textIndex == length
    }

    private fun String.doFind(textIndex: Int, regexIndex: Int): Boolean {
        val matchResult = matchesRegexes[regexIndex].find(this, textIndex)
        return matchResult != null && find(matchResult.range.last + 1, regexIndex + 1)
    }
}

//region Utils
private inline fun String.trimSpaces() = replace(StepPatterns.Regex.SPACE, " ")
//endregion
