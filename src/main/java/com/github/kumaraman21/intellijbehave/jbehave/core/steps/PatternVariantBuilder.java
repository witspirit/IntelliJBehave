package com.github.kumaraman21.intellijbehave.jbehave.core.steps;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Builds a set of pattern variants of given pattern input, supporting a custom
 * directives. Depending on the directives present, one or more resulting
 * variants are created.
 * </p>
 * <p>
 * Currently supported directives are
 * </p>
 * <table border="1">
 * <thead>
 * <tr>
 * <td>Pattern</td>
 * <td>Result</td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>..A {x|y} B..</td>
 * <td>
 * <ul>
 * <li>..A x B..</li>
 * <li>..A y B..</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td>..A {x|y|} B..</td>
 * <td>
 * <ul>
 * <li>..A x B..</li>
 * <li>..A y B..</li>
 * <li>..A B..</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td>..A {x} B..</td>
 * <td>
 * <ul>
 * <li>..A x B..</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <p>
 * These directives can be used to conveniently create several variants of a
 * step pattern, without having to repeat it as a whole as one or more aliases.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <ul>
 *     <li>
 *         <p>
 *             <code>@Then("the result {must |has to |}be $x")<br> public void checkResult(int x)...</code>
 *         </p>
 *         <p>
 *             Would match any of these variants from a story file:
 *             <ul>
 *                 <li>Then the result must be 42</li>
 *                 <li>Then the result has to be 42</li>
 *                 <li>Then the result be 42</li>
 *             </ul>
 *         </p>
 *     </li>
 *     <li>
 *         <p>
 *             <code>@When("$A {+|plus|is added to} $B")<br> public void add(int A, int B)...</code>
 *         </p>
 *         <p>
 *             Would match any of these variants from a story file:
 *             <ul>
 *                 <li>When 42 + 23</li>
 *                 <li>When 42 plus 23</li>
 *                 <li>When 42 is added to 23</li>
 *             </ul>
 *         </p>
 *     </li>
 * </ul>
 * <p>
 * This is the modified and optimized version of {@link org.jbehave.core.steps.PatternVariantBuilder}.
 *
 * @author Daniel Schneller
 */
public class PatternVariantBuilder {

    /**
     * Regular expression that locates patterns to be evaluated in the input
     * pattern.
     */
    private static final Pattern REGEX = Pattern.compile("([^\\n{]*+)(\\{(([^|}]++)(\\|)?+)*+\\})([^\\n]*+)");

    private final Set<String> variants;

    private final String input;

    /**
     * Creates a builder and calculates all variants for given input. When there
     * are no variants found in the input, it will itself be the only result.
     *
     * @param input to be evaluated
     */
    public PatternVariantBuilder(String input) {
        this.input = input;
        this.variants = variantsFor(input);
    }

    public String getInput() {
        return input;
    }

    /**
     * <p>
     * Parses the {@link #input} received at construction and generates the
     * variants. When there are multiple patterns in the input, the method will
     * recurse on itself to generate the variants for the tailing end after the
     * first matched pattern.
     * </p>
     * <p>
     * Generated variants are stored in a {@link Set}, so there will never be
     * any duplicates, even if the input's patterns were to result in such.
     * </p>
     */
    private Set<String> variantsFor(String input) {
        Matcher m = REGEX.matcher(input);

        if (!m.matches()) {
            // if the regex does not find any patterns,
            // simply add the input as is
            return Collections.singleton(input);
        }

        // isolate the pattern itself, removing its wrapping {}
        String patternGroup = m.group(2).replaceAll("[\\{\\}]", "");

        // split the pattern into its options and add an empty
        // string if it ends with a separator
        List<String> patternParts = new ArrayList<>(asList(patternGroup.split("\\|")));
        if (patternGroup.endsWith("|")) {
            patternParts.add("");
        }

        // Store current invocation's results
        Set<String> variants = new HashSet<>(8);

        if (!patternParts.isEmpty()) {
            // isolate the part before the first pattern
            String head = m.group(1);

            // isolate the remaining part of the input
            String tail = m.group(6);

            var variantsForTail = variantsFor(tail);

            // Iterate over the current pattern's
            // variants and construct the result.
            for (String part : patternParts) {
                var partString = head != null ? head + part : part;

                // recurse on the tail of the input
                // to handle the next pattern

                // append all variants of the tail end
                // and add each of them to the part we have
                // built up so far.
                for (String tailVariant : variantsForTail) {
                    variants.add(partString + tailVariant);
                }
            }
        }
        return variants;
    }

    /**
     * Returns a new copy set of all variants with no whitespace compression.
     *
     * @return a {@link Set} of all variants without whitespace compression
     */
    public Set<String> allVariants() {
        return variants.size() == 1 ? Collections.singleton(variants.iterator().next()) : new HashSet<>(variants);
    }
}
