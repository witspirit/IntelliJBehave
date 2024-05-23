package com.github.kumaraman21.intellijbehave.utility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class LocalizedStorySupportTest {

    @Test
    public void checkForLanguageDefinition_validCases() {
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- language:fr")).isEqualTo("fr");
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- language: fr")).isEqualTo("fr");
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- language: fr ")).isEqualTo("fr");
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- language:  fr ")).isEqualTo("fr");
    }

    @Test
    public void checkForLanguageDefinition_invalidCases() {
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- languge:fr")).isNull();
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- Language: fr")).isNull();
        assertThat(LocalizedStorySupport.checkForLanguageDefinition(" !-- lang: fr ")).isNull();
    }
}
