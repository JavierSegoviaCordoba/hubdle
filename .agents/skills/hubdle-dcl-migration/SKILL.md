---
name: hubdle-dcl-migration
description: Migrate Hubdle or Hubdle-like Gradle plugin DSLs from normal imperative Gradle extensions to Gradle Declarative DCL. Use when converting HubdleExtension, HubdleConfigExtension, Kotlin/Java/Gradle/config/versioning DSL blocks, enableable extensions, plugin aggregation, or nested Gradle DSL actions into DCL project types, project features, definitions, build models, and settings registration.
---

# Hubdle DCL Migration

## Goal

Migrate Hubdle's normal Gradle DSL to a Declarative Gradle surface while keeping existing
implementation logic reusable. Treat `hubdle {}` as the project identity and nested Hubdle
capabilities as project features.

Use this repository module split during migration:

- `hubdle-declarative-gradle`: owns the declarative plugin entrypoint and the `hubdle {}` project
  type registration.
- `hubdle-declarative-features`: owns Hubdle declarative features, organized as a multi-module tree
  with one module per feature and per sub-feature.

Current baseline already implemented in this repo:

- `com.javiersc.hubdle.declarative.HubdleDeclarativePlugin` is the settings plugin and registers
  `HubdleProjectType`.
- `com.javiersc.hubdle.declarative.HubdleProjectType` binds `hubdle`.
- `com.javiersc.hubdle.declarative.MainHubdleDefinition` extends shared
  `hubdle.platform.HubdleDefinition`.
- `HubdleApplyAction` uses `HubdleServices`, applies the base plugin, and registers lifecycle tasks.
- Functional smoke test exists at
  `hubdle-declarative-gradle/testFunctional/kotlin/com/javiersc/hubdle/declarative/HubdleDeclarativeTest.kt`.

Target shape:

```gradle
hubdle {
    config {
        versioning {
            semver {
                tagPrefix = "v"
            }
        }
    }

    kotlin {
        jvm {
        }
    }
}
```

## Core Mapping

Use this mapping first:

```text
Hubdle root DSL block       -> ProjectType: hubdle {}
Nested Hubdle DSL blocks    -> ProjectFeature: hubdle { config {} }, hubdle { kotlin {} }, etc.
User-facing DSL state       -> Definition interfaces
Derived/internal state      -> BuildModel interfaces
Existing task/plugin logic  -> ApplyAction or shared configurer called by ApplyAction
Settings plugin             -> Plugin<Settings> with @RegistersProjectFeatures(...)
```

Do not translate Kotlin DSL action functions directly. DCL needs managed model definitions, not
arbitrary `Action<T>` methods.

## Review Before Editing

Before modifying files, inspect:

- Current extension class and all child extensions.
- Current plugin apply flow and plugin IDs applied by Hubdle.
- Existing tests/resources for the DSL block being migrated.
- Whether the DSL operation is declarative data or imperative behavior.
- Whether the feature needs parent-derived state or only its own user input.

Only migrate declarative data to DCL. Keep imperative callbacks, lambdas, and arbitrary actions in
Kotlin DSL or replace them with explicit typed options.

## Migration Workflow

1. Locate the current DSL entrypoint. For Hubdle this is `HubdleExtension` and
   `HubdleProjectPlugin`.
2. In `hubdle-declarative-gradle`, keep `HubdleDeclarativePlugin` as `Plugin<Settings>` and register
   project types/features there.
3. In `hubdle-declarative-gradle`, keep `HubdleProjectType` bound to `hubdle`.
4. In `hubdle-declarative-features`, convert each direct child of `hubdle {}` into a
   `ProjectFeature` module.
5. For nested blocks, keep splitting into sub-feature modules (one module per sub-feature) under
   `hubdle-declarative-features`.
6. Convert each extension class property to a DCL-safe `Definition` property.
7. Move default values to read-side providers with `orElse(...)`, settings `defaults {}`, or
   internal build model. Do not mutate finalized definitions from apply actions.
8. Keep Hubdle's existing logic behind shared configurer functions so Kotlin DSL and DCL can coexist
   during migration.
9. Add functional tests with `settings.gradle.dcl`, `build.gradle.dcl`, `gradle.properties`, and
   Gradle version/flags known to support DCL.

## Coexistence Strategy

Keep the current Kotlin DSL plugin and add a separate DCL registration plugin. Do not point both
plugin IDs to the same class.

```kotlin
gradlePlugin {
    plugins.register("hubdleProject") {
        id = "com.javiersc.hubdle"
        implementationClass = "com.javiersc.hubdle.project.HubdleProjectPlugin"
    }

    plugins.register("hubdleDeclarative") {
        id = "com.javiersc.hubdle.declarative"
        implementationClass = "com.javiersc.hubdle.declarative.HubdleDeclarativePlugin"
    }
}
```

