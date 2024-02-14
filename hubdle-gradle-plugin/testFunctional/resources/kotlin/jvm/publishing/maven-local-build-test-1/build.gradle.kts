@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing {
            maven {
                repositories {
                    maven {
                        name = "mavenLocalBuildTest"
                        url =
                            rootProject.layout.buildDirectory.asFile
                                .get()
                                .resolve("mavenLocalBuildTest/repository")
                                .toURI()
                    }
                }
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
