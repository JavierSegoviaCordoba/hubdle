import com.android.build.gradle.LibraryExtension
import com.javiersc.plugins.core.isSignificant
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `maven-publish`
    signing
    id("org.jetbrains.dokka")
}

val isAndroidLibrary: Boolean
    get() = project.plugins.hasPlugin("com.android.library")
val isKotlinMultiplatform: Boolean
    get() = project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")
val isVersionCatalog: Boolean
    get() = project.plugins.hasPlugin("org.gradle.version-catalog")

configure<PublishingExtension> {
    publications {
        when {
            isVersionCatalog ->
                create<MavenPublication>("maven") { from(components["versionCatalog"]) }
            isAndroidLibrary && !isKotlinMultiplatform ->
                create<MavenPublication>("release") { from(components["release"]) }
            !isKotlinMultiplatform -> create<MavenPublication>("maven") { from(components["java"]) }
        }

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
                    tasks.creating(Jar::class) {
                        group = "build"
                        description = "Assembles Javadoc jar file from for publishing"
                        archiveClassifier.set("javadoc")
                        dependsOn(tasks.named<DokkaTask>("dokkaHtml"))
                    },
            )

            if (!isKotlinMultiplatform || !isVersionCatalog) {
                val allSource =
                        if (isAndroidLibrary) {
                            (project.extensions.getByName("android") as LibraryExtension)
                                    .sourceSets
                                    .named("main")
                                    .get()
                                    .java
                                    .srcDirs
                        } else {
                            (project.extensions.getByName("sourceSets") as SourceSetContainer)
                                    .named("main")
                                    .get()
                                    .allSource
                        }

                artifact(
                        tasks.creating(Jar::class) {
                            group = "build"
                            description = "Assembles Sources jar file for publishing"
                            archiveClassifier.set("sources")
                            from(allSource)
                        },
                )
            }
        }
    }
}

configure<SigningExtension> {
    if (!project.version.toString().endsWith("-SNAPSHOT") && isSignificant) {
        useGpgCmd()
        sign(extensions.getByName<PublishingExtension>("publishing").publications)
    }
}

rootProject.tasks {
    getByName("initializeSonatypeStagingRepository") { dependsOn("checkIsSignificant") }
}

tasks {
    getByName("publish") { dependsOn("checkIsSignificant") }

    getByName("publishToSonatype") { dependsOn("checkIsSignificant") }

    getByName("publishToMavenLocal") { dependsOn("checkIsSignificant") }
}
