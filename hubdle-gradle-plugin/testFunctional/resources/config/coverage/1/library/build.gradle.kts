@file:Suppress("PackageDirectoryMismatch")

version = "1.0.0"

hubdle {
    config {
        coverage()

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
