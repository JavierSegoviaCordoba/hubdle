@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        format {
            isEnabled.set(false)
        }
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
