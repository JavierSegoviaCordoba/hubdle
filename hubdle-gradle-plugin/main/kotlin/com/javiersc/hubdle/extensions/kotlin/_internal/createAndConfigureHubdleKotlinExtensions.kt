package com.javiersc.hubdle.extensions.kotlin._internal

import com.javiersc.hubdle.extensions._internal.HubdleState
import com.javiersc.hubdle.extensions.kotlin.HubdleKotlinExtension
import com.javiersc.hubdle.extensions.kotlin.android.HubdleKotlinAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.android.application.HubdleKotlinAndroidApplicationExtension
import com.javiersc.hubdle.extensions.kotlin.android.application.features.HubdleKotlinAndroidApplicationFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.android.features.HubdleKotlinAndroidBuildFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.android.features.HubdleKotlinAndroidFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.android.library.HubdleKotlinAndroidLibraryExtension
import com.javiersc.hubdle.extensions.kotlin.android.library.features.HubdleKotlinAndroidLibraryFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.features.HubdleKotlinFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinComposeFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinCoroutinesFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinExtendedStdlibFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinKotestFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinSerializationFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.HubdleKotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.features.HubdleKotlinJvmFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.features.HubdleKotlinMultiplatformFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.features.HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformAppleExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformCommonExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformIOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformJsExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformJvmAndAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformJvmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformLinuxExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformMacOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformMinGWExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformNativeExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformTvOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformWAsmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.HubdleKotlinMultiplatformWatchOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSArm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm32HfpExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMips32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMipsel32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.HubdleKotlinMultiplatformMacOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.HubdleKotlinMultiplatformMacOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw.HubdleKotlinMultiplatformMinGWX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw.HubdleKotlinMultiplatformMinGWX86Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.wasm.HubdleKotlinMultiplatformWAsm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSX86Extension
import com.javiersc.hubdle.extensions.shared.features.HubdleGradleFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.HubdleIntellijFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.HubdleJavaApplicationFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.HubdleJavaVersionFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.HubdleJvmToolchainFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.gradle.HubdleGradlePluginFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.gradle.HubdleGradleVersionCatalogFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.intellij.HubdleIntellijPluginFeatureExtension

internal fun HubdleState.createHubdleKotlinExtensions() {
    createExtension<HubdleKotlinExtension> {
        createKotlinFeatureExtensions()
        createKotlinAndroidExtensions()
        createKotlinJvmExtensions()
        createKotlinMultiplatformAndroidExtensions()
    }
}

private fun HubdleState.createKotlinFeatureExtensions() {
    createExtension<HubdleKotlinFeaturesExtension> {
        createExtension<HubdleJavaApplicationFeatureExtension>()
        createExtension<HubdleJavaVersionFeatureExtension>()
        createExtension<HubdleGradleFeatureExtension> {
            createExtension<HubdleGradlePluginFeatureExtension>()
            createExtension<HubdleGradleVersionCatalogFeatureExtension>()
        }
        createExtension<HubdleIntellijFeatureExtension> {
            createExtension<HubdleIntellijPluginFeatureExtension>()
        }
        createExtension<HubdleKotlinComposeFeatureExtension>()
        createExtension<HubdleKotlinCoroutinesFeatureExtension>()
        createExtension<HubdleKotlinExtendedStdlibFeatureExtension>()
        createExtension<HubdleKotlinKotestFeatureExtension>()
        createExtension<HubdleKotlinSerializationFeatureExtension>()
        createExtension<HubdleJvmToolchainFeatureExtension>()
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
        createExtension<HubdleKotlinMultiplatformAppleExtension> {
            createExtension<HubdleKotlinMultiplatformIOSExtension> {
                createExtension<HubdleKotlinMultiplatformIOSArm32Extension>()
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
                createExtension<HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension>()
                createExtension<HubdleKotlinMultiplatformWatchOSX64Extension>()
                createExtension<HubdleKotlinMultiplatformWatchOSX86Extension>()
            }
        }
        createExtension<HubdleKotlinMultiplatformCommonExtension>()
        createExtension<HubdleKotlinMultiplatformJsExtension>()
        createExtension<HubdleKotlinMultiplatformJvmAndAndroidExtension>()
        createExtension<HubdleKotlinMultiplatformJvmExtension>()
        createExtension<HubdleKotlinMultiplatformLinuxExtension> {
            createExtension<KotlinMultiplatformLinuxArm32HfpExtension>()
            createExtension<KotlinMultiplatformLinuxArm64Extension>()
            createExtension<KotlinMultiplatformLinuxMips32Extension>()
            createExtension<KotlinMultiplatformLinuxMipsel32Extension>()
            createExtension<KotlinMultiplatformLinuxX64Extension>()
        }
        createExtension<HubdleKotlinMultiplatformMinGWExtension> {
            createExtension<HubdleKotlinMultiplatformMinGWX64Extension>()
            createExtension<HubdleKotlinMultiplatformMinGWX86Extension>()
        }
        createExtension<HubdleKotlinMultiplatformNativeExtension>()
        createExtension<HubdleKotlinMultiplatformWAsmExtension> {
            createExtension<HubdleKotlinMultiplatformWAsm32Extension>()
        }
    }
}