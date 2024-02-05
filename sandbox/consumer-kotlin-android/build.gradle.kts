plugins {
    id("com.javiersc.hubdle") version "0.6.0"
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
