plugins { id("com.javiersc.hubdle") }

version = "1.0.0"

hubdle {
    config { versioning { enabled(false) } }

    kotlin { jvm() }
}
