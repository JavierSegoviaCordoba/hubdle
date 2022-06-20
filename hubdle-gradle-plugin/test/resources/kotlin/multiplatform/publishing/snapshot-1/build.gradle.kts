plugins {
    id("com.javiersc.hubdle")
}

version = "3.6.7-SNAPSHOT"

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
