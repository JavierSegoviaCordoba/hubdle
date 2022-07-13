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
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

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
        println("HELLO")
        project.configure<LibraryExtension> {
            compileSdk = project.hubdleState.kotlin.android.compileSdk
            defaultConfig.minSdk = project.hubdleState.kotlin.android.minSdk

            sourceSets.all { manifest.srcFile("android/$name/AndroidManifest.xml") }
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
            val commonMain: KSS by sourceSets.getting
            val iosArm32Main: KSS? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KSS? = sourceSets.findByName("iosArm64Main")
            val iosX64Main: KSS? = sourceSets.findByName("iosX64Main")
            val iosSimulatorArm64Main: KSS? = sourceSets.findByName("iosSimulatorArm64Main")

            val commonTest: KSS by sourceSets.getting
            val iosArm32Test: KSS? = sourceSets.findByName("iosArm32Test")
            val iosArm64Test: KSS? = sourceSets.findByName("iosArm64Test")
            val iosX64Test: KSS? = sourceSets.findByName("iosX64Test")
            val iosSimulatorArm64Test: KSS? = sourceSets.findByName("iosSimulatorArm64Test")

            val iosMainSourceSets: List<KSS> =
                listOfNotNull(iosArm32Main, iosArm64Main, iosX64Main, iosSimulatorArm64Main)

            val iosTestSourceSets: List<KSS> =
                listOfNotNull(iosArm32Test, iosArm64Test, iosX64Test, iosSimulatorArm64Test)

            val iosMain = sourceSets.maybeCreate("iosMain")
            val iosTest = sourceSets.maybeCreate("iosTest")

            iosMain.dependsOn(commonMain)
            for (iosMainSourceSet in iosMainSourceSets) {
                iosMainSourceSet.dependsOn(iosMain)
            }
            iosTest.dependsOn(commonTest)
            for (iosTestSourceSet in iosTestSourceSets) {
                iosTestSourceSet.dependsOn(iosTest)
            }
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

internal fun configureMultiplatformJvmAndAndroid(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.jvmAndAndroid.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            /* TODO: enable when granular metadata for jvm+android is fixed
            val jvmAndAndroidMain = sourceSets.create("jvmAndAndroidMain")
            val jvmAndAndroidTest = sourceSets.create("jvmAndAndroidTest")

            jvmAndAndroidMain.dependsOn(sourceSets.getByName("commonMain"))
            jvmAndAndroidTest.dependsOn(sourceSets.getByName("commonTest"))

            sourceSets.findByName("jvmMain")?.dependsOn(jvmAndAndroidMain)
            sourceSets.findByName("jvmTest")?.dependsOn(jvmAndAndroidTest)

            sourceSets.findByName("androidMain")?.dependsOn(jvmAndAndroidMain)
            sourceSets.findByName("androidTest")?.dependsOn(jvmAndAndroidTest)
            */

            // TODO: remove when granular metadata for jvm+android is fixed
            val mainKotlin = "jvmAndAndroidMain/kotlin"
            val mainResources = "jvmAndAndroidMain/resources"
            val testKotlin = "jvmAndAndroidTest/kotlin"
            val testResources = "jvmAndAndroidTest/resources"

            sourceSets.findByName("androidMain")?.apply {
                kotlin.srcDirs(mainKotlin)
                resources.srcDirs(mainResources)
            }
            sourceSets.findByName("androidTest")?.apply {
                kotlin.srcDirs(testKotlin)
                resources.srcDirs(testResources)
            }
            sourceSets.findByName("jvmMain")?.apply {
                kotlin.srcDirs(mainKotlin)
                resources.srcDirs(mainResources)
            }
            sourceSets.findByName("jvmTest")?.apply {
                kotlin.srcDirs(testKotlin)
                resources.srcDirs(testResources)
            }
        }
    }
}

