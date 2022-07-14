package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformMacOS(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.macos.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val macosX64Main: KSS? = sourceSets.findByName("macosX64Main")
            val macosArm64Main: KSS? = sourceSets.findByName("macosArm64Main")

            val commonTest: KSS by sourceSets.getting
            val macosX64Test: KSS? = sourceSets.findByName("macosX64Test")
            val macosArm64Test: KSS? = sourceSets.findByName("macosArm64Test")

            val macosMainSourceSets: List<KSS> =
                listOfNotNull(
                    macosX64Main,
                    macosArm64Main,
                )
            val macosTestSourceSets: List<KSS> =
                listOfNotNull(
                    macosX64Test,
                    macosArm64Test,
                )

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
