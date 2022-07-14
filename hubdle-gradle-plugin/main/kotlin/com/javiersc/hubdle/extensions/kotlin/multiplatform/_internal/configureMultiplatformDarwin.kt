package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformDarwin(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.darwin.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val iosArm32Main: KSS? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KSS? = sourceSets.findByName("iosArm64Main")
            val iosX64Main: KSS? = sourceSets.findByName("iosX64Main")
            val iosSimulatorArm64Main: KSS? = sourceSets.findByName("iosSimulatorArm64Main")
            val macosX64Main: KSS? = sourceSets.findByName("macosX64Main")
            val macosArm64Main: KSS? = sourceSets.findByName("macosArm64Main")
            val tvosArm64Main: KSS? = sourceSets.findByName("tvosArm64Main")
            val tvosX64Main: KSS? = sourceSets.findByName("tvosX64Main")
            val tvosSimulatorArm64Main: KSS? = sourceSets.findByName("tvosSimulatorArm64Main")
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
            val macosX64Test: KSS? = sourceSets.findByName("macosX64Test")
            val macosArm64Test: KSS? = sourceSets.findByName("macosArm64Test")
            val tvosArm64Test: KSS? = sourceSets.findByName("tvosArm64Test")
            val tvosX64Test: KSS? = sourceSets.findByName("tvosX64Test")
            val tvosSimulatorArm64Test: KSS? = sourceSets.findByName("tvosSimulatorArm64Test")
            val watchosArm32Test: KSS? = sourceSets.findByName("watchosArm32Test")
            val watchosArm64Test: KSS? = sourceSets.findByName("watchosArm64Test")
            val watchosX64Test: KSS? = sourceSets.findByName("watchosX64Test")
            val watchosSimulatorArm64Test: KSS? = sourceSets.findByName("watchosSimulatorArm64Test")
            val watchosX86Test: KSS? = sourceSets.findByName("watchosX86Test")

            val darwinMainSourceSets: List<KSS> =
                listOfNotNull(
                    iosArm32Main,
                    iosArm64Main,
                    iosX64Main,
                    iosSimulatorArm64Main,
                    macosX64Main,
                    macosArm64Main,
                    tvosArm64Main,
                    tvosX64Main,
                    tvosSimulatorArm64Main,
                    watchosArm32Main,
                    watchosArm64Main,
                    watchosX64Main,
                    watchosSimulatorArm64Main,
                    watchosX86Main,
                )

            val darwinTestSourceSets: List<KSS> =
                listOfNotNull(
                    iosArm32Test,
                    iosArm64Test,
                    iosX64Test,
                    iosSimulatorArm64Test,
                    macosX64Test,
                    macosArm64Test,
                    tvosArm64Test,
                    tvosX64Test,
                    tvosSimulatorArm64Test,
                    watchosArm32Test,
                    watchosArm64Test,
                    watchosX64Test,
                    watchosSimulatorArm64Test,
                    watchosX86Test,
                )

            val darwinMain = sourceSets.maybeCreate("darwinMain")
            val darwinTest = sourceSets.maybeCreate("darwinTest")

            darwinMain.dependsOn(commonMain)
            for (darwinMainSourceSet in darwinMainSourceSets) {
                darwinMainSourceSet.dependsOn(darwinMain)
            }

            darwinTest.dependsOn(commonTest)
            for (darwinTestSourceSet in darwinTestSourceSets) {
                darwinTestSourceSet.dependsOn(darwinTest)
            }
        }
    }
}
