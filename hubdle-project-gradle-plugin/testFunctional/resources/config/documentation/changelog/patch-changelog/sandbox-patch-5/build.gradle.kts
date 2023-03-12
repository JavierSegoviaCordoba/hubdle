plugins {
    id("com.javiersc.hubdle.project")
}

allprojects { version = "0.2.0-alpha.46" }

hubdle {
    config {
        documentation {
            changelog()
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
