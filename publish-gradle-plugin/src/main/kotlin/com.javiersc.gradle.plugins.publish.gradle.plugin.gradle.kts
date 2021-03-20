import com.javiersc.plugins.core.isSignificant
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `maven-publish`
    id("com.gradle.plugin-publish")
    signing
    id("org.jetbrains.dokka")
}

configure<PublishingExtension> {
    publications {
        withType<MavenPublication> {
            pom {
                name.set(property("pomName").toString())
                description.set(property("pomDescription").toString())
                url.set(property("pomUrl").toString())

                licenses {
                    license {
                        name.set(property("pomLicenseName").toString())
                        url.set(property("pomLicenseUrl").toString())
                    }
                }

                developers {
                    developer {
                        id.set(property("pomDeveloperId").toString())
                        name.set(property("pomDeveloperName").toString())
                        email.set(property("pomDeveloperEmail").toString())
                    }
                }

                scm {
                    url.set(property("pomSmcUrl").toString())
                    connection.set(property("pomSmcConnection").toString())
                    developerConnection.set(property("pomSmcDeveloperConnection").toString())
                }
            }

            artifact(
                project.tasks.creating(Jar::class) {
                    group = "build"
                    description = "Assembles Sources jar file from for publishing"
                    archiveClassifier.set("sources")
                    from((project.properties["sourceSets"] as SourceSetContainer)["main"].allSource)
                }
            )

            artifact(
                project.tasks.creating(Jar::class) {
                    group = "build"
                    description = "Assembles Javadoc jar file from for publishing"
                    archiveClassifier.set("javadoc")
                    dependsOn(tasks.named<DokkaTask>("dokkaHtml"))
                }
            )
        }
    }
}

configure<SigningExtension> {
    if (!project.version.toString().endsWith("-SNAPSHOT") && isSignificant) {
        useGpgCmd()
        sign(extensions.getByName<PublishingExtension>("publishing").publications)
    }
}

pluginBundle {
    website = property("pomSmcUrl").toString()
    vcsUrl = property("pomSmcConnection").toString()
}

val checkIsSignificant: Task by project.tasks.creating() {
    if (!isSignificant) {
        error("Only significant versions can be published (current: $version)")
    }
}

rootProject.tasks {
    getByName("initializeSonatypeStagingRepository") { dependsOn(checkIsSignificant) }
}

tasks {
    getByName("publish") { dependsOn(checkIsSignificant) }

    getByName("publishToSonatype") { dependsOn(checkIsSignificant) }

    getByName("publishToMavenLocal") { dependsOn(checkIsSignificant) }
}
