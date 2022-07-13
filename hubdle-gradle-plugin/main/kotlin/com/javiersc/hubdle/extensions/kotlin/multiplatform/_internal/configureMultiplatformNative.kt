package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformNative(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.native.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val darwinMain: KSS? = sourceSets.findByName("darwinMain")
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
            val darwinTest: KSS? = sourceSets.findByName("darwinTest")
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
                    darwinMain,
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
                    darwinTest,
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
