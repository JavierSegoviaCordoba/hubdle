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
                jvmVersion(JavaVersion.VERSION_11)
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
