plugins {
    id("com.javiersc.hubdle")
}

version = "1.0.0"

hubdle {
    config {
        analysis()

        format {
            isEnabled = false
        }

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm()
    }
}
