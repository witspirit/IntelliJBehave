package com.github.kumaraman21.intellijbehave.jbehave.core.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Unit test for {@link PatternVariantBuilder}.
 * <p>
 * Modified version of {@code org.jbehave.core.steps.PatternVariantBuilderBehaviour}.
 */
class PatternVariantBuilderTest {

    @Test
    void shouldReturnItselfForNoPatternString() {
        PatternVariantBuilder builder = new PatternVariantBuilder("No variants");
        assertThat(builder.getInput()).isEqualTo("No variants");
        Set<String> variants = builder.allVariants();
        assertThat(variants.iterator().next()).isEqualTo("No variants");
        assertThat(variants).hasSize(1);
    }

    @Test
    void shouldReturnTwoVariantsForOnePattern() {
        PatternVariantBuilder builder = new PatternVariantBuilder("There are {Two|One} variants");
        assertThat(builder.getInput()).isEqualTo("There are {Two|One} variants");
        Set<String> result = builder.allVariants();
        assertThat(result).hasSize(2).contains("There are One variants", "There are Two variants");
    }

    @Test
    void shouldReturnFourVariantsForTwoPatterns() {
        PatternVariantBuilder builder = new PatternVariantBuilder("There are {Two|One} variants, {hooray|alas}!");
        Set<String> result = builder.allVariants();
        assertThat(result).hasSize(4)
            .contains("There are One variants, hooray!", "There are Two variants, hooray!", "There are One variants, alas!", "There are Two variants, alas!");
    }

    @Test
    void shouldReturnFourVariantsForTwoPatternsWithOptionElements() {
        PatternVariantBuilder builder = new PatternVariantBuilder("There are {One|} variants{, hooray|}!");
        Set<String> result = builder.allVariants();
        assertThat(result).hasSize(4)
            .contains("There are One variants, hooray!", "There are  variants, hooray!", "There are One variants!", "There are  variants!");
    }

    @Test
    void shouldHandleSpecialCharacters() {
        PatternVariantBuilder builder = new PatternVariantBuilder("When $A {+|plus|is added to} $B");
        Set<String> result = builder.allVariants();
        assertThat(result).hasSize(3).contains("When $A + $B", "When $A plus $B", "When $A is added to $B");
    }

    @Test
    void hasUnclosedBracket() {
        PatternVariantBuilder builder = new PatternVariantBuilder("When $A {+|plus|is added to $B");
        Set<String> result = builder.allVariants();
        assertThat(result).hasSize(1).contains("When $A {+|plus|is added to $B");
    }

    @Test
    void hasUnclosedBrackets() {
        PatternVariantBuilder builder = new PatternVariantBuilder("When $A {+|plus|is added to} $B and }{$C");
        Set<String> result = builder.allVariants();
        assertThat(result).hasSize(3).contains("When $A + $B and }{$C", "When $A plus $B and }{$C", "When $A is added to $B and }{$C");
    }
}
