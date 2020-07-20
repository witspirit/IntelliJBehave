package com.github.kumaraman21.intellijbehave.utility;

import org.jbehave.core.i18n.LocalizedKeywords;
import org.junit.Before;
import org.junit.Test;

import static com.github.kumaraman21.intellijbehave.utility.JBKeyword.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class CharTreeTest {

    private CharTree<JBKeyword> charTree;

    @Before
    public void setUp () {
        LocalizedKeywords keywords = new LocalizedKeywords();
        charTree = new CharTree<JBKeyword>('/', null);
        for (JBKeyword kw : JBKeyword.values()) {
            String asString = kw.asString(keywords);
            charTree.push(asString, kw);
        }
    }

    @Test
    public void narrative_doubleDot() {
        assertThat(charTree.lookup("Narrative: \n", 0)).isEqualTo(new CharTree.Entry<JBKeyword>(Narrative, 10));
    }
}
