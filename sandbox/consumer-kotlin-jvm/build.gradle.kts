plugins {
    alias(libs.plugins.javiersc.hubdle)
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
