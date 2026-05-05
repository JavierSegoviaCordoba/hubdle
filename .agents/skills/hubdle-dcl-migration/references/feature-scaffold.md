# Feature Scaffold

Use this reference when creating a new Hubdle Declarative feature module.

## Module

Create a module with the `hubdle-declarative-*` prefix.

Examples:

```text
hubdle-declarative-features/hubdle-declarative-kotlin
hubdle-declarative-features/hubdle-declarative-config-versioning
hubdle-declarative-features/hubdle-declarative-config-versioning-semver
```

Use direct feature names for `hubdle { feature {} }`.
Use parent names for nested features, for example `config-versioning-semver` for:

```kotlin
hubdle {
    config {
        versioning {
            semver {
            }
        }
    }
}
```

## `build.gradle.kts`

Copy the shape from:

```text
hubdle-declarative-features/hubdle-declarative-config/build.gradle.kts
```

Keep the usual setup unless the feature needs less:

- Hubdle `config` setup.
- Kotlin JVM.
- Java 17.
- `api(projects.platform)` for shared DCL/platform APIs.
- Third-party plugin artifacts as `api(...)` only when the `ApplyAction` applies or configures
  their public APIs.
- `testFunctional` dependencies:

```kotlin
implementation(projects.hubdleDeclarative)
implementation(testFixtures(projects.platform))
```

Use `projects.hubdleDeclarative` in functional tests so the fixture can apply
`com.javiersc.hubdle.declarative`.

## Source Directories

Create:

```text
main/kotlin
```

Direct feature package:

```text
main/kotlin/hubdle/declarative/FEATURE_NAME/
```

Nested feature package:

```text
main/kotlin/hubdle/declarative/config/FEATURE_NAME/
```

Example:

```text
main/kotlin/hubdle/declarative/config/
```

## Core Files

Create these files, using the feature name:

```text
HubdleFeatureNameApplyAction.kt
HubdleFeatureNameBuildModel.kt
HubdleFeatureNameDefinition.kt
HubdleFeatureNameFeature.kt
```

For config, those are:

```text
HubdleConfigApplyAction.kt
HubdleConfigBuildModel.kt
HubdleConfigDefinition.kt
HubdleConfigFeature.kt
```

## Register The Module

Wire the module into `hubdle-declarative`.

In:

```text
hubdle-declarative/build.gradle.kts
```

add:

```kotlin
api(projects.hubdleDeclarativeFeatures.hubdleDeclarativeFeatureName)
```

In:

```text
hubdle-declarative/main/kotlin/hubdle/declarative/HubdleDeclarativePlugin.kt
```

import the feature and register it:

```kotlin
@RegistersProjectFeatures(HubdleProjectType::class, HubdleConfigFeature::class, NewFeature::class)
public open class HubdleDeclarativePlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
        // NO-OP
    }
}
```

The plugin applied in `settings.gradle.dcl` must stay a `Plugin<Settings>`.
