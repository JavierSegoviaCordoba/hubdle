plugins {
    id("com.javiersc.hubdle.project")
}

hubdle {
    config {
        documentation {
            api()
        }
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
