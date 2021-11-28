@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.publish.internal

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType

fun Project.configureMavenPublication(
    artifacts: List<Jar>,
    components: Map<String, String> = emptyMap(),
) {
    val publishingExtension: PublishingExtension? = extensions.findByType()

    if (publishingExtension == null) {
        warningMessage("`maven-publish` plugin is not being applied")
    } else {
        configure<PublishingExtension> {
            publications { container ->
                for ((name, component) in components) {
                    container.create<MavenPublication>(name) {
                        from(this@configureMavenPublication.components[component])
                    }
                }

                container.withType<MavenPublication> {
                    artifacts.forEach(::artifact)

                    pom { pom ->
                        pom.name.set(pomName)
                        pom.description.set(pomDescription)
                        pom.url.set(pomUrl)

                        pom.licenses { licenses ->
                            licenses.license { license ->
                                license.name.set(pomLicenseName)
                                license.name.set(pomLicenseUrl)
                            }
                        }

                        pom.developers { developers ->
                            developers.developer { developer ->
                                developer.id.set(pomDeveloperId)
                                developer.name.set(pomDeveloperName)
                                developer.email.set(pomDeveloperEmail)
                            }
                        }

                        pom.scm { smc ->
                            smc.url.set(pomSmcUrl)
                            smc.connection.set(pomSmcConnection)
                            smc.developerConnection.set(pomSmcDeveloperConnection)
                        }
                    }
                }
            }
        }
    }
}

val Project.pomName: String?
    get() = getVariable("pom.name", "POM_NAME")

val Project.pomDescription: String?
    get() = getVariable("pom.description", "POM_DESCRIPTION")

val Project.pomUrl: String?
    get() = getVariable("pom.url", "POM_URL")

val Project.pomLicenseName: String?
    get() = getVariable("pom.license.name", "POM_LICENSE_NAME")

val Project.pomLicenseUrl: String?
    get() = getVariable("pom.license.url", "POM_LICENSE_URL")

val Project.pomDeveloperId: String?
    get() = getVariable("pom.developer.id", "POM_DEVELOPER_ID")

val Project.pomDeveloperName: String?
    get() = getVariable("pom.developer.name", "POM_DEVELOPER_NAME")

val Project.pomDeveloperEmail: String?
    get() = getVariable("pom.developer.email", "POM_DEVELOPER_EMAIL")

val Project.pomSmcUrl: String?
    get() = getVariable("pom.smc.url", "POM_SMC_URL")

val Project.pomSmcConnection: String?
    get() = getVariable("pom.smc.connection", "POM_SMC_CONNECTION")

val Project.pomSmcDeveloperConnection: String?
    get() = getVariable("pom.smc.developerConnection", "POM_SMC_DEVELOPER_CONNECTION")
