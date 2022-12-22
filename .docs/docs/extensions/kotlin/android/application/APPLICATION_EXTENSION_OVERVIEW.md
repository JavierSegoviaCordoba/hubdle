# Application extension overview

- application
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - [features](features/FEATURES_EXTENSION_OVERVIEW.md)
    - `applicationId: Property<String?>` // `namespace`
    - `versionCode: Property<Int>` // 1
    - `versionName: Property<String>` // 0.1.0
    - `configuration(name: String, action: Action<Configuration>)`
    - `sourceSet(name: String, action: Action<KotlinSourceSet>)`
    - `main(action: Action<KotlinSourceSet>)`
    - `test(action: Action<KotlinSourceSet>)`
    - `android(action: Action<ApplicationExtension>)`
