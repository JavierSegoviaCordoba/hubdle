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
            val nativeMain: KSS? = sourceSets.findByName("nativeMain")
            val iosMain: KSS? = sourceSets.findByName("iosMain")
            val macosMain: KSS? = sourceSets.findByName("macosMain")
            val tvosMain: KSS? = sourceSets.findByName("tvosMain")
            val watchosMain: KSS? = sourceSets.findByName("watchosMain")

            val commonTest: KSS by sourceSets.getting
            val nativeTest: KSS? = sourceSets.findByName("nativeTest")
            val iosTest: KSS? = sourceSets.findByName("iosTest")
            val macosTest: KSS? = sourceSets.findByName("macosTest")
            val tvosTest: KSS? = sourceSets.findByName("tvosTest")
            val watchosTest: KSS? = sourceSets.findByName("watchosTest")

            val darwinMainSourceSets: List<KSS> =
                listOfNotNull(
                    iosMain,
                    macosMain,
                    tvosMain,
                    watchosMain,
                )

            val darwinTestSourceSets: List<KSS> =
                listOfNotNull(
                    iosTest,
                    macosTest,
                    tvosTest,
                    watchosTest,
                )

            val darwinMain = sourceSets.maybeCreate("darwinMain")
            val darwinTest = sourceSets.maybeCreate("darwinTest")

            darwinMain.dependsOn(commonMain)
            if (nativeMain != null) darwinMain.dependsOn(nativeMain)
            for (darwinMainSourceSet in darwinMainSourceSets) {
                darwinMainSourceSet.dependsOn(darwinMain)
            }

            darwinTest.dependsOn(commonTest)
            if (nativeTest != null) darwinTest.dependsOn(nativeTest)
            for (darwinTestSourceSet in darwinTestSourceSets) {
                darwinTestSourceSet.dependsOn(darwinTest)
            }
        }
    }
}
