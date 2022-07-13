package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformIOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.ios.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val darwinMain: KSS? = sourceSets.findByName("darwinMain")
            val iosArm32Main: KSS? = sourceSets.findByName("iosArm32Main")
            val iosArm64Main: KSS? = sourceSets.findByName("iosArm64Main")
            val iosX64Main: KSS? = sourceSets.findByName("iosX64Main")
            val iosSimulatorArm64Main: KSS? = sourceSets.findByName("iosSimulatorArm64Main")

            val commonTest: KSS by sourceSets.getting
            val darwinTest: KSS? = sourceSets.findByName("darwinTest")
            val iosArm32Test: KSS? = sourceSets.findByName("iosArm32Test")
            val iosArm64Test: KSS? = sourceSets.findByName("iosArm64Test")
            val iosX64Test: KSS? = sourceSets.findByName("iosX64Test")
            val iosSimulatorArm64Test: KSS? = sourceSets.findByName("iosSimulatorArm64Test")

            val iosMainSourceSets: List<KSS> =
                listOfNotNull(
                    iosArm32Main,
                    iosArm64Main,
                    iosX64Main,
                    iosSimulatorArm64Main,
                )

            val iosTestSourceSets: List<KSS> =
                listOfNotNull(
                    iosArm32Test,
                    iosArm64Test,
                    iosX64Test,
                    iosSimulatorArm64Test,
                )

            val iosMain = sourceSets.maybeCreate("iosMain")
            val iosTest = sourceSets.maybeCreate("iosTest")

            iosMain.dependsOn(commonMain)
            if (darwinMain != null) iosMain.dependsOn(darwinMain)
            for (iosMainSourceSet in iosMainSourceSets) {
                iosMainSourceSet.dependsOn(iosMain)
            }
            iosTest.dependsOn(commonTest)
            if (darwinTest != null) iosTest.dependsOn(darwinTest)
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
