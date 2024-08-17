package com.javiersc.hubdle.project.config

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import kotlin.test.Test

internal class VersioningTest : GradleTestKitTest() {

    @Test
    fun `given a project with semver enabled when it builds then tag prefix is v`() {
        gradleTestKitTest("config/versioning/semver") {
            projectDir.resolve("_git").renameTo(projectDir.resolve(".git"))
            gradlew("assemble", "--no-scan").output.apply {
                contains("semver for versioning-semver-project: v1.0.0")
            }
        }
    }
}
