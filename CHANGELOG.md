# Release notes

## [Unreleased]

## [1.69.1]
### Changed
- JUnit Jupiter test dependencies brought in by jbehave-core are now excluded from the built plugin archive.

## [1.69.0]
### Changed
- New supported IDE version range: 2025.1+.

### Fixed
- Fixed a step reference resolution issue. Contributed by @tomb50

## [1.68.0]
### Changed
- New supported IDE version range: 2024.3 - 2025.1-EAP.

## [1.67.5]
### Fixed
- Added further exception handling when analyzing Java and Kotlin classes if they are step definition classes.

## [1.67.4]
### Fixed
- Added exception handling when analyzing Java classes if they are step definition classes.

## [1.67.3]
### Fixed
- Added exception handling when analyzing Kotlin classes if they are step definition classes.

## [1.67.2]
### Changed
- Added a minor performance improvement to pattern variant building.

### Fixed
- [#94](https://github.com/witspirit/IntelliJBehave/issues/94): Improved matching steps with patterns with empty parameter values.

## [1.67.1]
### Fixed
- Fixed the K2 compiler compatibility configuration.

## [1.67.0]
### Changed
- New supported IDE version range: 2024.2.1 - 2024.3.*.
The previous version, 1.66.0 (and bugfixes of it) of JBehave Support will remain to support IDEs up to version 2024.2.0.2. 
- Updated the project to use the IntelliJ Platform Gradle Plugin 2.0.
- Updated the project to use JDK 21.
- Updated project configuration to make sure the plugin works when the K2 Kotlin compiler is enabled.
- Removed a couple of deprecated API usage.

### Fixed
- Fixed the listener that tracks modifications of JBehave step def classes. It no longer fails when it encounters invalid files.
- Fixed a potential exception during highlighting undefined steps.

## [1.66.0]
### Changed
- New supported IDE version range: 2023.2.8 - 2024.2.0.2
- Added code documentation to a handful of Kotlin step definitions related classes.

## [1.65.0]
### Changed
- Added caching for the JBehave step reference resolution and Given-When-Then step annotation lookup.

## [1.64.0]
### Changed
- New supported IDE version range: 2023.1.6-2024.2-EAP
- Did some optimizations on the unused step definition method inspection. Now it also marks the method name instead of
the entire method.
- Did some optimizations on the undefined step inspection.
- Removed duplicate reporting of steps that don't have step definition methods.
- Removed the class `StoryAnnotator` that didn't seem to do much useful.
- Improved the implementations of JBehave step reference handling.

## [1.63.0]
### Changed
- New supported IDE version range: 2022.2.5-2024.1-EAP

## [1.62.0]
### Changed
- New supported IDE version range: 2022.1-2023.3
- Removed some deprecated API usage, and simplified some related code.

## [1.61.0]
### Changed
- Added support for IJ-2023.2 and dropped support for IJ-2021.2.
- Updated the plugin configuration to be based on the intellij-platform-plugin-template
- Updated GitHub Actions integration to build and validate the plugin.
- Removed unnecessary project dependencies.
