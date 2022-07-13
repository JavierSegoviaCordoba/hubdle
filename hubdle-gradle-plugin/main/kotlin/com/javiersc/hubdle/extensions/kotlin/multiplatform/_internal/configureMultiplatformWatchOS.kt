package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformWatchOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.watchos.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val darwinMain: KSS? = sourceSets.findByName("darwinMain")
            val watchosArm32Main: KSS? = sourceSets.findByName("watchosArm32Main")
            val watchosArm64Main: KSS? = sourceSets.findByName("watchosArm64Main")
            val watchosX64Main: KSS? = sourceSets.findByName("watchosX64Main")
            val watchosSimulatorArm64Main: KSS? = sourceSets.findByName("watchosSimulatorArm64Main")
            val watchosX86Main: KSS? = sourceSets.findByName("watchosX86Main")

            val commonTest: KSS by sourceSets.getting
            val darwinTest: KSS? = sourceSets.findByName("darwinTest")
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
            if (darwinMain != null) watchosMain.dependsOn(darwinMain)
            for (watchosMainSourceSet in watchosMainSourceSets) {
                watchosMainSourceSet.dependsOn(watchosMain)
            }

            watchosTest.dependsOn(commonTest)
            if (darwinTest != null) watchosTest.dependsOn(darwinTest)
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
