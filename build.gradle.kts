import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(21)
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    //JBehave

    implementation("org.jbehave:jbehave-core:5.2.0")

    //Testing

    //Required for 'junit.framework.TestCase' referenced in 'com.intellij.testFramework.UsefulTestCase'
    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)
    testImplementation("org.assertj:assertj-core:3.27.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")

    //Others

    implementation("org.apache.commons:commons-text:1.13.0")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html

    intellijPlatform {
        create(providers.gradleProperty("platformType"), providers.gradleProperty("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        pluginVerifier()
        zipSigner()
        testFramework(TestFrameworkType.Platform)
        //Required for 'LightJavaCodeInsightFixtureTestCase5'
        testFramework(TestFrameworkType.Plugin.Java)
        //Required for the 'com.intellij.testFramework.junit5' package
        testFramework(TestFrameworkType.JUnit5)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
//            untilBuild = providers.gradleProperty("pluginUntilBuild")
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

intellijPlatformTesting {
    val runTestsInIJCommunity by intellijPlatformTesting.testIde.registering {
        type = IntelliJPlatformType.IntellijIdeaCommunity
        version = "2024.3"
        task {
            useJUnitPlatform {
                isScanForTestClasses = false
                include("**/codeInspector/*Test.class", "**/resolver/*Test.class", "**/utility/*Test.class", "**/service/*Test.class", "**/jbehave/core/steps/*Test.class")
                exclude("**/highlighter/*Test.class", "**/parser/*Test.class", "**/spellchecker/*Test.class", "**/structure/*Test.class")
            }
        }
    }

    val runTestsWithK2InIJCommunity by intellijPlatformTesting.testIde.registering {
        type = IntelliJPlatformType.IntellijIdeaCommunity
        version = "2024.3"
        task {
            //See https://kotlin.github.io/analysis-api/testing-in-k2-locally.html
            jvmArgumentProviders += CommandLineArgumentProvider {
                listOf("-Didea.kotlin.plugin.use.k2=true")
            }
            useJUnitPlatform {
                isScanForTestClasses = false
                include("**/codeInspector/*Test.class", "**/resolver/*Test.class", "**/utility/*Test.class", "**/service/*Test.class", "**/jbehave/core/steps/*Test.class")
                exclude("**/highlighter/*Test.class", "**/parser/*Test.class", "**/spellchecker/*Test.class", "**/structure/*Test.class")
            }
        }
    }

    val runJUnit3TestsInIJCommunity by intellijPlatformTesting.testIde.registering {
        type = IntelliJPlatformType.IntellijIdeaCommunity
        version = "2024.3"
        task {
            useJUnit {
                include("**/highlighter/*Test.class", "**/parser/*Test.class", "**/spellchecker/*Test.class", "**/structure/*Test.class")
                exclude("**/highlighter/StoryLocalizedLexer_FrenchTest.class")
            }
        }
    }
}

//Uncomment this to start the IDE with the K2 Kotlin compiler enabled
//tasks.named<RunIdeTask>("runIde") {
//    jvmArgumentProviders += CommandLineArgumentProvider {
//        listOf("-Didea.kotlin.plugin.use.k2=true")
//    }
//}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }
}
