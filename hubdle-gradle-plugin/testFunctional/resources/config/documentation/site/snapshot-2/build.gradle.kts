@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

allprojects {
    version = "1.0.1-SNAPSHOT"
}

hubdle {
    config {
        documentation {
            api()
            site {
                analysis {
                    qodana.set(false)
                    sonar.set(false)
                }
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
