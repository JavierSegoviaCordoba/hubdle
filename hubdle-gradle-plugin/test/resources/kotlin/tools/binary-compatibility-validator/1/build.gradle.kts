plugins {
    id("com.javiersc.hubdle")
}

version = "1.0.0"

hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        tools {
            binaryCompatibilityValidator()
        }

        jvm()
    }
}
