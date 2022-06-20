plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm {
            features {
                javierScStdlib(enabled = false)
            }
        }
    }
}
