import com.javiersc.semver.project.gradle.plugin.SemverExtension

plugins {
    id("com.javiersc.hubdle.project")
    `java-library`
}

hubdle {
    config {
        versioning()
    }
}

val semver = the<SemverExtension>()

semver.tagPrefix.set("v")

println("semver.tagPrefix: ${semver.tagPrefix.get()}")
