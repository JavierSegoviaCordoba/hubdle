package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS

internal fun configureMultiplatformMinGW(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.mingw.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            val commonMain: KSS by sourceSets.getting
            val nativeMain: KSS? = sourceSets.findByName("nativeMain")
            val mingwX64Main: KSS? = sourceSets.findByName("mingwX64Main")
            val mingwX86Main: KSS? = sourceSets.findByName("mingwX86Main")

            val commonTest: KSS by sourceSets.getting
            val nativeTest: KSS? = sourceSets.findByName("nativeTest")
            val mingwX64Test: KSS? = sourceSets.findByName("mingwX64Test")
            val mingwX86Test: KSS? = sourceSets.findByName("mingwX86Test")

            val mingwMainSourceSets: List<KSS> =
                listOfNotNull(
                    mingwX64Main,
                    mingwX86Main,
                )
            val mingwTestSourceSets: List<KSS> =
                listOfNotNull(
                    mingwX64Test,
                    mingwX86Test,
                )

            val mingwMain = sourceSets.maybeCreate("mingwMain")
            val mingwTest = sourceSets.maybeCreate("mingwTest")

            mingwMain.dependsOn(commonMain)
            if (nativeMain != null) mingwMain.dependsOn(nativeMain)
            for (mingwMainSourceSet in mingwMainSourceSets) {
                mingwMainSourceSet.dependsOn(mingwMain)
            }

            mingwTest.dependsOn(commonTest)
            if (nativeTest != null) mingwTest.dependsOn(nativeTest)
            for (mingwTestSourceSet in mingwTestSourceSets) {
                mingwTestSourceSet.dependsOn(mingwTest)
            }
        }
    }
}

internal fun configureMultiplatformMinGWX64(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.mingwX64.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { mingwX64() }
    }
}

internal fun configureMultiplatformMinGWX86(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.mingwX86.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { mingwX86() }
    }
}
