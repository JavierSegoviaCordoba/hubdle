plugins {
    id("com.javiersc.hubdle") version "0.3.0-SNAPSHOT"
}

hubdle {
    config {
        analysis()
        versioning { enabled(false) }
    }
}
