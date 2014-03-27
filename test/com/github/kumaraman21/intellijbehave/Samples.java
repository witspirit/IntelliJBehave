package com.github.kumaraman21.intellijbehave;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Samples {

	public static final String SIMPLE_SAMPLE =
			"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Meta:\n" + //
					"@skip\n" + //
					"\n" + //
					"Given i am the user with nickname: \"weird\"\n" + //
					"When i try to login using the password \"soweird\"\n" + //
					"Then i get an error message of type \"Wrong Credentials\"\n";

	public static final String MULTILINE_SAMPLE =
			"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Meta:\n" + //
					"@skip\n" + //
					"\n" + //
					"Given i am the user with\n nickname: \"weird\"\n" + //
					"When i try to login using \nthe password\n \"soweird\"\n" + //
					"Then i get an error\n message\n of type \"Wrong Credentials\"\n";

	public static final String SIMPLE_FR =
			"!-- language:fr\n" +
					"Scénario: une simple sortie\n" +
					"Etant donné que nous allons promener notre chienne\n" +
					"!-- un commentaire qui n'a rien à voir\n" +
					"Quand on sera dehors\n" +
					"Alors elle pourra se soulager!\n" +
					"Et elle sera super contente\n";

	public static final String LONG_SAMPLE =
			"Narrative: \n" + //
					"In order to play a game\n" + //
					"As a player\n" + //
					"I want to be able to create and manage my account\n" + //
					"\n" + //
					"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Meta:\n" + //
					"@skip\n" + //
					"\n" + //
					"Given i am the user with nickname: \"weird\"\n" + //
					"When i try to login using the password \"soweird\"\n" + //
					"Then i get an error message of type \"Wrong Credentials\"\n" + //
					"\n" + //
					"\n" + //
					"Scenario: A known user cannot be logged using a wrong password\n" + //
					"\n" + //
					"Given the following existing users:\n" + //
					"| nickname | password |\n" + //
					"|   Travis |   PacMan |\n" + //
					"Given i am the user with nickname: \"Travis\"\n" + //
					"When i try to login using the password \"McCallum\"\n" + //
					"Then i get an error message of type \"Wrong Credentials\"\n" + //
					"\n" + //
					"\n" + //
					"Scenario: A known user can be logged using the right password\n" + //
					"\n" + //
					"Given the following existing users:\n" + //
					"| nickname | password |\n" + //
					"|   Travis |   PacMan |\n" + //
					"Given i am the user with nickname: \"Travis\"\n" + //
					"When i try to login using the password \"PacMan\"\n" + //
					"Then i get logged\n" + //
					"And a welcome message is displayed\n" + //
					"\n";

	public static final String MULTILINE_LONG_SAMPLE =
			"Narrative: \n" + //
					"In order to play a game\n" + //
					"As a player\n" + //
					"I want to be able to create and manage my account\n" + //
					"\n" + //
					"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Meta:\n" + //
					"@skip\n" + //
					"\n" + //
					"Given i am the user\n with nickname: \"weird\"\n" + //
					"When i try to login using the password \"soweird\"\n" + //
					"Then i get an error\n message of type \"Wrong Credentials\"\n" + //
					"\n" + //
					"\n" + //
					"Scenario: A known user cannot be logged using a wrong password\n" + //
					"\n" + //
					"Given the following\n existing users:\n" + //
					"| nickname | password |\n" + //
					"|   Travis |   PacMan |\n" + //
					"Given i am the user\n with nickname: \"Travis\"\n" + //
					"And he is the user\n with nickname: \"Bomo\"\n" + //
					"When i try to login\n using the password \"McCallum\"\n" + //
					"And he tries to login\n using the password \"Bimo\"\n" + //
					"Then i get an error\n message of type \"Wrong Credentials\"\n" + //
					"\n" + //
					"\n" + //
					"Scenario: A known user can be logged using the right password\n" + //
					"\n" + //
					"Given the following\n existing users:\n" + //
					"| nickname | password |\n" + //
					"|   Travis |   PacMan |\n" + //
					"Given i am the user with\n nickname: \"Travis\"\n" + //
					"When i try to login using the\n password \"PacMan\"\n" + //
					"Then i get logged\n" + //
					"And a\n welcome message is displayed\n" + //
					"When i try again to login using the\n password \"PacMan\"\n" + //
					"Then i \nget logged\n" + //
					"\n";

	public static final String META_SAMPLE =
			"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Meta:\n" + //
					"@author carmen\n" + //
					"@skip\n" + //
					"\n" + //
					"Given i am the user with nickname: \"weird\"\n";

	public static final String EXAMPLES_SAMPLE =
			"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Given i am the user with nickname: \"<input>\"\n" + //
					"When i try to login using the password \"soweird\"\n" + //
					"Then i get an error message of type \"Wrong Credentials\"\n" + //
					"\n" +//
					"Examples: \n" + //
					"|  login   |   password   |\n" + //
					"|  Travis  |   Pacman     |\n" + //
					"|  Vlad    |   Thundercat |\n" + //
					"\n" +//
					"Scenario: A known user can be logged using the right password\n" + //
					"\n" + //
					"Given the following existing users:\n" + //
					"| nickname | password |\n" + //
					"|   Travis |   PacMan |\n" + //
					"Given i am the user with nickname: \"Travis\"\n" //
			;

	public static final String COMPLEX_SAMPLE =
			"Narrative: \n" + //
					"In order to play a game\n" + //
					"As a player\n" + //
					"I want to be able to create and manage my account\n" + //
					"\n" + //
					"Scenario: An unknown user cannot be logged\n" + //
					"\n" + //
					"Meta:\n" + //
					"@author turtle\n" + //
					"!-- This scenario should be skipped until fixed\n" + //
					"@skip\n" + //
					"\n" + //
					"Given i am the user with nickname: \"<input>\"\n" + //
					"!-- some weird and hard coded password\n" + //
					"When i try to login using the password \"soweird\"\n" + //
					"Then i get an error message of type \"Wrong Credentials\"\n" + //
					"\n" +//
					"Examples: \n" + //
					"|  login   |   password   |\n" + //
					"!-------------------------|\n" + //
					"|  Travis  |   Pacman     |\n" + //
					"|  Vlad    |   Thundercat |\n" + //
					"\n" + //
					"\n" + //
					"Scenario: A known user cannot be logged using a wrong password\n" + //
					"\n" + //
					"Given the following existing users:\n" + //
					"| nickname | password |\n" + //
					"|   Travis |   PacMan |\n" + //
					"Given i am the user with nickname: \"Travis\"\n" + //
					"When i try to login using the password \"McCallum\"\n" + //
					"Then i get an error message of type \"Wrong Credentials\"\n" + //
					"\n";
}
