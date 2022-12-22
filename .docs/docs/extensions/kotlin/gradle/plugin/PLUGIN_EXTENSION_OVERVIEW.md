# Plugin extension overview

- plugin
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - [features](features/FEATURES_EXTENSION_OVERVIEW.md)
    - `tags: SetProperty<String>` // empty set
    - `tags(vararg tags: String)`
    - `kotlin(action: Action<KotlinJvmProjectExtension>`
    - `gradlePlugin(action: Action<GradlePluginDevelopmentExtension>)`
    - `main(action: Action<KotlinSourceSet>)`
    - `functionalTest(action: Action<KotlinSourceSet>)`
    - `integrationTest(action: Action<KotlinSourceSet>)`
    - `test(action: Action<KotlinSourceSet>)`
    - `testFixtures(action: Action<KotlinSourceSet>)`
    - `pluginUnderTestDependencies: ListProperty<MinimalExternalModuleDependency>` // empty list
    - `pluginUnderTestDependencies(vararg pluginUnderTestDependencies: MinimalExternalModuleDependency)`
