plugins {
    id("com.javiersc.hubdle") version "0.3.1-SNAPSHOT"
}

hubdle {
    config {
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm {
            main {
                dependencies {
                    implementation(pluginLibs.javiersc.semver.semverCore)
                }
            }
        }
    }
}
