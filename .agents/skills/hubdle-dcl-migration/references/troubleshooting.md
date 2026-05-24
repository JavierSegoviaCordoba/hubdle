# Rules And Troubleshooting

Use this reference when checking a DCL migration for correctness or diagnosing common failures.

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
- `Could not find com.javiersc.hubdle:hubdle-ecosystem-api` or a Hubdle feature artifact while
  resolving a Semver plugin classpath: the snapshots repository content filter is missing
  `includeGroupByRegex("com\\.javiersc\\.hubdle.*")`.
- `Could not find com.javiersc.hubdle.ecosystem:com.javiersc.hubdle.ecosystem.gradle.plugin`
  through Semver: the published Semver snapshot still depends on the Hubdle ecosystem plugin marker.
  Publish a Semver snapshot where the Hubdle integration depends on `hubdle-ecosystem-api` and the
  required feature modules instead.
- `:hubdle-ecosystem:pluginUnderTestMetadata` failures involving Semver usually come from the
  plugin-under-test classpath, not from Hubdle main compilation. Inspect `testPluginClasspath` and
  snapshot timestamps before changing production dependencies.
