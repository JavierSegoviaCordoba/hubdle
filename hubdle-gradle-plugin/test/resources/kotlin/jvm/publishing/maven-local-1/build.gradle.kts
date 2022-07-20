import org.gradle.api.publish.PublishingExtension

plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        publishing()

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm()
    }
}
