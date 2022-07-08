@file:Suppress("SpreadOperator")

package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogImplementation
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureEmptyJavadocs
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun configureMultiplatform(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()
        project.the<KotlinMultiplatformExtension>().configureMultiplatformDependencies()

        if (project.hubdleState.config.publishing.isEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.configurePublishingExtension()
            project.configureSigningForPublishing()
            project.configureEmptyJavadocs()
        }
    }
}

internal fun configureMultiplatformAndroid(project: Project) {
    val androidState = project.hubdleState.kotlin.multiplatform.android

    if (androidState.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.library)
        project.configure<LibraryExtension> {
            compileSdk = project.hubdleState.kotlin.android.compileSdk
            defaultConfig.minSdk = project.hubdleState.kotlin.android.minSdk

            sourceSets.all { manifest.srcFile("android${name.capitalized()}/AndroidManifest.xml") }
        }

        project.configure<KotlinMultiplatformExtension> {
            android {
                if (project.hubdleState.config.publishing.isEnabled) {
                    when {
                        androidState.publishLibraryVariants.isNotEmpty() -> {
                            val variants = androidState.publishLibraryVariants.toTypedArray()
                            publishLibraryVariants(*variants)
                        }
                        androidState.allLibraryVariants -> publishAllLibraryVariants()
                    }
                }
            }
        }
    }
}

internal fun configureMultiplatformIOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.ios.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting
            val iosArm32Main: KotlinSourceSet? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KotlinSourceSet? = sourceSets.findByName("iosArm64Main")
            val iosX64Main: KotlinSourceSet? = sourceSets.findByName("iosX64Main")
            val iosSimulatorArm64Main: KotlinSourceSet? =
                sourceSets.findByName("iosSimulatorArm64Main")

            val iosSourceSets: List<KotlinSourceSet> =
                listOfNotNull(iosArm32Main, iosArm64Main, iosX64Main, iosSimulatorArm64Main)

            val iosMain = sourceSets.maybeCreate("iosMain")
            val iosTest = sourceSets.maybeCreate("iosTest")

            iosMain.dependsOn(commonMain)
            for (iosSourceSet in iosSourceSets) {
                iosSourceSet.dependsOn(iosMain)
            }

            iosTest.dependsOn(iosMain)
        }
    }
}

internal fun configureMultiplatformIOSArm32(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.iosArm32.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { iosArm32() }
    }
}

internal fun configureMultiplatformIOSArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.iosArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { iosArm64() }
    }
}

internal fun configureMultiplatformIOSX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.iosX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { iosX64() }
    }
}

internal fun configureMultiplatformIOSSimulatorArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.iosSimulatorArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { iosSimulatorArm64() }
    }
}

internal fun configureMultiplatformJvm(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.jvm.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { jvm() }
    }
}

internal fun configureMultiplatformJs(project: Project) {
    val jsState = project.hubdleState.kotlin.multiplatform.js
    if (jsState.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            js(BOTH) {
                if (jsState.browser) browser()
                if (jsState.nodeJs) nodejs()
            }
        }
    }
}

internal fun configureMultiplatformLinux(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linux.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting
            val linuxArm64Main: KotlinSourceSet? = sourceSets.findByName("linuxArm64Main")
            val linuxArm32HfpMain: KotlinSourceSet? = sourceSets.findByName("linuxArm32HfpMain")
            val linuxMips32Main: KotlinSourceSet? = sourceSets.findByName("linuxMips32Main")
            val linuxMipsel32Main: KotlinSourceSet? = sourceSets.findByName("linuxMipsel32Main")
            val linuxX64Main: KotlinSourceSet? = sourceSets.findByName("linuxX64Main")

            val linuxSourceSets: List<KotlinSourceSet> =
                listOfNotNull(
                    linuxArm64Main,
                    linuxArm32HfpMain,
                    linuxMips32Main,
                    linuxMipsel32Main,
                    linuxX64Main,
                )

            val linuxMain = sourceSets.maybeCreate("linuxMain")
            val linuxTest = sourceSets.maybeCreate("linuxTest")

            linuxMain.dependsOn(commonMain)
            for (linuxSourceSet in linuxSourceSets) {
                linuxSourceSet.dependsOn(linuxMain)
            }

            linuxTest.dependsOn(linuxMain)
        }
    }
}

