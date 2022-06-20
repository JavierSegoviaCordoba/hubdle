plugins {
    id("com.javiersc.hubdle")
}

allprojects {
    version = "1.0.0-alpha.1"
}

hubdle {
    config {
        documentation {
            readme {
                badges {
                    kotlin = false
                    mavenCentral = false
                    snapshots = false
                    build = false
                    coverage = false
                    quality = false
                    techDebt = false
                }
            }
        }

        versioning {
            isEnabled = false
        }
    }
}
