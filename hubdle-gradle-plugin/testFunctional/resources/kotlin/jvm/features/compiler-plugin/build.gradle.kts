@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm {
            features {
                compiler()
                extendedStdlib.isEnabled.set(false)
            }
        }
    }
}
