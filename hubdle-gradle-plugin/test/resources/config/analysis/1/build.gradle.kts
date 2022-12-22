plugins { id("com.javiersc.hubdle") }

version = "1.0.0"

hubdle {
    config {
        analysis()

        format { enabled(false) }

        versioning { enabled(false) }
    }

    kotlin { jvm() }
}
