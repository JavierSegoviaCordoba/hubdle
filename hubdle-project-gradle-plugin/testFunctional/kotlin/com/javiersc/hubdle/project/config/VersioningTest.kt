package com.javiersc.hubdle.project.config

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import kotlin.test.Test

internal class VersioningTest : GradleTestKitTest() {

    @Test
    fun `given a project with versioning with semver enabled when it builds then SemverExtension exists`() {
        gradleTestKitTest("config/versioning/semver-1") {
            gradlew("assemble").output.contains("semver.tagPrefix: v")
        }
    }
}
