plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing()

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        multiplatform {
            android()
            jvm()
        }
    }
}
