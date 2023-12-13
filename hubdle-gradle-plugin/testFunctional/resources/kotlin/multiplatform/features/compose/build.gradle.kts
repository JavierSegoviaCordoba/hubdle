@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        format { enabled(false) }

        versioning {
            isEnabled.set(false)
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
