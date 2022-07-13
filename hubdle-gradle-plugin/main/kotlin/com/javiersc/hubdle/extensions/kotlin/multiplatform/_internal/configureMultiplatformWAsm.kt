package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformWAsm(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.wasm.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val nativeMain: KSS? = sourceSets.findByName("nativeMain")
            val wasm32Main: KSS? = sourceSets.findByName("wasm32Main")

            val commonTest: KSS by sourceSets.getting
            val nativeTest: KSS? = sourceSets.findByName("nativeTest")
            val wasm32Test: KSS? = sourceSets.findByName("wasm32Test")

            val wasmMainSourceSets: List<KSS> =
                listOfNotNull(
                    wasm32Main,
                )
            val wasmTestSourceSets: List<KSS> =
                listOfNotNull(
                    wasm32Test,
                )

            val wasmMain = sourceSets.maybeCreate("wasmMain")
            val wasmTest = sourceSets.maybeCreate("wasmTest")

            wasmMain.dependsOn(commonMain)
            if (nativeMain != null) wasmMain.dependsOn(nativeMain)
            for (wasmMainSourceSet in wasmMainSourceSets) {
                wasmMainSourceSet.dependsOn(wasmMain)
            }

            wasmTest.dependsOn(commonTest)
            if (nativeTest != null) wasmTest.dependsOn(nativeTest)
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
