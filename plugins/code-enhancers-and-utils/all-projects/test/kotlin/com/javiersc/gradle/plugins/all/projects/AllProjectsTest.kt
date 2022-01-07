package com.javiersc.gradle.plugins.all.projects

import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import java.io.File
import kotlin.test.Test

class AllProjectsTest {

    @Test
    fun `all projects all pre-commits enabled 1`() =
        testSandbox(
            sandboxPath = "sandbox-all-projects-all-pre-commits-enabled-1",
            test = { _, testProjectDir ->
                testProjectDir.preCommitFileLines.shouldContain("./gradlew allTests")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew apiCheck")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew assemble")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew spotlessCheck")
            },
        )

    @Test
    fun `all projects all pre-commits enabled except apiCheck 1`() =
        testSandbox(
            sandboxPath = "sandbox-all-projects-all-pre-commits-enabled-except-api-check-1",
            test = { _, testProjectDir ->
                testProjectDir.preCommitFileLines.shouldContain("./gradlew allTests")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew assemble")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew spotlessCheck")
                testProjectDir.preCommitFileLines.shouldNotContain("./gradlew apiCheck")
            },
        )
}

private val File.preCommitFileLines: List<String>
    get() = File("$this/.git/hooks/pre-commit").readLines()
