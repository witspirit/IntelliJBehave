JBehave Support for IntelliJ IDEA
=================================

[![JBehave Support](https://img.shields.io/jetbrains/plugin/v/7268-jbehave-support)](https://plugins.jetbrains.com/plugin/7268-jbehave-support)

IntelliJ IDEA Plugin for JBehave

<!-- Plugin description -->
This plugin provides some support for JBehave. It is a fork of IntelliJBehave, originally created by Aman Kumar.

**NOTE**: This project is now looking for a maintainer. You can find contact information in the project's `plugin.xml` under vendor details.

## Features

The plugin provides the following features:
* Basic syntax highlighting for JBehave story files
* Jump to step definition in Java or Groovy
* Error Highlighting in story if step was not defined
* Create new story files from a configurable story template
* Comment/uncomment lines in story files
* Code inspections to report unused steps definitions and undefined step usages
* Run *.story files
* Spellchecking in .story files
* Structure view for .story files (via Ctrl+12 or similar shortcut)

## Known limitations

* Searches complete module classpath, no configuration available to limit scope
* Does not take into account any custom JBehave configuration
* Lifecycles are currently not supported in the .story file structure view
<!-- Plugin description end -->

## Contribute

* Setup:
```
git clone <URL>
./gradlew idea
```

In IntelliJ just 'Open' that folder and everything should be ready.

* Run:
```
./gradlew runIde
```
You can do this from within IntelliJ if you enable gradle on the project.

## Contributions

See original documentation at https://github.com/kumaraman21/IntelliJBehave/wiki.

Most of the original code has been retained, but several improvements have been incorporated by various contributors:
* https://github.com/jarosite
* https://github.com/Arnauld
* https://github.com/harley84
* https://github.com/RodrigoQuesadaDev
* https://github.com/JenoDK