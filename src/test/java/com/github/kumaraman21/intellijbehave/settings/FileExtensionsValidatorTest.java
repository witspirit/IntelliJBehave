package com.github.kumaraman21.intellijbehave.settings;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class FileExtensionsValidatorTest {

    FileExtensionsValidator underTest;

    @Before
    public void setup() {
        underTest = new FileExtensionsValidator();
    }

    @Test
    public void whenIsValid_givenEmptyString_thenValid() {
        assertThat(underTest.isValid("")).isTrue();
    }

    @Test
    public void whenIsValid_givenNull_thenValid() {
        assertThat(underTest.isValid(null)).isTrue();
    }

    @Test
    public void whenIsValid_givenDotWithExtension_thenValid() {
        assertThat(underTest.isValid(".feature")).isTrue();
    }

    @Test
    public void whenIsValid_givenExtensionWithoutDot_thenInvalid() {
        assertThat(underTest.isValid("foo")).isFalse();
    }

    @Test
    public void whenIsValid_givenMultipleValidExtensionsSeparatedWithComas_thenValid() {
        assertThat(underTest.isValid(".foo,.bar")).isTrue();
    }

    @Test
    public void whenIsValid_givenMultipleValidExtensionsSeparatedWithComasAndSpaces_thenValid() {
        assertThat(underTest.isValid(" .feature,.foo , .bar ,    .foobar     ")).isTrue();
    }

    @Test
    public void whenIsValid_givenNumbersInExtension_thenValid() {
        assertThat(underTest.isValid(".0123456789")).isTrue();
    }

    @Test
    public void whenIsValid_givenAllUpperAndLowerCases_thenValid() {
        assertThat(underTest.isValid(".abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
    }

    @Test
    public void whenIsValid_givenSpecialCharacters_thenInvalid() {
        "!@#$%?&*()-=+Ééè;^çàÀÇ¨^`~".codePoints()
                .mapToObj(c -> String.valueOf((char)c))
                .forEach(c -> assertThat(underTest.isValid("."+c)).isTrue());
    }
}
