plugins {
    id("com.javiersc.hubdle")
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