internal fun configureMultiplatformJs(project: Project) {
    val jsState = project.hubdleState.kotlin.multiplatform.js
    if (jsState.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            js(BOTH) {
                if (jsState.browser.isEnabled) {
                    browser { jsState.browser.action?.execute(this) }
                }
                if (jsState.nodejs.isEnabled) {
                    nodejs { jsState.nodejs.action?.execute(this) }
                }
            }
        }
    }
}

internal fun configureMultiplatformLinux(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linux.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val linuxArm64Main: KSS? = sourceSets.findByName("linuxArm64Main")
            val linuxArm32HfpMain: KSS? = sourceSets.findByName("linuxArm32HfpMain")
            val linuxMips32Main: KSS? = sourceSets.findByName("linuxMips32Main")
            val linuxMipsel32Main: KSS? = sourceSets.findByName("linuxMipsel32Main")
            val linuxX64Main: KSS? = sourceSets.findByName("linuxX64Main")

            val commonTest: KSS by sourceSets.getting
            val linuxArm64Test: KSS? = sourceSets.findByName("linuxArm64Test")
            val linuxArm32HfpTest: KSS? = sourceSets.findByName("linuxArm32HfpTest")
            val linuxMips32Test: KSS? = sourceSets.findByName("linuxMips32Test")
            val linuxMipsel32Test: KSS? = sourceSets.findByName("linuxMipsel32Test")
            val linuxX64Test: KSS? = sourceSets.findByName("linuxX64Test")

            val linuxMainSourceSets: List<KSS> =
                listOfNotNull(
                    linuxArm64Main,
                    linuxArm32HfpMain,
                    linuxMips32Main,
                    linuxMipsel32Main,
                    linuxX64Main,
                )

            val linuxTestSourceSets: List<KSS> =
                listOfNotNull(
                    linuxArm64Test,
                    linuxArm32HfpTest,
                    linuxMips32Test,
                    linuxMipsel32Test,
                    linuxX64Test,
                )

            val linuxMain = sourceSets.maybeCreate("linuxMain")
            val linuxTest = sourceSets.maybeCreate("linuxTest")

            linuxMain.dependsOn(commonMain)
            for (linuxMainSourceSet in linuxMainSourceSets) {
                linuxMainSourceSet.dependsOn(linuxMain)
            }

            linuxTest.dependsOn(commonTest)
            for (linuxTestSourceSet in linuxTestSourceSets) {
                linuxTestSourceSet.dependsOn(linuxTest)
            }
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
            val commonMain: KSS by sourceSets.getting
            val iosMain: KSS? = sourceSets.findByName("iosMain")
            val iosArm32Main: KSS? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KSS? = sourceSets.findByName("iosArm64Main")
            val iosSimulatorArm64Main: KSS? = sourceSets.findByName("iosSimulatorArm64Main")
            val iosX64Main: KSS? = sourceSets.findByName("iosX64Main")
            val linuxMain: KSS? = sourceSets.findByName("linuxMain")
            val linuxArm32HfpMain: KSS? = sourceSets.findByName("linuxArm32HfpMain")
            val linuxArm64Main: KSS? = sourceSets.findByName("linuxArm64Main")
            val linuxMips32Main: KSS? = sourceSets.findByName("linuxMips32Main")
            val linuxMipsel32Main: KSS? = sourceSets.findByName("linuxMipsel32Main")
            val linuxX64Main: KSS? = sourceSets.findByName("linuxX64Main")
            val macosMain: KSS? = sourceSets.findByName("macosMain")
            val macosArm64Main: KSS? = sourceSets.findByName("macosArm64Main")
            val macosX64Main: KSS? = sourceSets.findByName("macosX64Main")
            val mingwMain: KSS? = sourceSets.findByName("mingwMain")
            val mingwX64Main: KSS? = sourceSets.findByName("mingwX64Main")
            val mingwX86Main: KSS? = sourceSets.findByName("mingwX86Main")
            val tvosMain: KSS? = sourceSets.findByName("tvosMain")
            val tvosArm64Main: KSS? = sourceSets.findByName("tvosArm64Main")
            val tvosSimulatorArm64Main: KSS? = sourceSets.findByName("tvosSimulatorArm64Main")
            val tvosX64Main: KSS? = sourceSets.findByName("tvosX64Main")
            val wasmMain: KSS? = sourceSets.findByName("wasmMain")
            val wasm32Main: KSS? = sourceSets.findByName("wasm32Main")
            val watchosMain: KSS? = sourceSets.findByName("watchosMain")
            val watchosArm32Main: KSS? = sourceSets.findByName("watchosArm32Main")
            val watchosArm64Main: KSS? = sourceSets.findByName("watchosArm64Main")
            val watchosSimulatorArm64Main: KSS? = sourceSets.findByName("watchosSimulatorArm64Main")
            val watchosX64Main: KSS? = sourceSets.findByName("watchosX64Main")
            val watchosX86Main: KSS? = sourceSets.findByName("watchosX86Main")

            val commonTest: KSS by sourceSets.getting
            val iosTest: KSS? = sourceSets.findByName("iosTest")
            val iosArm32Test: KSS? = sourceSets.findByName("iosArm32Test")
            val iosArm64Test: KSS? = sourceSets.findByName("iosArm64Test")
            val iosSimulatorArm64Test: KSS? = sourceSets.findByName("iosSimulatorArm64Test")
            val iosX64Test: KSS? = sourceSets.findByName("iosX64Test")
            val linuxTest: KSS? = sourceSets.findByName("linuxTest")
            val linuxArm32HfpTest: KSS? = sourceSets.findByName("linuxArm32HfpTest")
            val linuxArm64Test: KSS? = sourceSets.findByName("linuxArm64Test")
            val linuxMips32Test: KSS? = sourceSets.findByName("linuxMips32Test")
            val linuxMipsel32Test: KSS? = sourceSets.findByName("linuxMipsel32Test")
            val linuxX64Test: KSS? = sourceSets.findByName("linuxX64Test")
            val macosTest: KSS? = sourceSets.findByName("macosTest")
            val macosArm64Test: KSS? = sourceSets.findByName("macosArm64Test")
            val macosX64Test: KSS? = sourceSets.findByName("macosX64Test")
            val mingwTest: KSS? = sourceSets.findByName("mingwTest")
            val mingwX64Test: KSS? = sourceSets.findByName("mingwX64Test")
            val mingwX86Test: KSS? = sourceSets.findByName("mingwX86Test")
            val tvosTest: KSS? = sourceSets.findByName("tvosTest")
            val tvosArm64Test: KSS? = sourceSets.findByName("tvosArm64Test")
            val tvosSimulatorArm64Test: KSS? = sourceSets.findByName("tvosSimulatorArm64Test")
            val tvosX64Test: KSS? = sourceSets.findByName("tvosX64Test")
            val wasmTest: KSS? = sourceSets.findByName("wasmTest")
            val wasm32Test: KSS? = sourceSets.findByName("wasm32Test")
            val watchosTest: KSS? = sourceSets.findByName("watchosTest")
            val watchosArm32Test: KSS? = sourceSets.findByName("watchosArm32Test")
            val watchosArm64Test: KSS? = sourceSets.findByName("watchosArm64Test")
            val watchosSimulatorArm64Test: KSS? = sourceSets.findByName("watchosSimulatorArm64Test")
            val watchosX64Test: KSS? = sourceSets.findByName("watchosX64Test")
            val watchosX86Test: KSS? = sourceSets.findByName("watchosX86Test")

            val nativeMainSourceSets: List<KSS> =
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

            val nativeTestSourceSets: List<KSS> =
                listOfNotNull(
                    iosTest,
                    iosArm32Test,
                    iosArm64Test,
                    iosSimulatorArm64Test,
                    iosX64Test,
                    linuxTest,
                    linuxArm32HfpTest,
                    linuxArm64Test,
                    linuxMips32Test,
                    linuxMipsel32Test,
                    linuxX64Test,
                    macosTest,
                    macosArm64Test,
                    macosX64Test,
                    mingwTest,
                    mingwX64Test,
                    mingwX86Test,
                    tvosTest,
                    tvosArm64Test,
                    tvosSimulatorArm64Test,
                    tvosX64Test,
                    wasmTest,
                    wasm32Test,
                    watchosTest,
                    watchosArm32Test,
                    watchosArm64Test,
                    watchosSimulatorArm64Test,
                    watchosX64Test,
                    watchosX86Test,
                )

            val nativeMain = sourceSets.maybeCreate("nativeMain")
            val nativeTest = sourceSets.maybeCreate("nativeTest")

            nativeMain.dependsOn(commonMain)
            for (nativeMainSourceSet in nativeMainSourceSets) {
                nativeMainSourceSet.dependsOn(nativeMain)
            }

            nativeTest.dependsOn(commonTest)
            for (nativeTestSourceSet in nativeTestSourceSets) {
                nativeTestSourceSet.dependsOn(nativeTest)
            }
        }
    }
}

