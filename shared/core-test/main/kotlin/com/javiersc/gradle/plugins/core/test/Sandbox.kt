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

val sandboxPath: Path = Paths.get("build/sandbox").apply { toFile().mkdirs() }
val sandboxCachePath: Path = Paths.get("build/sandbox-cache").apply { toFile().mkdirs() }

val File.sandboxFile: File
    get() = File("$this/build/sandbox")

fun getResource(resource: String): File =
    File(Thread.currentThread().contextClassLoader?.getResource(resource)?.toURI()!!)

infix fun String.copyResourceTo(destination: File) {
    getResource(this).copyRecursively(destination)
}

fun createSandboxFile(prefix: String): File =
    Files.createTempDirectory(sandboxPath, "$prefix-").toFile()

fun createSandboxCacheFile(prefix: String): File =
    Files.createTempDirectory(sandboxCachePath, "$prefix-").toFile()

val File.arguments: List<String>
    get() =
        File("$this/ARGUMENTS.txt").readLines().first().split(" ", limit = 3).map { argument ->
            argument.replace("\"", "")
        }

val File.taskFromArguments: String
    get() =
        File("$this/ARGUMENTS.txt")
            .readLines()
            .first()
            .split(" ", limit = 3)
            .map { argument -> argument.replace("\"", "") }
            .first()

fun testSandbox(
    sandboxPath: String,
    prefix: String = sandboxPath.split("/").last(),
    beforeTest: File.(GradleRunner) -> Unit = {},
    taskOutcome: TaskOutcome = TaskOutcome.SUCCESS,
    isBuildCacheTest: Boolean = false,
    test: (result: BuildResult, testProjectDir: File) -> Unit,
): Pair<GradleRunner, File> {
    val testProjectDir: File = createSandboxFile(prefix)
    val testProjectCacheDir: File? = if (isBuildCacheTest) createSandboxCacheFile(prefix) else null

    sandboxPath copyResourceTo testProjectDir

    val runner =
        GradleRunner.create().apply {
            withDebug(true)
            withProjectDir(testProjectDir)
            if (isBuildCacheTest) withTestKitDir(testProjectCacheDir)
            withPluginClasspath()
        }

    beforeTest(testProjectDir, runner)

    runner.withArguments(testProjectDir.arguments).build().run {
        checkArgumentsTasks(testProjectDir, taskOutcome)
        test(this, testProjectDir)
    }

    return runner to testProjectDir
}

fun BuildResult.checkArgumentsTasks(testProjectDir: File, taskOutcome: TaskOutcome) {
    val executedTaskName = ":${testProjectDir.arguments.first()}"
    val task = tasks.first { task -> task.path.endsWith(executedTaskName) }
    task.outcome.shouldBe(taskOutcome)
}

fun File.commitAndCheckout(message: String, branch: String = "sandbox/gradle-plugins") {
    val git: Git = Git.init().setDirectory(this).call()
    git.add().addFilepattern(".").call()
    git.commit().setMessage(message).call()
    git.checkout().setCreateBranch(true).setName(branch).call()
}
