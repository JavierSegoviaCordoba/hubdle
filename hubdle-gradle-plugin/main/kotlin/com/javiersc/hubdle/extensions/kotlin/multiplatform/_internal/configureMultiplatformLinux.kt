package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformLinux(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.linux.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val nativeMain: KSS? = sourceSets.findByName("nativeMain")
            val linuxArm64Main: KSS? = sourceSets.findByName("linuxArm64Main")
            val linuxArm32HfpMain: KSS? = sourceSets.findByName("linuxArm32HfpMain")
            val linuxMips32Main: KSS? = sourceSets.findByName("linuxMips32Main")
            val linuxMipsel32Main: KSS? = sourceSets.findByName("linuxMipsel32Main")
            val linuxX64Main: KSS? = sourceSets.findByName("linuxX64Main")

            val commonTest: KSS by sourceSets.getting
            val nativeTest: KSS? = sourceSets.findByName("nativeTest")
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
            if (nativeMain != null) linuxMain.dependsOn(nativeMain)
            for (linuxMainSourceSet in linuxMainSourceSets) {
                linuxMainSourceSet.dependsOn(linuxMain)
            }

            linuxTest.dependsOn(commonTest)
            if (nativeTest != null) linuxTest.dependsOn(nativeTest)
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