internal fun configureMultiplatformLinuxArm32Hfp(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linuxArm32Hfp.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { linuxArm32Hfp() }
    }
}

internal fun configureMultiplatformLinuxArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linuxArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { linuxArm64() }
    }
}

internal fun configureMultiplatformLinuxMips32(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linuxMips32.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { linuxMips32() }
    }
}

internal fun configureMultiplatformLinuxMipsel32(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linuxMipsel32.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { linuxMipsel32() }
    }
}

internal fun configureMultiplatformLinuxX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linuxX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { linuxX64() }
    }
}

internal fun configureMultiplatformNative(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.native.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting

            val iosMain: KotlinSourceSet? = sourceSets.findByName("iosMain")
            val iosArm32Main: KotlinSourceSet? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KotlinSourceSet? = sourceSets.findByName("iosArm64Main")
            val iosSimulatorArm64Main: KotlinSourceSet? =
                sourceSets.findByName("iosSimulatorArm64Main")
            val iosX64Main: KotlinSourceSet? = sourceSets.findByName("iosX64Main")
            val linuxMain: KotlinSourceSet? = sourceSets.findByName("linuxMain")
            val linuxArm32HfpMain: KotlinSourceSet? = sourceSets.findByName("linuxArm32HfpMain")
            val linuxArm64Main: KotlinSourceSet? = sourceSets.findByName("linuxArm64Main")
            val linuxMips32Main: KotlinSourceSet? = sourceSets.findByName("linuxMips32Main")
            val linuxMipsel32Main: KotlinSourceSet? = sourceSets.findByName("linuxMipsel32Main")
            val linuxX64Main: KotlinSourceSet? = sourceSets.findByName("linuxX64Main")
            val macosMain: KotlinSourceSet? = sourceSets.findByName("macosMain")
            val macosArm64Main: KotlinSourceSet? = sourceSets.findByName("macosArm64Main")
            val macosX64Main: KotlinSourceSet? = sourceSets.findByName("macosX64Main")
            val mingwMain: KotlinSourceSet? = sourceSets.findByName("mingwMain")
            val mingwX64Main: KotlinSourceSet? = sourceSets.findByName("mingwX64Main")
            val mingwX86Main: KotlinSourceSet? = sourceSets.findByName("mingwX86Main")
            val tvosMain: KotlinSourceSet? = sourceSets.findByName("tvosMain")
            val tvosArm64Main: KotlinSourceSet? = sourceSets.findByName("tvosArm64Main")
            val tvosSimulatorArm64Main: KotlinSourceSet? =
                sourceSets.findByName("tvosSimulatorArm64Main")
            val tvosX64Main: KotlinSourceSet? = sourceSets.findByName("tvosX64Main")
            val wasmMain: KotlinSourceSet? = sourceSets.findByName("wasmMain")
            val wasm32Main: KotlinSourceSet? = sourceSets.findByName("wasm32Main")
            val watchosMain: KotlinSourceSet? = sourceSets.findByName("watchosMain")
            val watchosArm32Main: KotlinSourceSet? = sourceSets.findByName("watchosArm32Main")
            val watchosArm64Main: KotlinSourceSet? = sourceSets.findByName("watchosArm64Main")
            val watchosSimulatorArm64Main: KotlinSourceSet? =
                sourceSets.findByName("watchosSimulatorArm64Main")
            val watchosX64Main: KotlinSourceSet? = sourceSets.findByName("watchosX64Main")
            val watchosX86Main: KotlinSourceSet? = sourceSets.findByName("watchosX86Main")

            val nativeSourceSets: List<KotlinSourceSet> =
                listOfNotNull(
                    iosMain,
                    iosArm32Main,
                    iosArm64Main,
                    iosSimulatorArm64Main,
                    iosX64Main,
                    linuxMain,
                    linuxArm32HfpMain,
                    linuxArm64Main,
                    linuxMips32Main,
                    linuxMipsel32Main,
                    linuxX64Main,
                    macosMain,
                    macosArm64Main,
                    macosX64Main,
                    mingwMain,
                    mingwX64Main,
                    mingwX86Main,
                    tvosMain,
                    tvosArm64Main,
                    tvosSimulatorArm64Main,
                    tvosX64Main,
                    wasmMain,
                    wasm32Main,
                    watchosMain,
                    watchosArm32Main,
                    watchosArm64Main,
                    watchosSimulatorArm64Main,
                    watchosX64Main,
                    watchosX86Main,
                )

            val nativeMain = sourceSets.maybeCreate("nativeMain")
            val nativeTest = sourceSets.maybeCreate("nativeTest")

            nativeMain.dependsOn(commonMain)
            for (nativeSourceSet in nativeSourceSets) {
                nativeSourceSet.dependsOn(nativeMain)
            }

            nativeTest.dependsOn(nativeMain)
        }
    }
}

