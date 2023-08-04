package com.javiersc.hubdle.project.config

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import kotlin.test.Test

internal class VersioningTest : GradleTestKitTest() {

    @Test
    fun `given a project with semver enabled when it builds then tag prefix is v`() {
        gradleTestKitTest("config/versioning/semver-1") {
            gradlew("assemble").output.apply {
                contains("semver.tagPrefix: v")
                contains("semver.version: 1.0.0")
            }
        }
    }
}
