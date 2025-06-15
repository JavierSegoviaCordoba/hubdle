package com.javiersc.hubdle.project.extensions.kotlin._internal

import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions.kotlin.HubdleKotlinExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.HubdleKotlinAndroidExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.application.HubdleKotlinAndroidApplicationExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.application.features.HubdleKotlinAndroidApplicationFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.features.HubdleKotlinAndroidBuildFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.features.HubdleKotlinAndroidFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.library.HubdleKotlinAndroidLibraryExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.library.features.HubdleKotlinAndroidLibraryFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.compiler.options.HubdleKotlinCompilerOptionsExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.HubdleKotlinFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinAtomicfuFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinBuildKonfigFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinComposeFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinCoroutinesFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinExtendedStdlibFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinKopyFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinKotestFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinMoleculeFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinPowerAssertFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinSerializationFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinSqlDelightFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.HubdleKotlinJvmExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.HubdleKotlinCompilerPluginFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.HubdleKotlinJvmFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.features.HubdleKotlinMultiplatformFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.features.HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformAndroidExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformAndroidNativeExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformAppleExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformCommonExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformIOSExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformJsExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformJvmAndAndroidExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformJvmExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformLinuxExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformMacOSExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformMinGWExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformNativeExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformTvOSExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformWAsmExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformWatchOSExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeArm32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeX86Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.macos.HubdleKotlinMultiplatformMacOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.macos.HubdleKotlinMultiplatformMacOSX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.mingw.HubdleKotlinMultiplatformMinGWX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.wasm.HubdleKotlinMultiplatformWAsmJsExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.wasm.HubdleKotlinMultiplatformWAsmWAsiExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSDeviceArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSX64Extension

internal fun HubdleState.createHubdleKotlinExtensions() {
    createExtension<HubdleKotlinExtension> {
        createKotlinFeatureExtensions()
        createKotlinAndroidExtensions()
        createExtension<HubdleKotlinCompilerOptionsExtension>()
        createKotlinJvmExtensions()
        createKotlinMultiplatformAndroidExtensions()
    }
}

private fun HubdleState.createKotlinFeatureExtensions() {
    createExtension<HubdleKotlinFeaturesExtension> {
        createExtension<HubdleKotlinCompilerPluginFeatureExtension>()
        createExtension<HubdleKotlinAtomicfuFeatureExtension>()
        createExtension<HubdleKotlinBuildKonfigFeatureExtension>()
        createExtension<HubdleKotlinComposeFeatureExtension>()
        createExtension<HubdleKotlinCoroutinesFeatureExtension>()
        createExtension<HubdleKotlinExtendedStdlibFeatureExtension>()
        createExtension<HubdleKotlinKopyFeatureExtension>()
        createExtension<HubdleKotlinKotestFeatureExtension>()
        createExtension<HubdleKotlinMoleculeFeatureExtension>()
        createExtension<HubdleKotlinPowerAssertFeatureExtension>()
        createExtension<HubdleKotlinSerializationFeatureExtension>()
        createExtension<HubdleKotlinSqlDelightFeatureExtension>()
    }
}

private fun HubdleState.createKotlinAndroidExtensions() {
    createExtension<HubdleKotlinAndroidExtension> {
        createExtension<HubdleKotlinAndroidFeaturesExtension>()
        createExtension<HubdleKotlinAndroidApplicationExtension> {
            createExtension<HubdleKotlinAndroidApplicationFeaturesExtension>()
        }
        createExtension<HubdleKotlinAndroidLibraryExtension> {
            createExtension<HubdleKotlinAndroidLibraryFeaturesExtension>()
        }
        createExtension<HubdleKotlinAndroidBuildFeaturesExtension>()
    }
}

private fun HubdleState.createKotlinJvmExtensions() {
    createExtension<HubdleKotlinJvmExtension> {
        createExtension<HubdleKotlinJvmFeaturesExtension>()
    }
}

private fun HubdleState.createKotlinMultiplatformAndroidExtensions() {
    createExtension<HubdleKotlinMultiplatformExtension> {
        createExtension<HubdleKotlinMultiplatformFeaturesExtension>()
        createExtension<HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension>()
        createExtension<HubdleKotlinMultiplatformAndroidExtension>()
        createExtension<HubdleKotlinMultiplatformAndroidNativeExtension> {
            createExtension<HubdleKotlinMultiplatformAndroidNativeArm32Extension>()
            createExtension<HubdleKotlinMultiplatformAndroidNativeArm64Extension>()
            createExtension<HubdleKotlinMultiplatformAndroidNativeX64Extension>()
            createExtension<HubdleKotlinMultiplatformAndroidNativeX86Extension>()
        }
        createExtension<HubdleKotlinMultiplatformAppleExtension> {
            createExtension<HubdleKotlinMultiplatformIOSExtension> {
                createExtension<HubdleKotlinMultiplatformIOSArm64Extension>()
                createExtension<HubdleKotlinMultiplatformIOSSimulatorArm64Extension>()
                createExtension<HubdleKotlinMultiplatformIOSX64Extension>()
            }
            createExtension<HubdleKotlinMultiplatformMacOSExtension> {
                createExtension<HubdleKotlinMultiplatformMacOSArm64Extension>()
                createExtension<HubdleKotlinMultiplatformMacOSX64Extension>()
            }
            createExtension<HubdleKotlinMultiplatformTvOSExtension> {
                createExtension<HubdleKotlinMultiplatformTvOSArm64Extension>()
                createExtension<HubdleKotlinMultiplatformTvOSSimulatorArm64Extension>()
                createExtension<HubdleKotlinMultiplatformTvOSX64Extension>()
            }
            createExtension<HubdleKotlinMultiplatformWatchOSExtension> {
                createExtension<HubdleKotlinMultiplatformWatchOSArm32Extension>()
                createExtension<HubdleKotlinMultiplatformWatchOSArm64Extension>()
                createExtension<HubdleKotlinMultiplatformWatchOSDeviceArm64Extension>()
                createExtension<HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension>()
                createExtension<HubdleKotlinMultiplatformWatchOSX64Extension>()
            }
        }
        createExtension<HubdleKotlinMultiplatformCommonExtension>()
        createExtension<HubdleKotlinMultiplatformJsExtension>()
        createExtension<HubdleKotlinMultiplatformJvmAndAndroidExtension>()
        createExtension<HubdleKotlinMultiplatformJvmExtension>()
        createExtension<HubdleKotlinMultiplatformLinuxExtension> {
            createExtension<KotlinMultiplatformLinuxArm64Extension>()
            createExtension<KotlinMultiplatformLinuxX64Extension>()
        }
        createExtension<HubdleKotlinMultiplatformMinGWExtension> {
            createExtension<HubdleKotlinMultiplatformMinGWX64Extension>()
        }
        createExtension<HubdleKotlinMultiplatformNativeExtension>()
        createExtension<HubdleKotlinMultiplatformWAsmExtension> {
            createExtension<HubdleKotlinMultiplatformWAsmJsExtension>()
            createExtension<HubdleKotlinMultiplatformWAsmWAsiExtension>()
        }
    }
}
