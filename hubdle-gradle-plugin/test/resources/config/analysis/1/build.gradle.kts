plugins {
    id("com.javiersc.hubdle")
}

version = "1.0.0"

hubdle {
    config {
        analysis()

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm()
    }
}
