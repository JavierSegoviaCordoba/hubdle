plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        android {
            library {
                main {
                    dependencies {
                        implementation(pluginLibs.javiersc.semver.semverCore)
                    }
                }
            }
        }
    }
}
