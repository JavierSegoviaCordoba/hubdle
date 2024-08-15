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
            }
        }
    }
}