The normal Kotlin DSL surface continues to use:

```kotlin
plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        versioning {
            semver {
                tagPrefix("v")
            }
        }
    }
}
```

The DCL surface applies only the settings plugin:

```gradle
// settings.gradle.dcl
plugins {
    id("com.javiersc.hubdle.declarative")
}
```

```gradle
// build.gradle.dcl
hubdle {
    config {
        versioning {
            semver {
                tagPrefix = "v"
            }
        }
    }
}
```

Both surfaces should call the same implementation:

```kotlin
object HubdleSemverConfigurer {
    fun configure(project: Project, tagPrefix: Provider<String>, enabled: Provider<Boolean>) {
        if (enabled.get()) {
            project.pluginManager.apply("com.javiersc.semver")
            project.extensions.configure<SemverExtension> {
                this.tagPrefix.set(tagPrefix)
            }
        }
    }
}
```

Hubdle-owned shared configurers should accept plain Gradle providers/values, not DCL `Definition`
types or Kotlin DSL extension types. Keep those in adapters only.

```kotlin
// Kotlin DSL adapter
HubdleSemverConfigurer.configure(project, extension.tagPrefix, extension.isFullEnabled)

// DCL adapter
HubdleSemverConfigurer.configure(
    project = project,
    tagPrefix = definition.tagPrefix.orElse(defaultTagPrefix(project)),
    enabled = definition.enabled.orElse(true),
)
```

## Project Type Template

Use a project type for `hubdle {}` because it is the top-level project identity.

```kotlin
@BindsProjectType(HubdleProjectType::class)
public abstract class HubdleProjectType : Plugin<Project>, ProjectTypeBinding {
    override fun apply(target: Project) = Unit

    override fun bind(builder: ProjectTypeBindingBuilder) {
        builder.bindProjectType("hubdle", ApplyAction::class)
            .withUnsafeDefinition()
            .withBuildModelImplementationType(DefaultHubdleBuildModel::class.java)
            .withUnsafeApplyAction()
    }

    public abstract class ApplyAction :
        ProjectTypeApplyAction<HubdleDefinition, HubdleBuildModel> {
        @get:Inject public abstract val project: Project
        @get:Inject public abstract val pluginManager: PluginManager

        override fun apply(
            context: ProjectFeatureApplicationContext,
            definition: HubdleDefinition,
            buildModel: HubdleBuildModel,
        ) {
            pluginManager.apply(BasePlugin::class.java)
            project.registerHubdleLifecycleTasks()

            (buildModel as DefaultHubdleBuildModel).enabled =
                definition.enabled.orElse(true)

            HubdleConfigurer.configure(project, definition, buildModel)
        }
    }
}

public interface HubdleDefinition : Definition<HubdleBuildModel> {
    public val enabled: Property<Boolean>
}

public interface HubdleBuildModel : BuildModel {
    public val enabled: Provider<Boolean>
}

internal abstract class DefaultHubdleBuildModel : HubdleBuildModel {
    override lateinit var enabled: Provider<Boolean>
}
```

## Project Feature Template

Use a project feature for every nested Hubdle capability.

```kotlin
@BindsProjectFeature(HubdleConfigFeature::class)
public abstract class HubdleConfigFeature : Plugin<Project>, ProjectFeatureBinding {
    override fun apply(target: Project) = Unit

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder.bindProjectFeature("config", ApplyAction::class)
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }

    public abstract class ApplyAction :
        ProjectFeatureApplyAction<HubdleConfigDefinition, BuildModel.None, HubdleDefinition> {
        @get:Inject public abstract val project: Project

        override fun apply(
            context: ProjectFeatureApplicationContext,
            definition: HubdleConfigDefinition,
            buildModel: BuildModel.None,
            parentDefinition: HubdleDefinition,
        ) {
            HubdleConfigConfigurer.configure(project, definition, parentDefinition)
        }
    }
}

public interface HubdleConfigDefinition : Definition<BuildModel.None> {
    public val enabled: Property<Boolean>
    public val group: Property<String>
}
```

If a feature must attach to a specific parent build model, bind it to that model instead of using
only parent definition. Prefer explicit build models when features need data produced by the parent.

`withUnsafeDefinition()` and `withUnsafeApplyAction()` are temporary DCL/incubating API escape
hatches used by current Gradle Declarative examples. Keep them while required by the current Gradle
version; remove them only when the stable API supports the same binding without unsafe calls.

## Lifecycle And Ordering

Do not rely on incidental feature apply order unless Gradle's binding contract explicitly provides
it for that relationship. Model dependencies through parent definitions, build models, required
plugins, task dependencies, and provider wiring.

