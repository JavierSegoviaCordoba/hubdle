package com.javiersc.hubdle.kotlin.multiplatform.features

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import kotlin.test.Test

internal class KotlinMultiplatformFeaturesComposeTest : GradleTest() {

    @Test
    fun compose() {
        gradleTestKitTest("kotlin/multiplatform/features/compose") {
            println("------ output starts ------")
            println(gradlew("build").output)
            println("------ output ends ------")
        }
    }
}
