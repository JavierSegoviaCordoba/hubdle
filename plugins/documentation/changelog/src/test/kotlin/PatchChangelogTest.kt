package com.javiersc.gradle.plugins.changelog

import com.javiersc.gradle.plugins.changelog.utils.arguments
import com.javiersc.gradle.plugins.changelog.utils.changelog
import com.javiersc.gradle.plugins.changelog.utils.changelogActual
import com.javiersc.gradle.plugins.changelog.utils.copyResourceTo
import com.javiersc.gradle.plugins.changelog.utils.createSandboxFile
import io.kotest.matchers.file.shouldHaveSameContentAs
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Test

class PatchChangelogTest {

    @Test fun `patch 1`() = testSandbox("sandbox-patch-1")
}

private fun testSandbox(name: String) {
    val testProjectDir: File = createSandboxFile(name)
    "patch-changelog/$name" copyResourceTo testProjectDir

    with(testProjectDir) {
        val result =
            GradleRunner.create()
                .withProjectDir(this)
                .withArguments(arguments)
                .withPluginClasspath()
                .build()

        result.task(":patchChangelog").shouldNotBeNull().outcome shouldBe TaskOutcome.SUCCESS

        changelogActual.writeText(
            changelogActual
                .readText()
                .replace("{{ PLACEHOLDER_DATE }}", SimpleDateFormat("yyyy-MM-dd").format(Date()))
        )

        changelog.shouldHaveSameContentAs(changelogActual)
    }
}
