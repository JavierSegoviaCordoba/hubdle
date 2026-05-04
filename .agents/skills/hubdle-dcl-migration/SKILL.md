---
name: hubdle-dcl-migration
description: Migrate Hubdle or Hubdle-like Gradle plugin DSLs from normal imperative Gradle extensions to Gradle Declarative DCL. Use when converting HubdleExtension, HubdleConfigExtension, Kotlin/Java/Gradle/config/versioning DSL blocks, enableable extensions, plugin aggregation, or nested Gradle DSL actions into DCL project types, project features, definitions, build models, and settings registration.
---

# Hubdle DCL Migration

## Goal

Create Hubdle Gradle Declarative features without getting lost.

Use the existing config feature as the reference:

```text
hubdle-declarative-features/hubdle-declarative-config
```

Current shape:

- `hubdle-declarative` owns the settings plugin and root `hubdle {}` project type.
- `hubdle-declarative-features` owns feature modules.
- `hubdle {}` is the root project type.
- Nested blocks such as `hubdle { config {} }` are project features.

## Before Editing

Inspect the current feature or DSL block first:

1. Find the existing Kotlin DSL extension and plugin apply flow.
2. Identify what is declarative data and what is imperative behavior.
3. Keep imperative lambdas/actions out of DCL unless they can become typed declarative options.
4. Reuse existing Hubdle implementation logic through provider/value based configurers when
   possible.
5. Use `hubdle-declarative-config` as the concrete implementation reference.

## Create A New Feature

Follow these steps in order.

### 1. Create The Module

Create a module with the `hubdle-declarative-*` prefix.

Examples:

```text
hubdle-declarative-features/hubdle-declarative-kotlin
hubdle-declarative-features/hubdle-declarative-config-versioning
hubdle-declarative-features/hubdle-declarative-config-versioning-semver
```

Use direct feature names for `hubdle { feature {} }`.
Use parent names for nested features, for example `config-versioning-semver` for
`hubdle { config { versioning { semver {} } } }`.

### 2. Add `build.gradle.kts`

Copy the shape from:

```text
hubdle-declarative-features/hubdle-declarative-config/build.gradle.kts
```

Keep the usual setup unless the feature needs less:

- Hubdle `config` setup.
- Kotlin JVM.
- Java 17.
- `api(projects.platform)` for shared DCL/platform APIs.
- Third-party plugin artifacts as `api(...)` only when the ApplyAction applies or configures their
  public APIs.
- `testFunctional` dependencies:

```kotlin
implementation(projects.hubdleDeclarative)
implementation(testFixtures(projects.platform))
```

Use `projects.hubdleDeclarative` in functional tests so the fixture can apply
`com.javiersc.hubdle.declarative`.

### 3. Create Source Directories

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

### 4. Create The Four Core Files

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

### 5. Implement `Feature`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleConfigFeature::class)
public open class HubdleConfigFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(HubdleConfigApplyAction.NAME, HubdleConfigApplyAction::class)
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
```

Rules:

- Binding name is the DCL block name.
- Keep feature classes public if `HubdleDeclarativePlugin` registers them.
- Keep actual behavior in `ApplyAction`, not in `Plugin<Project>.apply`.

### 6. Implement `Definition`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val group: Property<String>
}
```

Rules:

- Extend `HubdleDefinition` for Hubdle features.
- Set `featureName` to the DSL block name.
- Use `enabled: Property<Boolean>` from `HubdleDefinition`.
- Add only declarative user-facing state here.
- Use typed managed Gradle properties.
- Use `@get:Nested` for nested definition objects and managed containers.
- Do not add arbitrary action/lambda methods.
- Do not use `isEnabled: Property<Boolean>` in DCL definitions.

#### Factory Functions

Use factory functions when the DSL needs to assign a declarative object value without a named
container or configuration block.

Example DSL:

```kotlin
hubdle {
    config {
        foo = foo("foo", 42)
    }
}
```

Factory functions are value expressions. They must be used where their returned value is consumed,
for example on the right side of an assignment:

```kotlin
foo = foo("foo", 42)
```

or inside a collection that is assigned to a property:

```kotlin
foos = listOf(
    foo("main", 1),
    foo("test", 2)
)
```

Do not call them as standalone statements:

```kotlin
foo("foo", 42) // invalid for this pattern
```

Standalone calls require an explicit adding function shape; that is a different DSL pattern from
these simple value factories.

Definition pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HiddenInDefinition
import org.gradle.features.binding.Definition
import org.gradle.kotlin.dsl.newInstance

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val foo: Property<HubdleConfigFooDefinition>

    @get:Inject
    @get:HiddenInDefinition
    public val objects: ObjectFactory

    public fun foo(name: String, number: Int): HubdleConfigFooDefinition =
        objects.newInstance<HubdleConfigFooDefinition>().also { foo ->
            foo.name.set(name)
            foo.number.set(number)
        }
}

