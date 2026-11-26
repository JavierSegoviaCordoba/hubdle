@file:Suppress("PackageDirectoryMismatch")

plugins { //
    id("com.javiersc.hubdle")
}

version = "1.2.3"

hubdle {
    config {
        publishing()

        versioning { //
            isEnabled.set(false)
        }
    }

    kotlin {
        compilerPlugin {
            ideaVersionsEnabled(false)
            componentRegistrar("com.kotlin.devkit.DevKitPluginRegistrar")
            commandLineProcessor("com.kotlin.devkit.DevKitPluginCLP")
        }
    }
}
