package com.javiersc.gradle.plugins.core.test

import io.kotest.matchers.shouldBe
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.eclipse.jgit.api.Git
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

infix fun String.copyResourceTo(destination: File) {
    File(Thread.currentThread().contextClassLoader?.getResource(this)?.toURI()!!)
        .copyRecursively(destination)
}

fun createSandboxFile(prefix: String): File {
    val path: Path = Paths.get("build/sandbox").apply { toFile().mkdirs() }
    return Files.createTempDirectory(path, "$prefix-").toFile()
}

val File.arguments: List<String>
    get() =
        File("$this/ARGUMENTS.txt").readLines().first().split(" ", limit = 3).map { argument ->
            argument.replace("\"", "")
        }

fun testSandbox(
    sandboxPath: String,
    prefix: String = sandboxPath.split("/").last(),
    beforeTest: File.() -> Unit = {},
    test: (result: BuildResult, testProjectDir: File) -> Unit,
) {
    val testProjectDir: File = createSandboxFile(prefix)
    sandboxPath copyResourceTo testProjectDir

    beforeTest(testProjectDir)

    GradleRunner.create()
        .withProjectDir(testProjectDir)
        .withArguments(testProjectDir.arguments)
        .withPluginClasspath()
        .build()
        .run {
            checkArgumentsTasks(testProjectDir)
            test(this, testProjectDir)
        }
}

fun BuildResult.checkArgumentsTasks(testProjectDir: File) {
    task(":${testProjectDir.arguments.first()}")?.outcome.shouldBe(TaskOutcome.SUCCESS)
}

fun File.commitAndCheckout(message: String, branch: String = "sandbox/gradle-plugins") {
    val git: Git = Git.init().setDirectory(this).call()
    git.add().addFilepattern(".").call()
    git.commit().setMessage(message).call()
    git.checkout().setCreateBranch(true).setName(branch).call()
}
