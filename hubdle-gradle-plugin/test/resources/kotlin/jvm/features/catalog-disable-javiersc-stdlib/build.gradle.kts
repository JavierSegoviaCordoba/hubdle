plugins { id("com.javiersc.hubdle") }

hubdle {
    config { versioning { enabled(false) } }

    kotlin { jvm { features { extendedStdlib.isEnabled.set(false) } } }
}
