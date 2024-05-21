# Release notes

## [Unreleased]

## [1.64.0]
### Changed
- Did some optimizations on the unused step definition method inspection. Now it also marks the method name instead of
the entire method.
- Did some optimizations on the undefined step inspection.
- Removed duplicate reporting of steps that don't have step definition methods.
- Removed the class `StoryAnnotator` that didn't seem to do much useful.

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
