@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

version = "1.0.0"

hubdle {
    config {
        binaryCompatibilityValidator()

        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
