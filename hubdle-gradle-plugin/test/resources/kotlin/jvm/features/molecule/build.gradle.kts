plugins {
    id("com.javiersc.hubdle")
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
                     mainClass.set("com.javiersc.kotlin.jvm.molecule.MainKt")
                }
                compose()
                coroutines()
                molecule()
            }
        }
    }
}
