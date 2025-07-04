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
        plugin {
            gradlePlugin {
                plugins {
                    create("sandbox") {
                        id = "com.javiersc.sandbox"
                        displayName = "Sandbox"
                        description = "Sandbox plugin"
                        implementationClass = "com.javiersc.sandbox.SandboxPlugin"
                        tags.set(listOf("sandbox"))
                    }
                }
            }
        }
    }

    kotlin {
        jvm()
    }
}

afterEvaluate {
    println("Publications: ${the<PublishingExtension>().publications.map { it.name }}")
}