- Put data needed by children into the parent `BuildModel`.
- Make features idempotent; applying them twice or in a different order should not corrupt state.
- Use `pluginManager.apply(...)` and `plugins.withId(...)` for plugin-dependent configuration.
- Use task providers and `dependsOn` instead of assuming tasks already exist.
- If one feature truly requires another feature, express it in the model shape or combine them under
  one parent feature instead of depending on execution order.

```kotlin
// Assumes versioning feature already ran and configured a global mutable variable.
HubdleSemverState.tagPrefix.get()
```

```kotlin
// Parent model carries data; consumers use providers.
(buildModel as DefaultHubdleVersioningBuildModel).tagPrefix =
    definition.tagPrefix.orElse("")
```

For third-party plugins, apply the plugin if the feature owns it, then configure by plugin id:

```kotlin
if (definition.enabled.orElse(true).get()) {
    pluginManager.apply("io.gitlab.arturbosch.detekt")
}

project.plugins.withId("io.gitlab.arturbosch.detekt") {
    project.extensions.configure<DetektExtension> {
        buildUponDefaultConfig = true
    }
}
```

If the feature only reacts to an optional third-party plugin, skip `pluginManager.apply(...)`.

## Managed Containers

Use managed containers for repeated child elements such as source sets, publications, targets,
environments, or named tool configurations.

```kotlin
public interface HubdleTestingDefinition : Definition<BuildModel.None> {
    @get:Nested
    public val suites: NamedDomainObjectContainer<HubdleTestSuiteDefinition>
}

public interface HubdleTestSuiteDefinition : Named {
    public val enabled: Property<Boolean>
    public val displayName: Property<String>

    @get:Nested
    public val targets: NamedDomainObjectContainer<HubdleTestTargetDefinition>
}

public interface HubdleTestTargetDefinition : Named {
    public val enabled: Property<Boolean>
}
```

```gradle
hubdle {
    testing {
        suites {
            hubdleTestSuite("integrationTest") {
                displayName = "Integration tests"
                targets {
                    hubdleTestTarget("jvm") {
                        enabled = true
                    }
                }
            }
        }
    }
}
```

```kotlin
definition.suites.all {
    val suiteDefinition = this
    testing.suites.register(suiteDefinition.name, JvmTestSuite::class) {
        targets.all {
            // Configure each target from suiteDefinition.targets.
        }
    }
}
```

If a Gradle core type uses wildcards, `Any`, or unsupported generic shapes, create a DCL-specific
interface and map it manually in the apply action.

## Settings Registration

The plugin applied in `settings.gradle.dcl` must be `Plugin<Settings>`.

```kotlin
@RegistersProjectFeatures(HubdleProjectType::class)
public abstract class HubdleDeclarativePlugin : Plugin<Settings> {
    override fun apply(target: Settings) = Unit
}
```

The plugin marker must point to this settings plugin, not to `HubdleProjectType`.

```kotlin
gradlePlugin {
    plugins.register("hubdleDeclarative") {
        id = "com.javiersc.hubdle.declarative"
        implementationClass = "com.javiersc.hubdle.declarative.HubdleDeclarativePlugin"
    }
}
```

## DCL Model Rules

Follow these rules strictly:

- Use `@BindsProjectType` for top-level `hubdle {}`.
- Use `@BindsProjectFeature` for nested blocks inside `hubdle {}`.
- Do not model a standalone plugin's `ProjectType` as nested under Hubdle. To nest it, expose or
  recreate a feature surface.
- Duplicating DCL surface is acceptable; duplicating Hubdle implementation logic is not.
- Keep DCL binding internals `internal` by default: project types, features, definitions, apply
  actions, and build model implementations.
- Make only plugin marker implementation classes `public` unless Gradle or a real external API need
  forces wider visibility.
- Use `enabled: Property<Boolean>`, not `isEnabled: Property<Boolean>`, because
  `isEnabled(): Property<Boolean>` is not a valid managed Boolean getter shape.
- Reuse `hubdle.platform.HubdleDefinition` for shared flags (`enabled`, `loggingEnabled`,
  `featureName`) instead of duplicating those properties in each definition.
- Do not call `definition.someProperty.convention(...)` in apply actions if DCL may have finalized
  the definition. Use `definition.someProperty.orElse(default)` instead.
- Use `@get:Nested` for nested definition objects and managed containers.
- Avoid arbitrary methods such as `fun semver(action: Action<SemverExtension>)` in definitions. DCL
  definitions should expose typed properties and nested managed objects.
- Avoid `Any`, wildcard-heavy Gradle API types, and Kotlin function types in DCL definitions. Create
  DCL-specific interfaces when Gradle core types are not schema-friendly.

## Converting Existing Hubdle Extensions

For an existing extension:

```kotlin
public open class HubdleConfigVersioningSemverExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {
    override val isEnabled: Property<Boolean> = property { true }
    public val tagPrefix: Property<String> = defaultTagPrefix()
    public fun mapVersion(action: VersionMapper) { ... }
}
```

