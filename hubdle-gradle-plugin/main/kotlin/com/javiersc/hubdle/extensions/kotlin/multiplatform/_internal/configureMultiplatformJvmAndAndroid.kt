package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureMultiplatformJvmAndAndroid(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.jvmAndAndroid.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            /* TODO: enable when granular metadata for jvm+android is fixed
            val jvmAndAndroidMain = sourceSets.create("jvmAndAndroidMain")
            val jvmAndAndroidTest = sourceSets.create("jvmAndAndroidTest")

            jvmAndAndroidMain.dependsOn(sourceSets.getByName("commonMain"))
            jvmAndAndroidTest.dependsOn(sourceSets.getByName("commonTest"))

            sourceSets.findByName("jvmMain")?.dependsOn(jvmAndAndroidMain)
            sourceSets.findByName("jvmTest")?.dependsOn(jvmAndAndroidTest)

            sourceSets.findByName("androidMain")?.dependsOn(jvmAndAndroidMain)
            sourceSets.findByName("androidTest")?.dependsOn(jvmAndAndroidTest)
            */

            // TODO: remove when granular metadata for jvm+android is fixed
            val mainKotlin = "jvmAndAndroid/main/kotlin"
            val mainResources = "jvmAndAndroid/main/resources"
            val testKotlin = "jvmAndAndroid/test/kotlin"
            val testResources = "jvmAndAndroid/test/resources"

            sourceSets.findByName("androidMain")?.apply {
                kotlin.srcDirs(mainKotlin)
                resources.srcDirs(mainResources)
            }
            sourceSets.findByName("androidTest")?.apply {
                kotlin.srcDirs(testKotlin)
                resources.srcDirs(testResources)
            }
            sourceSets.findByName("jvmMain")?.apply {
                kotlin.srcDirs(mainKotlin)
                resources.srcDirs(mainResources)
            }
            sourceSets.findByName("jvmTest")?.apply {
                kotlin.srcDirs(testKotlin)
                resources.srcDirs(testResources)
            }
        }
    }
}
