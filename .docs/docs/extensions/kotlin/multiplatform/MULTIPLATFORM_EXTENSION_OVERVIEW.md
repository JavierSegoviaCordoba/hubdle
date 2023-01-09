# Multiplatform extension overview

- multiplatform
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - [features](features/FEATURES_EXTENSION_OVERVIEW.md)
    - common
    - android
    - apple
        - ios
            - iosArm32
            - iosArm64
            - iosSimulatorArm64
            - iosX64
        - macos
            - macosArm64
            - macosX64
        - tvos
            - tvosArm64
            - tvosSimulatorArm64
            - tvosX64
        - watchos
            - watchosArm32
            - watchosArm64
            - watchosSimulatorArm64
            - watchosX64
            - watchosX86
    - jvm
    - jvmAndAndroid
    - js
        - browser
        - nodejs
    - linux
    - mingw
    - native
    - wasm
    - `kotlin(action: Action<KotlinMultiplatformExtension>)`