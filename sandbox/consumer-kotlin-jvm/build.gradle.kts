plugins {
    id("com.javiersc.hubdle") version "0.1.1-SNAPSHOT"
}

hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm()
    }
}
