plugins {
    id("com.javiersc.hubdle")
}

allprojects {
    version = "1.0.1-SNAPSHOT"
}

hubdle {
    config {
        documentation {
            site()
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
