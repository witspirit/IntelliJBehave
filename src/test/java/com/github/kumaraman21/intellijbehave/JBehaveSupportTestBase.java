package com.github.kumaraman21.intellijbehave;

import static com.intellij.openapi.application.ReadAction.compute;

import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase5;

/**
 * Base test class for this plugin.
 */
public abstract class JBehaveSupportTestBase extends LightJavaCodeInsightFixtureTestCase5 {

    protected JBehaveSupportTestBase() {
        super(new DefaultLightProjectDescriptor(() -> JavaSdk.getInstance().createJdk("Real JDK", System.getenv("JAVA_HOME"), false))
            .withRepositoryLibrary("org.jbehave:jbehave-core:5.2.0"));
    }

    protected JBehaveSupportTestBase(DefaultLightProjectDescriptor projectDescriptor) {
        super(projectDescriptor);
    }

    protected int getCaretOffset() {
        return compute(() -> getFixture().getCaretOffset());
    }

    protected PsiElement getParentOfElementAtCaretIn(PsiFile psiFile) {
        return compute(() -> psiFile.findElementAt(getFixture().getCaretOffset()).getParent());
    }
}
