@file:Suppress("PackageDirectoryMismatch")

import com.javiersc.semver.project.gradle.plugin.SemverExtension

plugins {
    id("com.javiersc.hubdle")
    `java-library`
}

hubdle {
    config {
        versioning {
            semver {
                tagPrefix.set("v")
                semver {
                    version.set("1.0.0")
                }
            }
        }
    }
}

val semver = the<SemverExtension>()

println("semver.tagPrefix: ${semver.tagPrefix.get()}")
println("semver.version: ${semver.version.get()}")
