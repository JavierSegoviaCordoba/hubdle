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
            val linuxMain: KSS? = sourceSets.findByName("linuxMain")
            val mingwMain: KSS? = sourceSets.findByName("mingwMain")
            val wasmMain: KSS? = sourceSets.findByName("wasmMain")

            val commonTest: KSS by sourceSets.getting
            val darwinTest: KSS? = sourceSets.findByName("darwinTest")
            val linuxTest: KSS? = sourceSets.findByName("linuxTest")
            val mingwTest: KSS? = sourceSets.findByName("mingwTest")
            val wasmTest: KSS? = sourceSets.findByName("wasmTest")

            val nativeMainSourceSets: List<KSS> =
                listOfNotNull(
                    darwinMain,
                    linuxMain,
                    mingwMain,
                    wasmMain,
                )

            val nativeTestSourceSets: List<KSS> =
                listOfNotNull(
                    darwinTest,
                    linuxTest,
                    mingwTest,
                    wasmTest,
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
