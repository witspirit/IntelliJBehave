package com.github.kumaraman21.intellijbehave.utility;

import org.jbehave.core.configuration.Keywords;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */

public enum JBKeyword {
    Meta {
        @Override
        public String asString(Keywords keywords) {
            return keywords.meta();
        }
    },
    MetaProperty {
        @Override
        public String asString(Keywords keywords) {
            return keywords.metaProperty();
        }
    },
    Narrative {
        @Override
        public String asString(Keywords keywords) {
            return keywords.narrative();
        }
    },
    InOrderTo {
        @Override
        public String asString(Keywords keywords) {
            return keywords.inOrderTo();
        }
    },
    AsA {
        @Override
        public String asString(Keywords keywords) {
            return keywords.asA();
        }
    },
    IWantTo {
        @Override
        public String asString(Keywords keywords) {
            return keywords.iWantTo();
        }
    },
    Scenario {
        @Override
        public String asString(Keywords keywords) {
            return keywords.scenario();
        }
    },
    GivenStories {
        @Override
        public String asString(Keywords keywords) {
            return keywords.givenStories();
        }
    },
    ExamplesTable {
        @Override
        public String asString(Keywords keywords) {
            return keywords.examplesTable();
        }
    },
    ExamplesTableRow {
        @Override
        public String asString(Keywords keywords) {
            return keywords.examplesTableRow();
        }
    },
    ExamplesTableHeaderSeparator {
        @Override
        public String asString(Keywords keywords) {
            return keywords.examplesTableHeaderSeparator();
        }
    },
    ExamplesTableValueSeparator {
        @Override
        public String asString(Keywords keywords) {
            return keywords.examplesTableValueSeparator();
        }
    },
    ExamplesTableIgnorableSeparator {
        @Override
        public String asString(Keywords keywords) {
            return keywords.examplesTableIgnorableSeparator();
        }
    },
    Given {
        @Override
        public String asString(Keywords keywords) {
            return keywords.given();
        }
    },
    When {
        @Override
        public String asString(Keywords keywords) {
            return keywords.when();
        }
    },
    Then {
        @Override
        public String asString(Keywords keywords) {
            return keywords.then();
        }
    },
    And {
        @Override
        public String asString(Keywords keywords) {
            return keywords.and();
        }
    },
    Ignorable {
        @Override
        public String asString(Keywords keywords) {
            return keywords.ignorable();
        }
    };

    public String asString(Keywords keywords) {
        throw new AbstractMethodError();
    }

    public boolean isComment() {
        return this == Ignorable;
    }
}
