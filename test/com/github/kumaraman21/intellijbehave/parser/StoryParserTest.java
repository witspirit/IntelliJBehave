package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.Samples;
import com.github.kumaraman21.intellijbehave.peg.StoryPegLexer;
import com.github.kumaraman21.intellijbehave.peg.StoryPegParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.openapi.project.Project;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 * @see IntelliJBehaveBaseTestCase
 */
@Ignore("... need a full plateform initialization!?!")
public class StoryParserTest {

    @Test
    public void test1() throws Throwable {
        Project project = mock(Project.class);
        PsiBuilder builder = new PsiBuilderImpl(project, null, new StoryPegParserDefinition(), new StoryPegLexer(), null, Samples.SIMPLE_SAMPLE, null, null);

        StoryPegParser parser = new StoryPegParser();
        parser.parse(StoryElementType.STORY_FILE, builder);
    }
}
