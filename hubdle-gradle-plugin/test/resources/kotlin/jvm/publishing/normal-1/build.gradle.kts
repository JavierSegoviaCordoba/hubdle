plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

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

        jvm()
    }
}