public interface HubdleConfigFooDefinition {
    public val name: Property<String>
    public val number: Property<Int>
}
```

For multiple values, expose a collection property and assign a list of factory-created objects:

```kotlin
public val foos: ListProperty<HubdleConfigFooDefinition>
```

```kotlin
foos = listOf(
    foo("main", 1),
    foo("test", 2)
)
```

Rules:

- Create returned objects with `ObjectFactory.newInstance<T>()`.
- Inject `ObjectFactory` in the definition with `@get:Inject`.
- Hide injected services from the DCL schema with `@get:HiddenInDefinition`.
- Do not use `HiddenInDeclarativeDsl`; it is deprecated.
- Keep factory functions declarative: set properties on a model object and return it.
- Use factory functions only as consumed expressions: assignment RHS or inside an assigned
  collection.
- Do not call factory functions as standalone statements; DCL rejects pure value calls whose result
  is unused.
- Do not apply plugins, register tasks, mutate the project, or run imperative build logic in factory
  functions.
- Prefer factory functions for small value objects with simple constructor-like arguments.
- Prefer project features, nested definitions, or managed containers when elements need their own
  configuration blocks or apply behavior.

#### Adding Functions

Use `@Adding` when the DSL needs a standalone function call:

```kotlin
hubdle {
    config {
        bar()
        bar("name", 42)
    }
}
```

Definition pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.declarative.dsl.model.annotations.Adding
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    @Adding
    public fun bar() {
        // Mutate declarative model state here, or intentionally trigger an adding operation.
    }

    @Adding
    public fun bar(name: String, number: Int) {
        // Parameters are supported for standalone adding calls.
    }
}
```

Rules:

- Add `@Adding` to functions that must be callable as standalone DCL statements.
- `@Adding` functions may have parameters, for example `bar("name", 42)`.
- A plain function call such as `bar()` is not valid unless the function has DCL-recognized
  semantics such as `@Adding`.
- Keep `@Adding` functions model-oriented. They may update managed declarative properties or add
  model elements, but should not apply plugins, register tasks, mutate `Project`, or run build logic.
- Use a pure factory function when the result should be assigned:
  `foo = foo("foo", 42)`.
- Use an `@Adding` function when the call itself is the DSL operation:
  `bar()`.
- A `Unit` returning `@Adding` function works for standalone calls without a configuration block.
- Gradle's DCL schema builder rejects a `Unit` returning `@Adding` function when its last parameter
  is treated as a configuration lambda. If an element needs a block, validate the exact return type
  and lambda shape with a functional test before documenting it as supported.

### 7. Implement `BuildModel`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleConfigBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
```

Rules:

- Use `HubdleBuildModel` for features that need parent-aware enablement.
- Use `effectiveEnabled` to pass enablement to child features.
- Use `BuildModel.None` only for features with no derived state and no child state needs.

### 8. Implement `ApplyAction`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleRootDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleConfigApplyAction :
    ProjectFeatureApplyAction<HubdleConfigDefinition, HubdleConfigBuildModel, HubdleRootDefinition>,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigDefinition,
        buildModel: HubdleConfigBuildModel,
        parentDefinition: HubdleRootDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
            // Configure project behavior here.
        }
    }

    companion object {
        const val NAME = "config"
    }
}
```

Rules:

- Direct children of `hubdle {}` use `HubdleRootDefinition` as parent.
- Nested features use the immediate parent definition.
- Compute `featureEffectiveEnabled` first.
- Do nothing when disabled.
- Use `definition.someProperty.orElse(default)` for defaults.
- Do not call `convention(...)` on finalized DCL definitions inside apply actions.
- Apply owned plugins with `pluginManager.apply(...)`.
- Configure optional plugins with `plugins.withId(...)`.
- Reuse Kotlin DSL behavior through shared provider/value based configurers.

### 9. Register The Feature

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

### 10. Add Functional Tests

Before creating new DCL tests, look for functional tests associated with the feature being
migrated.

Search at least:

```text
hubdle-gradle-plugin/testFunctional/kotlin
hubdle-gradle-plugin/testFunctional/resources
```

Use the existing imperative plugin functional tests as behavioral source material. Migrate their
fixtures and assertions when they cover real feature behavior, instead of replacing them with only
feature-enabled log assertions.

Create test class:

```text
testFunctional/kotlin/hubdle/declarative/<feature>/HubdleDeclarativeFeatureNameTest.kt
```

Use:

```kotlin
class HubdleDeclarativeConfigTest : GradleTestKitTest()
```

Create fixtures:

```text
testFunctional/resources/<fixture-group>/basic/settings.gradle.dcl
testFunctional/resources/<fixture-group>/basic/build.gradle.dcl
testFunctional/resources/<fixture-group>/hubdle-disabled/settings.gradle.dcl
testFunctional/resources/<fixture-group>/hubdle-disabled/build.gradle.dcl
```

`settings.gradle.dcl`:

```gradle
plugins {
    id("com.javiersc.hubdle.declarative")
}

rootProject.name = "hubdle-feature-sandbox"
```

Basic `build.gradle.dcl`:

