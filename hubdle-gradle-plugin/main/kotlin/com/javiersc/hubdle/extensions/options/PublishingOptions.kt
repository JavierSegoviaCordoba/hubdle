package com.javiersc.hubdle.extensions.options

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.gradle.project.extensions.isNotSnapshot
import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.HubdleProperty.POM
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.semver.Version
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskCollection
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

internal fun Project.configureMavenPublication(name: String) {
    configure<PublishingExtension> {
        publications { container ->
            container.create<MavenPublication>(name) { from(components[name]) }
        }
    }
}

internal fun Project.configureJavaJarsForAndroidPublishing() {
    configure<LibraryExtension> {
        publishing {
            multipleVariants {
                withSourcesJar()
                withJavadocJar()
                allVariants()
            }
        }
    }
}

internal fun Project.configureJavaJarsForPublishing() {
    the<JavaPluginExtension>().withSourcesJar()
    the<JavaPluginExtension>().withJavadocJar()
}

internal fun Project.configurePublishingExtension() {
    configure<PublishingExtension> {
        publications { container ->
            container.withType<MavenPublication> {
                pom.name.set(getProperty(POM.name))
                pom.description.set(getProperty(POM.description))
                pom.url.set(getProperty(POM.url))
                pom.licenses { pomLicenseSpec ->
                    pomLicenseSpec.license { pomLicense ->
                        pomLicense.name.set(getProperty(POM.licenseName))
                        pomLicense.url.set(getProperty(POM.licenseUrl))
                    }
                }
                pom.developers { pomDeveloperSpec ->
                    pomDeveloperSpec.developer { pomDeveloper ->
                        pomDeveloper.id.set(getProperty(POM.developerId))
                        pomDeveloper.name.set(getProperty(POM.developerName))
                        pomDeveloper.email.set(getProperty(POM.developerEmail))
                    }
                }
                pom.scm { pomScm ->
                    pomScm.url.set(getProperty(POM.scmUrl))
                    pomScm.connection.set(getProperty(POM.scmConnection))
                    pomScm.developerConnection.set(getProperty(POM.scmDeveloperConnection))
                }
            }
        }
    }

    configurePublishOnlySemver()
}

internal fun Project.configureEmptyJavadocs() {
    val emptyJavadocsJarTask: TaskCollection<Jar> = tasks.maybeRegisterLazily("emptyJavadocsJar")
    emptyJavadocsJarTask.configureEach {
        it.group = "build"
        it.description = "Assembles an empty Javadoc jar file for publishing"
        it.archiveClassifier.set("javadoc")
    }
    val emptyJavadocsJar: Jar = emptyJavadocsJarTask.first()
    the<PublishingExtension>().publications.withType<MavenPublication> {
        artifact(emptyJavadocsJar)
    }
}

internal fun Project.configureSigningForPublishing() {
    val shouldSign = project.getPropertyOrNull(HubdleProperty.Publishing.sign)?.toBoolean() ?: true
    if (project.isNotSnapshot && project.isSemver && shouldSign) {
        project.pluginManager.apply(PluginIds.Publishing.signing)
        configure<SigningExtension> { signPublications() }
    }
}

private fun SigningExtension.signPublications() {
    signInMemory()
    sign(project.the<PublishingExtension>().publications)
}

private fun SigningExtension.signInMemory() {
    val keyId = project.getPropertyOrNull(HubdleProperty.Signing.keyId)
    val gnupgKey = project.getPropertyOrNull(HubdleProperty.Signing.gnupgKey)?.replace("\\n", "\n")
    val gnupgPassphrase = project.getPropertyOrNull(HubdleProperty.Signing.gnupgPassphrase)

    when {
        keyId != null && gnupgKey != null && gnupgPassphrase != null -> {
            useInMemoryPgpKeys(keyId, gnupgKey, gnupgPassphrase)
        }
        gnupgKey != null && gnupgPassphrase != null -> {
            useInMemoryPgpKeys(gnupgKey, gnupgPassphrase)
        }
    }
}

private fun Project.configurePublishOnlySemver() {
    val checkIsSemver: TaskCollection<Task> =
        project.tasks.maybeRegisterLazily("checkIsSemver") {
            it.doLast {
                val publishNonSemver = getBooleanProperty(HubdleProperty.Publishing.nonSemver)
                check(isSemver || publishNonSemver) {
                    "Only semantic versions can be published (current: $version)"
                }
            }
        }

    tasks {
        namedLazily<Task>("initializeSonatypeStagingRepository").configureEach {
            it.dependsOn(checkIsSemver)
        }
        namedLazily<Task>("publish").configureEach { it.dependsOn(checkIsSemver) }
        namedLazily<Task>("publishToSonatype").configureEach { it.dependsOn(checkIsSemver) }
        namedLazily<Task>("publishToMavenLocal").configureEach { it.dependsOn(checkIsSemver) }
        namedLazily<Task>("publishPlugins").configureEach { it.dependsOn(checkIsSemver) }
    }
}

private val Project.isSemver: Boolean
    get() = Version.regex.matches("$version")
