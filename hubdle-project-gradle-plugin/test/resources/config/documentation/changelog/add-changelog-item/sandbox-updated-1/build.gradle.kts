plugins {
    id("com.javiersc.hubdle.project")
}

hubdle {
    config {
        documentation {
            changelog()
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
