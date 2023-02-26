plugins {
    id("com.javiersc.hubdle.project")
}

version = "1.0.0"

hubdle {
    config {
        analysis()

        format {
            isEnabled.set(false)
        }

        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm()
    }
}
