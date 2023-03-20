plugins {
    id("com.javiersc.hubdle")
}

allprojects { version = "0.1.1" }

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
