import java.io.File
import org.gradle.api.Project

val Project.changelogFile: File
    get() = file("$projectDir/CHANGELOG.md")

fun String.containVersion(version: String): Boolean =
    contains("## [$version-") || contains("## [$version]")
