package com.javiersc.hubdle.project.kotlin.multiplatform.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

internal class KotlinMultiplatformFeaturesComposeTest : GradleTestKitTest() {

    @Test
    fun compose() {
        gradleTestKitTest("kotlin/multiplatform/features/compose") {
            gradlew("build").task(":build").shouldNotBeNull().outcome.shouldBe(TaskOutcome.SUCCESS)
        }
    }
}
