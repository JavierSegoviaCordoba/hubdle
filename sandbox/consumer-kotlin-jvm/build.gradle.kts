plugins {
    id("com.javiersc.hubdle") version "0.3.0-SNAPSHOT"
}

hubdle {
    config {
        versioning {
            enabled(false)
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
