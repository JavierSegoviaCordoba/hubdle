@file:Suppress("PackageDirectoryMismatch")

plugins { //
    id("com.javiersc.hubdle")
}

allprojects { //
    version = "1.2.3"
}

hubdle {
    config {
        versioning { //
            isEnabled.set(false)
        }
    }
}
