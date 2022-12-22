# IntelliJ extension overview

- intellij
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - [features](features/FEATURES_EXTENSION_OVERVIEW.md)
    - `kotlin(action: Action<KotlinJvmProjectExtension>)`
    - `intellij(action: Action<IntelliJPluginExtension>)`
    - `patchPluginXml(action: Action<PatchPluginXmlTask>)`
    - `publishPlugin(action: Action<PublishPluginTask>)`
    - `signPlugin(action: Action<SignPluginTask>)`
    - `main(action: Action<KotlinSourceSet>)`
    - `test(action: Action<KotlinSourceSet>)`
