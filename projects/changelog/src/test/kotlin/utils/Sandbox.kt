package com.javiersc.gradle.plugins.changelog.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

internal infix fun String.copyResourceTo(destination: File) {
    File(Thread.currentThread().contextClassLoader?.getResource(this)?.toURI()!!)
        .copyRecursively(destination)
}

internal fun createSandboxFile(): File {
    val path: Path = Paths.get("build/sandbox").apply { toFile().mkdirs() }
    return Files.createTempDirectory(path, null).toFile()
}

internal val File.arguments: List<String>
    get() =
        File("$this/ARGUMENTS.txt").readLines().first().split(" ", limit = 3).map { argument ->
            argument.replace("\"", "")
        }

internal val File.changelog: File
    get() = File("$this/CHANGELOG.md")

internal val File.changelogActual: File
    get() = File("$this/CHANGELOG_ACTUAL.md")
