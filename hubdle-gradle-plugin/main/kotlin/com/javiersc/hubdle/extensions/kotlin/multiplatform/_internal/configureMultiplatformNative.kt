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
            val iosArm32Main: KSS? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KSS? = sourceSets.findByName("iosArm64Main")
            val iosX64Main: KSS? = sourceSets.findByName("iosX64Main")
            val iosSimulatorArm64Main: KSS? = sourceSets.findByName("iosSimulatorArm64Main")
            val linuxArm64Main: KSS? = sourceSets.findByName("linuxArm64Main")
            val linuxArm32HfpMain: KSS? = sourceSets.findByName("linuxArm32HfpMain")
            val linuxMips32Main: KSS? = sourceSets.findByName("linuxMips32Main")
            val linuxMipsel32Main: KSS? = sourceSets.findByName("linuxMipsel32Main")
            val linuxX64Main: KSS? = sourceSets.findByName("linuxX64Main")
            val macosX64Main: KSS? = sourceSets.findByName("macosX64Main")
            val macosArm64Main: KSS? = sourceSets.findByName("macosArm64Main")
            val mingwX64Main: KSS? = sourceSets.findByName("mingwX64Main")
            val mingwX86Main: KSS? = sourceSets.findByName("mingwX86Main")
            val tvosArm64Main: KSS? = sourceSets.findByName("tvosArm64Main")
            val tvosX64Main: KSS? = sourceSets.findByName("tvosX64Main")
            val tvosSimulatorArm64Main: KSS? = sourceSets.findByName("tvosSimulatorArm64Main")
            val wasm32Main: KSS? = sourceSets.findByName("wasm32Main")
            val watchosArm32Main: KSS? = sourceSets.findByName("watchosArm32Main")
            val watchosArm64Main: KSS? = sourceSets.findByName("watchosArm64Main")
            val watchosX64Main: KSS? = sourceSets.findByName("watchosX64Main")
            val watchosSimulatorArm64Main: KSS? = sourceSets.findByName("watchosSimulatorArm64Main")
            val watchosX86Main: KSS? = sourceSets.findByName("watchosX86Main")

            val commonTest: KSS by sourceSets.getting
            val iosArm32Test: KSS? = sourceSets.findByName("iosArm32Test")
            val iosArm64Test: KSS? = sourceSets.findByName("iosArm64Test")
            val iosX64Test: KSS? = sourceSets.findByName("iosX64Test")
            val iosSimulatorArm64Test: KSS? = sourceSets.findByName("iosSimulatorArm64Test")
            val linuxArm64Test: KSS? = sourceSets.findByName("linuxArm64Test")
            val linuxArm32HfpTest: KSS? = sourceSets.findByName("linuxArm32HfpTest")
            val linuxMips32Test: KSS? = sourceSets.findByName("linuxMips32Test")
            val linuxMipsel32Test: KSS? = sourceSets.findByName("linuxMipsel32Test")
            val linuxX64Test: KSS? = sourceSets.findByName("linuxX64Test")
            val macosX64Test: KSS? = sourceSets.findByName("macosX64Test")
            val macosArm64Test: KSS? = sourceSets.findByName("macosArm64Test")
            val mingwX64Test: KSS? = sourceSets.findByName("mingwX64Test")
            val mingwX86Test: KSS? = sourceSets.findByName("mingwX86Test")
            val tvosArm64Test: KSS? = sourceSets.findByName("tvosArm64Test")
            val tvosX64Test: KSS? = sourceSets.findByName("tvosX64Test")
            val tvosSimulatorArm64Test: KSS? = sourceSets.findByName("tvosSimulatorArm64Test")
            val wasm32Test: KSS? = sourceSets.findByName("wasm32Test")
            val watchosArm32Test: KSS? = sourceSets.findByName("watchosArm32Test")
            val watchosArm64Test: KSS? = sourceSets.findByName("watchosArm64Test")
            val watchosX64Test: KSS? = sourceSets.findByName("watchosX64Test")
            val watchosSimulatorArm64Test: KSS? = sourceSets.findByName("watchosSimulatorArm64Test")
            val watchosX86Test: KSS? = sourceSets.findByName("watchosX86Test")

            val nativeMainSourceSets: List<KSS> =
                listOfNotNull(
                    iosArm32Main,
                    iosArm64Main,
                    iosX64Main,
                    iosSimulatorArm64Main,
                    linuxArm64Main,
                    linuxArm32HfpMain,
                    linuxMips32Main,
                    linuxMipsel32Main,
                    linuxX64Main,
                    macosX64Main,
                    macosArm64Main,
                    mingwX64Main,
                    mingwX86Main,
                    tvosArm64Main,
                    tvosX64Main,
                    tvosSimulatorArm64Main,
                    wasm32Main,
                    watchosArm32Main,
                    watchosArm64Main,
                    watchosX64Main,
                    watchosSimulatorArm64Main,
                    watchosX86Main,
                )

            val nativeTestSourceSets: List<KSS> =
                listOfNotNull(
                    iosArm32Test,
                    iosArm64Test,
                    iosX64Test,
                    iosSimulatorArm64Test,
                    linuxArm64Test,
                    linuxArm32HfpTest,
                    linuxMips32Test,
                    linuxMipsel32Test,
                    linuxX64Test,
                    macosX64Test,
                    macosArm64Test,
                    mingwX64Test,
                    mingwX86Test,
                    tvosArm64Test,
                    tvosX64Test,
                    tvosSimulatorArm64Test,
                    wasm32Test,
                    watchosArm32Test,
                    watchosArm64Test,
                    watchosX64Test,
                    watchosSimulatorArm64Test,
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
