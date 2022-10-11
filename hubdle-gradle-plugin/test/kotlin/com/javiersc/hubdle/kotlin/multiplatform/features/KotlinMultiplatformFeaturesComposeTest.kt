package com.javiersc.hubdle.kotlin.multiplatform.features

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

internal class KotlinMultiplatformFeaturesComposeTest : GradleTest() {

    @Test
    fun compose() {
        gradleTestKitTest("kotlin/multiplatform/features/compose") {
            gradlew("build").task(":build").shouldNotBeNull().outcome.shouldBe(TaskOutcome.SUCCESS)
        }
    }
}
