plugins {
    id("com.javiersc.hubdle.project")
}

allprojects {
    version = "1.0.0-alpha.1"
}

hubdle {
    config {
        documentation {
            readme {
                badges {
                    coverage.set(false)
                }
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
