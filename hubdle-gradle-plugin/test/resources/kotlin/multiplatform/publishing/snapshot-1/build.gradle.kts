plugins { id("com.javiersc.hubdle") }

version = "3.6.7-SNAPSHOT"

hubdle {
    config {
        publishing()

        versioning { enabled(false) }
    }

    kotlin {
        multiplatform {
            android()
            jvm()
        }
    }
}
