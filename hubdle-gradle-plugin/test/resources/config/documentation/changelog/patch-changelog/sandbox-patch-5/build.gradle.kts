plugins { id("com.javiersc.hubdle") }

allprojects { version = "0.2.0-alpha.46" }

hubdle {
    config {
        documentation { changelog() }

        versioning { enabled(false) }
    }
}
