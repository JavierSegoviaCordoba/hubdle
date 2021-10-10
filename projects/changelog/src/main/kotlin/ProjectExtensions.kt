import java.io.File
import org.gradle.api.Project

val Project.changelogFile: File
    get() = file("$projectDir/CHANGELOG.md")
