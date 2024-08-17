@file:Suppress("PackageDirectoryMismatch")

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
