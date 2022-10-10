plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        format {
            isEnabled = false
        }

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        multiplatform {
            features {
                compose()
            }

            common {
                main {
                    dependencies {
                        implementation(compose.runtime)
                        implementation(compose.foundation)
                    }
                }
            }

            jvm()
        }
    }
}
