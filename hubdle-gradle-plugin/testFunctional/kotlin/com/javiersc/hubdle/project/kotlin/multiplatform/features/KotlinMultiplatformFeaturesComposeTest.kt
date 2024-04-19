package com.javiersc.hubdle.project.kotlin.multiplatform.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.hubdle.gradle.plugin.HubdleGradlePluginProjectData.RootDirAbsolutePath
import com.javiersc.hubdle.project.fixtures.KotlinVersionEnv
import com.javiersc.hubdle.project.fixtures.KotlinVersionRegexOrEmptyOrNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.io.File
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.condition.EnabledIfSystemProperty

internal class KotlinMultiplatformFeaturesComposeTest : GradleTestKitTest() {

    @Test
    @EnabledIfSystemProperty(named = KotlinVersionEnv, matches = KotlinVersionRegexOrEmptyOrNull)
    fun compose() {
        gradleTestKitTest("kotlin/multiplatform/features/compose") {
            val hubdleTomlDestination =
                projectDir.resolve("gradle/hubdle.libs.versions.toml").apply {
                    parentFile.mkdirs()
                    createNewFile()
                }
            File(RootDirAbsolutePath)
                .resolve("gradle/hubdle.libs.versions.toml")
                .copyTo(target = hubdleTomlDestination, overwrite = true)
            gradlew("build", stacktrace(), "--no-scan")
                .task(":build")
                .shouldNotBeNull()
                .outcome
                .shouldBe(TaskOutcome.SUCCESS)
        }
    }
}
