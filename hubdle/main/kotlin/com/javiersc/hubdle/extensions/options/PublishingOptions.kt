package com.javiersc.hubdle.extensions.options

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.PropertyKey.POM
import com.javiersc.hubdle.properties.getProperty
import com.javiersc.hubdle.properties.getPropertyOrNull
import com.javiersc.hubdle.properties.isNotSnapshot
import com.javiersc.semver.Version
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

public interface PublishingOptions {

    public fun Project.publishing()
}

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
            container.withType<MavenPublication>() {
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
}

internal fun Project.configureSigningForPublishing() {
    configure<SigningExtension>() { signPublications() }
}

private fun SigningExtension.signPublications() {
    if (project.isNotSnapshot && project.isSignificant) {
        try {
            signInMemory()
        } catch (_: Throwable) {
            useGpgCmd()
        }
        sign(project.the<PublishingExtension>().publications)
    }
}

private fun SigningExtension.signInMemory() {
    val keyId = project.getPropertyOrNull(PropertyKey.Signing.keyId)
    val gnupgKey = project.getProperty(PropertyKey.Signing.gnupgKey)
    val gnupgPassphrase = project.getProperty(PropertyKey.Signing.gnupgPassphrase)

    if (keyId != null) useInMemoryPgpKeys(keyId, gnupgKey, gnupgPassphrase)
    else useInMemoryPgpKeys(gnupgKey, gnupgPassphrase)
}

private val Project.isSignificant: Boolean
    get() = Version.regex.matches("$version")

// TODO: ???/
private val Project.isSignificantProperty: Boolean
    get() = getPropertyOrNull("publish.significant")?.toBoolean() ?: true
