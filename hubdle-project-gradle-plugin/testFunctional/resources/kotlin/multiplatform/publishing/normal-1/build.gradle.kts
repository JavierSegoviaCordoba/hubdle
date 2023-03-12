plugins {
    id("com.javiersc.hubdle.project")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing()

        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        multiplatform {
            android()
            jvm()
        }
    }
}
