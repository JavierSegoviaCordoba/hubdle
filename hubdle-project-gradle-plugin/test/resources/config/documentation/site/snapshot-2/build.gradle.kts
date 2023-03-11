plugins {
    id("com.javiersc.hubdle.project")
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
