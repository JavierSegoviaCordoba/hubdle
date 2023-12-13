@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing(enabled = provider { "$version".contains("beta") })

        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
