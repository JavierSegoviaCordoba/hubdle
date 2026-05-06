---
name: hubdle-dcl-migration
description: Migrate Hubdle or Hubdle-like Gradle plugin DSLs from normal imperative Gradle extensions to Gradle Declarative DCL. Use when converting HubdleExtension, HubdleConfigExtension, Kotlin/Java/Gradle/config/versioning DSL blocks, enableable extensions, plugin aggregation, or nested Gradle DSL actions into DCL project types, project features, definitions, build models, and settings registration.
---

# Hubdle DCL Migration

## Goal

Create Hubdle Gradle Declarative features without losing the separation between declarative model
state and imperative Gradle behavior.

Primary reference implementation:

```text
hubdle-declarative-features/hubdle-declarative-config
```

Current shape:

- `hubdle-declarative` owns the settings plugin and root `hubdle {}` project type.
- `hubdle-declarative-features` owns feature modules.
- `hubdle {}` is the root project type.
- Nested blocks such as `hubdle { config {} }` are project features.

Module boundaries:

- Default to one Gradle module per project feature, including intermediate namespaces that can
  contain multiple feature types.
- Create a module for a namespace feature and separate modules for each non-terminal child feature.
  For example, `versioning` is its own module because it can later contain `semver`, `calver`, and
  other strategies, and `semver` must also live in its own module.
- Apply the same rule to technology families: `kotlin` is a module, and concrete Kotlin feature
  types such as `jvm`, `multiplatform`, and `android` are separate modules.
- Nested blocks inside a terminal concrete feature may live in that concrete feature module unless
  they represent shared behavior reused by several features.
- Shared behavior from the legacy DSL should be extracted to its own declarative feature module
  when it is reused across feature families.
- When adapting Kotlin root, keep shared Kotlin features under the Kotlin owner
  (`hubdle { kotlin { features { ... } } }`) and let concrete targets such as `jvm`,
  `multiplatform`, and `android` contribute target-specific source sets, dependencies, test tasks,
  publishing inputs, and plugin application separately. Do not duplicate Kotlin operational blocks
  such as shared `features`, `analysis`, `format`, `coverage`, `documentation`, or generic
  `testing` under each concrete target.

## Before Editing

Inspect the current feature or DSL block first:

1. Find the existing Kotlin DSL extension and plugin apply flow.
2. Identify what is declarative data and what is imperative behavior.
3. Keep imperative lambdas/actions out of DCL unless they can become typed declarative options.
4. Reuse existing Hubdle implementation logic through provider/value based configurers when
   possible.
5. Use `hubdle-declarative-config` as the concrete implementation reference.
6. Map every new DCL property, nested block, and `@Adding` function to an existing legacy DSL
   property/function or to a specific issue requirement before implementing it. Do not invent
   generic model words such as `capability`, `classifier`, `participant`, `artifact`, or `fact`
   unless they already exist in the legacy DSL or the issue explicitly asks for that exact model.
7. Reuse platform-owned constants for plugin IDs and Gradle property names before adding literals
   to feature apply actions. Add missing shared constants to `platform` instead of keeping local
   `private const val` copies.
8. Insert new constants, enum entries, dependencies, registrations, and similar lists in
   alphabetical order unless the surrounding file uses a different explicit ordering.

Platform naming:

- Add plugin IDs to `platform/main/kotlin/hubdle/platform/PluginIds.kt`.
- Name plugin enum entries from the complete plugin ID after dropping only the top-level domain
  prefix such as `com.`, `org.`, `io.`, `dev.`, or `app.`.
- Preserve the remaining namespace so provider/project identity is not lost. Examples:
  `com.javiersc.semver` becomes `JavierscSemver`, and `org.sonarqube` becomes `Sonarqube`.
- Add Gradle property names to `platform/main/kotlin/hubdle/platform/HubdleProperties.kt`.
- Group properties by their domain namespace as nested objects, named in PascalCase. For example,
  `semver.tagPrefix` belongs in `HubdleProperties.Semver.TagPrefix`.
- Property constant names are the remaining property path segments after the domain object, also in
  PascalCase. For example, `hubdle.logging.enabled` is
  `HubdleProperties.Logging.Enabled`.
- Keep both plugin enum entries and property domain objects/properties alphabetically sorted unless
  the file already has a clearer explicit order.

## Reference Map

Read only the reference needed for the current task:

- New module layout, Gradle build file, source directories, core file names, and registration:
  [`references/feature-scaffold.md`](references/feature-scaffold.md).
- `Feature`, `Definition`, `BuildModel`, and `ApplyAction` implementation patterns:
  [`references/feature-implementation.md`](references/feature-implementation.md).
- Factory functions and DCL annotations with DSL and Kotlin examples:
  [`references/dcl-annotations.md`](references/dcl-annotations.md).
- Functional test fixtures, disabled behavior tests, and snapshot migration:
  [`references/testing.md`](references/testing.md).
- DCL rules and common failure diagnosis:
  [`references/troubleshooting.md`](references/troubleshooting.md).

## Default Workflow

For a new or migrated Hubdle DCL feature:

1. Read [`references/feature-scaffold.md`](references/feature-scaffold.md) and create/wire the
   module.
2. Read [`references/feature-implementation.md`](references/feature-implementation.md) and implement
   the four core files.
3. If the definition needs factories, standalone functions, containers, hidden services, defaults,
   or receiver constraints, read [`references/dcl-annotations.md`](references/dcl-annotations.md).
4. Read [`references/testing.md`](references/testing.md) and add focused functional fixtures.
5. If Gradle DCL schema/evaluation fails, read
   [`references/troubleshooting.md`](references/troubleshooting.md).

Before compiling or running tests, always run:

```shell
./gradlew fixChecks
```

This keeps formatting and API dumps current before verification.

## Core Rules

- Use `@BindsProjectType` only for root `hubdle {}`.
- Use `@BindsProjectFeature` for nested Hubdle blocks.
- Keep `Plugin<Project>.apply` as a no-op for feature bindings; behavior belongs in `ApplyAction`.
- Keep DCL definitions declarative, typed, and side-effect free.
- Do not expose imperative Gradle actions/lambdas directly in DCL definitions.
- Do not use deprecated DCL annotations.
- Add or update a `.gradle.dcl` functional fixture for every new DSL shape.
- Every functional test fixture directory must include a `gradle.properties` file with:
  `org.gradle.caching=true`, `org.gradle.configuration-cache=true`,
  `org.gradle.parallel=true`, and `org.gradle.unsafe.isolated-projects=true`.
- Do not edit `settings.gradle.kts` unless the user explicitly asks for it.
- In functional test `settings.gradle.dcl` fixtures, always set `rootProject.name` following the
  repository's fixture naming pattern.
- Keep each issue in a separate commit, but never create commits without explicit user permission in
  the current turn. If the user asks to delay committing until review, wait for their confirmation
  before creating the issue commit.
