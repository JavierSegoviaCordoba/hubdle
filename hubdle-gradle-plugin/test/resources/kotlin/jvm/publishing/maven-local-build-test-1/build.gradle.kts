plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing {
            repositories {
                maven {
                    name = "mavenLocalBuildTest"
                    url = rootProject.buildDir.resolve("mavenLocalBuildTest/repository").toURI()
                }
            }
        }

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm()
    }
}
