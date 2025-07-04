@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.plugin
import org.gradle.kotlin.dsl.the

plugins {
    id("com.javiersc.hubdle")
}

version = "9.8.3-alpha.4"

hubdle {
    config {
        format {
            isEnabled.set(false)
        }

        publishing()

        versioning {
            isEnabled.set(false)
        }
    }

    gradle {
        versionCatalogs {
            catalog {
                toml(layout.projectDirectory.file("sandbox.libs.versions.toml"))
            }
        }
    }
}

afterEvaluate {
    println("Publications: ${the<PublishingExtension>().publications.map { it.name }}")
}
