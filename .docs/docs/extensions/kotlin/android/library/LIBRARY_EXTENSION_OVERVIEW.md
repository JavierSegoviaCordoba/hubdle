# Library extension overview

- library
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - [features](features/FEATURES_EXTENSION_OVERVIEW.md)
    - `configuration(name: String, action: Action<Configuration>)`
    - `sourceSet(name: String, action: Action<KotlinSourceSet>)`
    - `main(action: Action<KotlinSourceSet>)`
    - `test(action: Action<KotlinSourceSet>)`
    - `android(action: Action<LibraryExtension>)`
