plugins {
    id("com.javiersc.hubdle.project")
}

hubdle {
    config {
        versioning {
            isEnabled.set(false)
        }
    }

    kotlin {
        jvm {
            features {
                coroutines()
                kotest()
            }
        }
    }
}
