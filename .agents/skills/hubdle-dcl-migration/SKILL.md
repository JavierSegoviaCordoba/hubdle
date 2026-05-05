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

## Before Editing

Inspect the current feature or DSL block first:

1. Find the existing Kotlin DSL extension and plugin apply flow.
2. Identify what is declarative data and what is imperative behavior.
3. Keep imperative lambdas/actions out of DCL unless they can become typed declarative options.
4. Reuse existing Hubdle implementation logic through provider/value based configurers when
   possible.
5. Use `hubdle-declarative-config` as the concrete implementation reference.

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

## Core Rules

- Use `@BindsProjectType` only for root `hubdle {}`.
- Use `@BindsProjectFeature` for nested Hubdle blocks.
- Keep `Plugin<Project>.apply` as a no-op for feature bindings; behavior belongs in `ApplyAction`.
- Keep DCL definitions declarative, typed, and side-effect free.
- Do not expose imperative Gradle actions/lambdas directly in DCL definitions.
- Do not use deprecated DCL annotations.
- Add or update a `.gradle.dcl` functional fixture for every new DSL shape.
