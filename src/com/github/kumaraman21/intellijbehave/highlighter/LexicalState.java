package com.github.kumaraman21.intellijbehave.highlighter;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public enum LexicalState {
	YYINITIAL(_StoryLexer.YYINITIAL),
	IN_DIRECTIVE(_StoryLexer.IN_DIRECTIVE),
	IN_STORY(_StoryLexer.IN_STORY),
	IN_SCENARIO(_StoryLexer.IN_SCENARIO),
	IN_GIVEN(_StoryLexer.IN_GIVEN),
	IN_WHEN(_StoryLexer.IN_WHEN),
	IN_THEN(_StoryLexer.IN_THEN),
	IN_META(_StoryLexer.IN_META),
	IN_TABLE(_StoryLexer.IN_TABLE),
	IN_EXAMPLES(_StoryLexer.IN_EXAMPLES);

	private final int lexerId;

	LexicalState(int lexerId) {
		this.lexerId = lexerId;
	}

	public static LexicalState fromLexer(int lexerId) {
		for (LexicalState state : values()) {
			if (state.lexerId == lexerId) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unsupported lexer id: " + lexerId);
	}
}