internal fun configureMultiplatformMacOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.macos.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting
            val macosX64Main: KotlinSourceSet? = sourceSets.findByName("macosX64Main")
            val macosArm64Main: KotlinSourceSet? = sourceSets.findByName("macosArm64Main")

            val macosSourceSets: List<KotlinSourceSet> = listOfNotNull(macosX64Main, macosArm64Main)

            val macosMain = sourceSets.maybeCreate("macosMain")
            val macosTest = sourceSets.maybeCreate("macosTest")

            macosMain.dependsOn(commonMain)
            for (macosSourceSet in macosSourceSets) {
                macosMain.dependsOn(macosSourceSet)
            }

            macosTest.dependsOn(macosMain)
        }
    }
}

internal fun configureMultiplatformMacOSArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.macosArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { macosArm64() }
    }
}

internal fun configureMultiplatformMacOSX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.macosX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { macosX64() }
    }
}

internal fun configureMultiplatformMinGW(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.mingw.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting
            val mingwX64Main: KotlinSourceSet? = sourceSets.findByName("mingwX64Main")
            val mingwX86Main: KotlinSourceSet? = sourceSets.findByName("mingwX86Main")

            val mingwSourceSets: List<KotlinSourceSet> = listOfNotNull(mingwX64Main, mingwX86Main)

            val mingwMain = sourceSets.maybeCreate("mingwMain")
            val mingwTest = sourceSets.maybeCreate("mingwTest")

            mingwMain.dependsOn(commonMain)
            for (mingwSourceSet in mingwSourceSets) {
                mingwSourceSet.dependsOn(mingwMain)
            }

            mingwTest.dependsOn(mingwMain)
        }
    }
}

internal fun configureMultiplatformMinGWX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.mingwX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { mingwX64() }
    }
}

internal fun configureMultiplatformMinGWX86(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.mingwX86.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { mingwX86() }
    }
}

internal fun configureMultiplatformTvOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.tvos.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting

            val tvosArm64Main: KotlinSourceSet? = sourceSets.findByName("tvosArm64Main")
            val tvosX64Main: KotlinSourceSet? = sourceSets.findByName("tvosX64Main")
            val tvosSimulatorArm64Main: KotlinSourceSet? =
                sourceSets.findByName("tvosSimulatorArm64Main")

            val tvosSourceSets: List<KotlinSourceSet> =
                listOfNotNull(tvosArm64Main, tvosX64Main, tvosSimulatorArm64Main)

            val tvosMain = sourceSets.maybeCreate("tvosMain")
            val tvosTest = sourceSets.maybeCreate("tvosTest")

            tvosMain.dependsOn(commonMain)
            for (tvosSourceSet in tvosSourceSets) {
                tvosSourceSet.dependsOn(tvosMain)
            }

            tvosTest.dependsOn(tvosMain)
        }
    }
}

internal fun configureMultiplatformTvOSArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.tvosArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { tvosArm64() }
    }
}

internal fun configureMultiplatformTvOSSimulatorArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.tvosArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { tvosSimulatorArm64() }
    }
}

internal fun configureMultiplatformTvOSX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.tvosX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { tvosX64() }
    }
}

