package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.Lexer;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryLexerFactory {
	public static final boolean USE_LOCALIZED = true;

	public Lexer createLexer() {
		if (USE_LOCALIZED) {
			return new StoryLocalizedLexer();
		} else {
			return new StoryLexer();
		}
	}
}
