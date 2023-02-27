plugins {
    id("com.javiersc.hubdle.project")
}

version = "1.0.0"

hubdle {
    config {
        format()

        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}