internal fun configureMultiplatformWAsm(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.wasm.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting

            val wasm32Main: KotlinSourceSet? = sourceSets.findByName("wasm32Main")

            val wasmSourceSets: List<KotlinSourceSet> = listOfNotNull(wasm32Main)

            val wasmMain = sourceSets.maybeCreate("wasmMain")
            val wasmTest = sourceSets.maybeCreate("wasmTest")

            wasmMain.dependsOn(commonMain)
            for (wasmSourceSet in wasmSourceSets) {
                wasmSourceSet.dependsOn(wasmMain)
            }

            wasmTest.dependsOn(wasmMain)
        }
    }
}

internal fun configureMultiplatformWAsm32(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.wasm32.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { wasm32() }
    }
}

internal fun configureMultiplatformWatchOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchos.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KotlinSourceSet by sourceSets.getting

            val watchosArm32Main: KotlinSourceSet? = sourceSets.findByName("watchosArm32Main")
            val watchosArm64Main: KotlinSourceSet? = sourceSets.findByName("watchosArm64Main")
            val watchosX64Main: KotlinSourceSet? = sourceSets.findByName("watchosX64Main")
            val watchosSimulatorArm64Main: KotlinSourceSet? =
                sourceSets.findByName("watchosSimulatorArm64Main")
            val watchosX86Main: KotlinSourceSet? = sourceSets.findByName("watchosX86Main")

            val watchosSourceSets: List<KotlinSourceSet> =
                listOfNotNull(
                    watchosArm32Main,
                    watchosArm64Main,
                    watchosX64Main,
                    watchosSimulatorArm64Main,
                    watchosX86Main,
                )

            val watchosMain = sourceSets.maybeCreate("watchosMain")
            val watchosTest = sourceSets.maybeCreate("watchosTest")

            watchosMain.dependsOn(commonMain)
            for (watchosSourceSet in watchosSourceSets) {
                watchosSourceSet.dependsOn(watchosMain)
            }

            watchosTest.dependsOn(watchosMain)
        }
    }
}

internal fun configureMultiplatformWatchOSArm32(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchosArm32.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { watchosArm32() }
    }
}

internal fun configureMultiplatformWatchOSArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchosArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { watchosArm64() }
    }
}

internal fun configureMultiplatformWatchOSSimulatorArm64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchosSimulatorArm64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { watchosSimulatorArm64() }
    }
}

internal fun configureMultiplatformWatchOSX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchosX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { watchosX64() }
    }
}

internal fun configureMultiplatformWatchOSX86(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchosX86.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { watchosX86() }
    }
}

internal fun configureMultiplatformRawConfig(project: Project) {
    project.hubdleState.kotlin.multiplatform.rawConfig.kotlin?.execute(project.the())
}

internal fun configureMultiplatformAndroidRawConfig(project: Project) {
    project.hubdleState.kotlin.multiplatform.android.rawConfig.android?.execute(project.the())
}

private fun KotlinMultiplatformExtension.configureMultiplatformDependencies() {
    sourceSets.getByName("commonMain") { dependencies { configureCommonMainDependencies() } }
    sourceSets.getByName("commonTest") { dependencies { configureCommonTestDependencies() } }
    sourceSets.findByName("androidMain")?.apply {
        dependencies { configureAndroidMainDependencies() }
    }
}

internal val Project.multiplatformFeatures: HubdleState.Kotlin.Multiplatform.Features
    get() = hubdleState.kotlin.multiplatform.features

private val KotlinDependencyHandler.multiplatformFeatures: HubdleState.Kotlin.Multiplatform.Features
    get() = project.multiplatformFeatures

private fun KotlinDependencyHandler.configureCommonMainDependencies() {
    if (multiplatformFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE)
    }
    if (multiplatformFeatures.extendedStdlib) {
        catalogImplementation(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE)
    }

    if (multiplatformFeatures.serialization.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.serialization)
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE)
        if (multiplatformFeatures.serialization.useJson) {
            catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE)
        }
    }
}

private fun KotlinDependencyHandler.configureCommonTestDependencies() {
    if (multiplatformFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE)
    }
    catalogImplementation(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE)
    if (multiplatformFeatures.extendedTesting) {
        catalogImplementation(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE)
    }
}

private fun KotlinDependencyHandler.configureAndroidMainDependencies() {
    if (multiplatformFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE)
    }
}
