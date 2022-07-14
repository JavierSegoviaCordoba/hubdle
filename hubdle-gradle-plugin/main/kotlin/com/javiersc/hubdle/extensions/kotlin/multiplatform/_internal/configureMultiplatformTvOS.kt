package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

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
                listOfNotNull(
                    tvosArm64Main,
                    tvosX64Main,
                    tvosSimulatorArm64Main,
                )
            val tvosTestSourceSets: List<KSS> =
                listOfNotNull(
                    tvosArm64Test,
                    tvosX64Test,
                    tvosSimulatorArm64Test,
                )

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
