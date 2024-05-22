package com.github.kumaraman21.intellijbehave;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase5;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

/**
 * Base test class for this plugin.
 */
public abstract class JBehaveSupportTestBase extends LightJavaCodeInsightFixtureTestCase5 {

    protected JBehaveSupportTestBase() {
        super(new DefaultLightProjectDescriptor(() -> JavaSdk.getInstance().createJdk("Real JDK", System.getenv("JAVA_HOME"), false)));
    }

    protected JBehaveSupportTestBase(DefaultLightProjectDescriptor projectDescriptor) {
        super(projectDescriptor);
    }

    @BeforeEach
    protected void setUp() {
        loadLibrary(getFixture().getProjectDisposable(), getFixture().getModule(), "JBehave Core", "jbehave-core-5.1.1.jar");
    }

    private static void loadLibrary(@NotNull Disposable projectDisposable, @NotNull Module module, String libraryName, String libraryJarName) {
        String libPath = PathUtil.toSystemIndependentName(new File("lib").getAbsolutePath());
        VfsRootAccess.allowRootAccess(projectDisposable, libPath);
        PsiTestUtil.addLibrary(projectDisposable, module, libraryName, libPath, libraryJarName);
    }
}
