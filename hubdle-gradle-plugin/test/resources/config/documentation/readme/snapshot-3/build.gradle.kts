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
                    kotlin.set(false)
                    mavenCentral.set(false)
                    snapshots.set(false)
                    build.set(false)
                    coverage.set(false)
                    quality.set(false)
                    techDebt.set(false)
                }
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
