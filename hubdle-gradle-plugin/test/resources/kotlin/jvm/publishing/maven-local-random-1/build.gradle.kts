plugins { id("com.javiersc.hubdle") }

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing {
            repositories {
                maven {
                    name = "mavenLocalRandom"
                    url = rootProject.buildDir.resolve("mavenLocalRandom/repository").toURI()
                }
            }
        }

        versioning { enabled(false) }
    }

    kotlin { jvm() }
}