internal fun configureMultiplatformMacOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.macos.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val macosX64Main: KSS? = sourceSets.findByName("macosX64Main")
            val macosArm64Main: KSS? = sourceSets.findByName("macosArm64Main")

            val commonTest: KSS by sourceSets.getting
            val macosX64Test: KSS? = sourceSets.findByName("macosX64Test")
            val macosArm64Test: KSS? = sourceSets.findByName("macosArm64Test")

            val macosMainSourceSets: List<KSS> = listOfNotNull(macosX64Main, macosArm64Main)
            val macosTestSourceSets: List<KSS> = listOfNotNull(macosX64Test, macosArm64Test)

            val macosMain = sourceSets.maybeCreate("macosMain")
            val macosTest = sourceSets.maybeCreate("macosTest")

            macosMain.dependsOn(commonMain)
            for (macosMainSourceSet in macosMainSourceSets) {
                macosMainSourceSet.dependsOn(macosMain)
            }

            macosTest.dependsOn(commonTest)
            for (macosTestSourceSet in macosTestSourceSets) {
                macosTestSourceSet.dependsOn(macosTest)
            }
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
            val commonMain: KSS by sourceSets.getting
            val mingwX64Main: KSS? = sourceSets.findByName("mingwX64Main")
            val mingwX86Main: KSS? = sourceSets.findByName("mingwX86Main")

            val commonTest: KSS by sourceSets.getting
            val mingwX64Test: KSS? = sourceSets.findByName("mingwX64Test")
            val mingwX86Test: KSS? = sourceSets.findByName("mingwX86Test")

            val mingwMainSourceSets: List<KSS> = listOfNotNull(mingwX64Main, mingwX86Main)
            val mingwTestSourceSets: List<KSS> = listOfNotNull(mingwX64Test, mingwX86Test)

            val mingwMain = sourceSets.maybeCreate("mingwMain")
            val mingwTest = sourceSets.maybeCreate("mingwTest")

            mingwMain.dependsOn(commonMain)
            for (mingwMainSourceSet in mingwMainSourceSets) {
                mingwMainSourceSet.dependsOn(mingwMain)
            }

            mingwTest.dependsOn(commonTest)
            for (mingwTestSourceSet in mingwTestSourceSets) {
                mingwTestSourceSet.dependsOn(mingwTest)
            }
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
            val commonMain: KSS by sourceSets.getting
            val tvosArm64Main: KSS? = sourceSets.findByName("tvosArm64Main")
            val tvosX64Main: KSS? = sourceSets.findByName("tvosX64Main")
            val tvosSimulatorArm64Main: KSS? = sourceSets.findByName("tvosSimulatorArm64Main")

            val commonTest: KSS by sourceSets.getting
            val tvosArm64Test: KSS? = sourceSets.findByName("tvosArm64Test")
            val tvosX64Test: KSS? = sourceSets.findByName("tvosX64Test")
            val tvosSimulatorArm64Test: KSS? = sourceSets.findByName("tvosSimulatorArm64Test")

            val tvosMainSourceSets: List<KSS> =
                listOfNotNull(tvosArm64Main, tvosX64Main, tvosSimulatorArm64Main)
            val tvosTestSourceSets: List<KSS> =
                listOfNotNull(tvosArm64Test, tvosX64Test, tvosSimulatorArm64Test)

            val tvosMain = sourceSets.maybeCreate("tvosMain")
            val tvosTest = sourceSets.maybeCreate("tvosTest")

            tvosMain.dependsOn(commonMain)
            for (tvosMainSourceSet in tvosMainSourceSets) {
                tvosMainSourceSet.dependsOn(tvosMain)
            }

            tvosTest.dependsOn(commonTest)
            for (tvosTestSourceSet in tvosTestSourceSets) {
                tvosTestSourceSet.dependsOn(tvosTest)
            }
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
            val commonMain: KSS by sourceSets.getting
            val wasm32Main: KSS? = sourceSets.findByName("wasm32Main")

            val commonTest: KSS by sourceSets.getting
            val wasm32Test: KSS? = sourceSets.findByName("wasm32Test")

            val wasmMainSourceSets: List<KSS> = listOfNotNull(wasm32Main)
            val wasmTestSourceSets: List<KSS> = listOfNotNull(wasm32Test)

            val wasmMain = sourceSets.maybeCreate("wasmMain")
            val wasmTest = sourceSets.maybeCreate("wasmTest")

            wasmMain.dependsOn(commonMain)
            for (wasmMainSourceSet in wasmMainSourceSets) {
                wasmMainSourceSet.dependsOn(wasmMain)
            }

            wasmTest.dependsOn(commonTest)
            for (wasmTestSourceSet in wasmTestSourceSets) {
                wasmTestSourceSet.dependsOn(wasmTest)
            }
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
            val commonMain: KSS by sourceSets.getting
            val watchosArm32Main: KSS? = sourceSets.findByName("watchosArm32Main")
            val watchosArm64Main: KSS? = sourceSets.findByName("watchosArm64Main")
            val watchosX64Main: KSS? = sourceSets.findByName("watchosX64Main")
            val watchosSimulatorArm64Main: KSS? = sourceSets.findByName("watchosSimulatorArm64Main")
            val watchosX86Main: KSS? = sourceSets.findByName("watchosX86Main")

            val commonTest: KSS by sourceSets.getting
            val watchosArm32Test: KSS? = sourceSets.findByName("watchosArm32Test")
            val watchosArm64Test: KSS? = sourceSets.findByName("watchosArm64Test")
            val watchosX64Test: KSS? = sourceSets.findByName("watchosX64Test")
            val watchosSimulatorArm64Test: KSS? = sourceSets.findByName("watchosSimulatorArm64Test")
            val watchosX86Test: KSS? = sourceSets.findByName("watchosX86Test")

            val watchosMainSourceSets: List<KSS> =
                listOfNotNull(
                    watchosArm32Main,
                    watchosArm64Main,
                    watchosX64Main,
                    watchosSimulatorArm64Main,
                    watchosX86Main,
                )
            val watchosTestSourceSets: List<KSS> =
                listOfNotNull(
                    watchosArm32Test,
                    watchosArm64Test,
                    watchosX64Test,
                    watchosSimulatorArm64Test,
                    watchosX86Test,
                )

            val watchosMain = sourceSets.maybeCreate("watchosMain")
            val watchosTest = sourceSets.maybeCreate("watchosTest")

            watchosMain.dependsOn(commonMain)
            for (watchosMainSourceSet in watchosMainSourceSets) {
                watchosMainSourceSet.dependsOn(watchosMain)
            }

            watchosTest.dependsOn(commonTest)
            for (watchosTestSourceSet in watchosTestSourceSets) {
                watchosTestSourceSet.dependsOn(watchosMain)
            }
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