Create a DCL definition:

```kotlin
public interface HubdleSemverDefinition : Definition<BuildModel.None> {
    public val enabled: Property<Boolean>
    public val tagPrefix: Property<String>
}
```

Then bridge through the same provider-based configurer used by Kotlin DSL:

```kotlin
HubdleSemverConfigurer.configure(
    project = project,
    tagPrefix = definition.tagPrefix.orElse(defaultTagPrefix(project)),
    enabled = definition.enabled.orElse(true),
)
```

Do not expose `mapVersion(VersionMapper)` in DCL unless it can be represented declaratively. If not,
keep it Kotlin DSL-only or replace it with typed declarative options.

## Feature Ownership

If the feature belongs semantically to Hubdle, define it in Hubdle:

```gradle
hubdle {
    config {
        versioning {
            semver {}
        }
    }
}
```

This can coexist with an external standalone plugin:

```gradle
semver {}
```

But that standalone `semver {}` is a different DCL entrypoint. Hubdle must not depend on another
plugin's internal DCL `ProjectType`, `Definition`, `ApplyAction`, or `BuildModel`; those are usually
internal and specific to that plugin's top-level surface.

```text
semver-declarative
  SemverDeclarativePlugin -> public Plugin<Settings>
  SemverProjectType -> semver {}
  SemverDefinition -> internal
  SemverApplyAction -> internal

hubdle-declarative
  HubdleProjectType -> hubdle {}
  HubdleSemverFeature -> hubdle { config { versioning { semver {} } } }
```

When integrating an external plugin such as SemVer, treat it as a black box unless it intentionally
exposes a normal public Gradle API. The Hubdle feature owns its DCL definition and maps it to the
external plugin by applying the plugin and configuring whatever public extension/task API exists.

```kotlin
internal interface HubdleSemverDefinition : Definition<BuildModel.None> {
    val enabled: Property<Boolean>
    val tagPrefix: Property<String>
}

internal abstract class HubdleSemverApplyAction :
    ProjectFeatureApplyAction<HubdleSemverDefinition, BuildModel.None, HubdleVersioningDefinition> {
    @get:Inject abstract val project: Project
    @get:Inject abstract val pluginManager: PluginManager

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleSemverDefinition,
        buildModel: BuildModel.None,
        parentDefinition: HubdleVersioningDefinition,
    ) {
        if (definition.enabled.orElse(true).get()) {
            pluginManager.apply("com.javiersc.semver")
        }

        project.plugins.withId("com.javiersc.semver") {
            project.extensions.configure<SemverExtension> {
                tagPrefix.set(definition.tagPrefix.orElse(""))
            }
        }
    }
}
```

Use `BuildModel.None` for Hubdle features that do not expose derived state to child features.
Introduce a real `BuildModel` only when a parent feature must pass providers/data to subfeatures.

## Testing Checklist

For functional tests, write a fixture with:

```text
settings.gradle.dcl
build.gradle.dcl
gradle.properties
```

`settings.gradle.dcl`:

```gradle
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("com.javiersc.hubdle.declarative")
}

rootProject.name = "sample"
```

`gradle.properties`:

```properties
org.gradle.kotlin.dsl.dcl=true
```

`build.gradle.dcl`:

```gradle
hubdle {
    enabled = true
    loggingEnabled = true
}
```

Verify these failures explicitly:

- `unresolved function call signature for 'hubdle'` means the settings plugin did not register/apply
  the project type or Gradle is using the wrong version/flags.
- `Unexpected plugin type` means the plugin id applied in settings points to a `Plugin<Project>`
  instead of `Plugin<Settings>`.
- `does not expose a project feature` means a registered class lacks `@BindsProjectType` or
  `@BindsProjectFeature`.
- `Cannot have abstract method ... isEnabled(): Property<Boolean>` means a definition used
  `isEnabled`; rename to `enabled`.
- `property ... is final and cannot be changed` means an apply action tried to mutate a finalized
  definition; consume with `orElse`.

## Functional Tests And Test Fixtures

Use this repository pattern for declarative modules:

- Keep functional tests focused on behavior, not helper/formatting duplication.
- Reuse existing `testFixtures` first (currently platform fixtures such as logging helpers).
- Create new fixtures only when existing ones cannot express the scenario cleanly.
- If a declarative module needs shared fixtures, wire them in its `testFunctional` source set
  dependencies.

Current wiring pattern:

```kotlin
testFunctional {
    dependencies {
        implementation(testFixtures(projects.platform))
    }
}
```

Feature/sub-feature modules under `hubdle-declarative-features` should follow the same approach:

- add fixtures dependency in `testFunctional` the same way,
- prefer shared fixtures from platform (or other shared fixture modules),
- keep feature-local fixtures only for genuinely feature-specific helpers.
