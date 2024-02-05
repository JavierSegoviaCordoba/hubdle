plugins {
    id("com.javiersc.hubdle") version "0.6.0"
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
                application {
                    application {
                        mainClass.set("com.javiersc.hubdle.kotlin.jvm.features.molecule.MainKt")
                    }
                }
                coroutines()
            }
        }
    }
}
