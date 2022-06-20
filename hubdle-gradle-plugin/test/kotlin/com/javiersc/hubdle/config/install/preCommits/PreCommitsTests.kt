package com.javiersc.hubdle.config.install.preCommits

import com.javiersc.gradle.testkit.test.extensions.cleanBuildDirectory
import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.gradlewArgumentFromTXT
import com.javiersc.gradle.testkit.test.extensions.testConfigurationCache
import com.javiersc.gradle.testkit.test.extensions.withArgumentsFromTXT
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import kotlin.test.Test
import org.gradle.testkit.runner.GradleRunner

class PreCommitsTests {

    private val basePath = "config/install/pre-commits"

    @Test
    fun `all projects all pre-commits enabled 1`() {
        gradleTestKitTest("$basePath/enabled-1") {
            gradlewArgumentFromTXT()
            preCommitFileLines.shouldContain("./gradlew allTests")
            preCommitFileLines.shouldContain("./gradlew applyFormat")
            preCommitFileLines.shouldContain("./gradlew assemble")
            preCommitFileLines.shouldContain("./gradlew checkAnalysis")
            preCommitFileLines.shouldContain("./gradlew checkApi")
            preCommitFileLines.shouldContain("./gradlew checkFormat")
            preCommitFileLines.shouldContain("./gradlew dumpApi")
            preCommitFileLines.shouldHaveSize(22)
        }
    }

    @Test
    fun `all projects all pre-commits enabled 1 configuration cache`() {
        gradleTestKitTest("$basePath/enabled-configuration-cache-1", isolated = true) {
            withArgumentsFromTXT()
            build()
            preCommitFileLines.shouldContain("./gradlew allTests")
            preCommitFileLines.shouldContain("./gradlew applyFormat")
            preCommitFileLines.shouldContain("./gradlew assemble")
            preCommitFileLines.shouldContain("./gradlew checkAnalysis")
            preCommitFileLines.shouldContain("./gradlew checkApi")
            cleanBuildDirectory()
            testConfigurationCache()
        }
    }

    @Test
    fun `all projects all pre-commits enabled except checkApi 1`() {
        gradleTestKitTest("$basePath/enabled-except-api-check-1") {
            gradlewArgumentFromTXT()
            preCommitFileLines.shouldContain("./gradlew allTests")
            preCommitFileLines.shouldContain("./gradlew applyFormat")
            preCommitFileLines.shouldContain("./gradlew assemble")
            preCommitFileLines.shouldContain("./gradlew checkAnalysis")
            preCommitFileLines.shouldNotContain("./gradlew checkApi")
        }
    }

    private val GradleRunner.preCommitFileLines: List<String>
        get() = projectDir.resolve(".git/hooks/pre-commit").readLines()
}
