plugins {
    id("com.javiersc.hubdle.project")
}

allprojects { version = "0.1.0" }

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
