plugins {
    id("com.javiersc.hubdle")
}


hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        gradle {
            versionCatalog()
        }
        jvm()
    }
}
