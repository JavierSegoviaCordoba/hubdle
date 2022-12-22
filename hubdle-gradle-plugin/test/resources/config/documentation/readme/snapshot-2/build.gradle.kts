plugins { id("com.javiersc.hubdle") }

allprojects { version = "1.0.0-alpha.1" }

hubdle {
    config {
        documentation {
            readme {
                badges {
                    mavenCentral.set(false)
                    quality.set(false)
                }
            }
        }

        versioning { enabled(false) }
    }
}
