plugins {
    id("com.javiersc.hubdle")
}

version = "3.6.7-SNAPSHOT"

hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        tools {
            publishing()
        }

        multiplatform {
            android()
            jvm()
        }
    }
}
