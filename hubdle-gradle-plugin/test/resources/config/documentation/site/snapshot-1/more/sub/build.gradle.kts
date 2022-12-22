plugins { id("com.javiersc.hubdle") }

hubdle {
    config { versioning { enabled(false) } }

    kotlin { jvm() }
}
