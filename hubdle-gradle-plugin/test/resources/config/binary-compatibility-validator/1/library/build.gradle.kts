plugins {
    id("com.javiersc.hubdle")
}

version = "1.0.0"

hubdle {
    config {
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