```gradle
hubdle {
    config {
        group = "foo-some"
    }
}
```

Disabled `build.gradle.dcl`:

```gradle
hubdle {
    enabled = false

    config {
    }
}
```

Positive test pattern:

```kotlin
gradleTestKitTest("hubdle-config/basic") {
    gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
        .output
        .shouldContainInOrder(
            lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
            lifecycle("config") { "Feature 'config' enabled on ':'" },
        )
}
```

Disabled test pattern:

```kotlin
gradleTestKitTest("hubdle-config/hubdle-disabled") {
    gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
        .output
        .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
        .shouldNotContain(lifecycle("config") { "Feature 'config' enabled on ':'" })
}
```

Snapshot/fixture migration pattern, when the original feature has output-based functional tests:

- Copy the relevant original fixture directories into the DCL feature resources under a feature
  specific group, for example:

```text
hubdle-declarative-features/<feature>/testFunctional/resources/<fixture-group>/snapshots/snapshot-1
```

- Convert only the Gradle entrypoint needed for the migrated feature to DCL:

```text
settings.gradle.kts -> settings.gradle.dcl
build.gradle.kts -> build.gradle.dcl
```

- Keep auxiliary project files, expected output files, `ARGUMENTS.txt`, `gradle.properties`, and
  nested sample project build files when they are part of the behavior under test.
- Use a parameterized test with `@MethodSource` to discover snapshot directories from resources.
- Run each fixture with `withArgumentsFromTXT()` and `build()` so the fixture owns the task
  arguments.
- Assert the observable generated output, not just successful parsing or task execution.
- If expected output contains a runtime-dependent version, normalize it in the expected file before
  comparing.

Example adapted from `HubdleDeclarativeConfigDocumentationReadmeBadgesTest`:

```kotlin
@ParameterizedTest
@MethodSource("provideReadmeBadgeSnapshots")
fun `GIVEN readme badges snapshot WHEN writeReadmeBadges THEN readme is created`(
    snapshot: String
) {
    val sandboxPath = "hubdle-config-documentation-readme-badges/snapshots/$snapshot"
    gradleTestKitTest(sandboxPath = sandboxPath) {
        withArgumentsFromTXT()
        build()

        val expect: File = projectDir.resolve("README.expect.md")
        val actual: File = projectDir.resolve("README.md")

        val actualKotlinVersion =
            actual.readLines().first().substringAfter("kotlin-").substringBefore("-blueviolet")

        expect.apply {
            val updatedText = readText().replace("{VERSION}", actualKotlinVersion)
            writeText(updatedText)
        }
        actual.readText().shouldBe(expect.readText())
    }
}

companion object {

    @JvmStatic
    fun provideReadmeBadgeSnapshots(): Stream<String> {
        val snapshotsFile = resource("hubdle-config-documentation-readme-badges/snapshots")
        val snapshotsPath = Paths.get(snapshotsFile.toURI())
        return Files.list(snapshotsPath)
            .filter(Files::isDirectory)
            .map { it.fileName.toString() }
            .sorted()
    }
}
```

Test rules:

- Test at least one positive case.
- Test parent-disabled behavior for Hubdle nested features.
- Assert observable behavior, not only parsing.
- Use `LogFixture.lifecycle` for lifecycle log assertions.
- Keep fixtures small.
- When migrating an existing feature, first inspect and reuse/adapt its existing functional tests if
  they exist.
- Add source files only when the feature needs compilation, tests, publications, or plugin-specific
  inputs.
- Add extra tests for defaults, explicit `enabled = false`, plugin application, tasks, generated
  files, publications, or compilation only when the feature owns those semantics.

## DCL Rules

Follow these strictly:

- Use `@BindsProjectType` only for root `hubdle {}`.
- Use `@BindsProjectFeature` for nested Hubdle blocks.
- Do not nest another plugin's internal DCL `ProjectType` under Hubdle.
- Hubdle owns its DCL feature surface even when it applies external plugins.
- Reuse external plugins only through public Gradle APIs.
- Keep DCL state declarative and typed.
- Avoid Kotlin function types, `Any`, and wildcard-heavy Gradle API types in definitions.
- Prefer parent build models/providers over mutable global state or apply-order assumptions.
- Use provider wiring and task providers.
- Keep feature behavior idempotent.
- Keep public API as small as Gradle discovery allows.

## Common Failures

- `unresolved function call signature for 'hubdle'`: settings plugin did not register the root
  project type or Gradle is using the wrong DCL support.
- `Unexpected plugin type`: `com.javiersc.hubdle.declarative` points to `Plugin<Project>` instead of
  `Plugin<Settings>`.
- `does not expose a project feature`: registered class is missing `@BindsProjectType` or
  `@BindsProjectFeature`.
- `Cannot have abstract method ... isEnabled(): Property<Boolean>`: rename `isEnabled` to
  `enabled`.
- `property ... is final and cannot be changed`: apply action mutated a finalized DCL definition;
  consume it with `orElse(...)`.
