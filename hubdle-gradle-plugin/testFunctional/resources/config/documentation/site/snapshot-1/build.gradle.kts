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
            site()
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
