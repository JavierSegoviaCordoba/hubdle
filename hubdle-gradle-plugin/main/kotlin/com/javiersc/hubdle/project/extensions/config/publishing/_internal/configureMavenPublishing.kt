package com.javiersc.hubdle.project.extensions.config.publishing._internal

import com.javiersc.gradle.project.extensions.isNotSnapshot
import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.hubdle.project.HubdleProperty
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.api.tasks.TaskCollection
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

internal fun HubdleConfigurableExtension.configurableMavenPublishing(
    mavenPublicationName: String? = null,
    configJavaExtension: Boolean = false,
    configEmptyJavaDocs: Boolean = false,
    additionalConfig: Configurable.() -> Unit = {},
) =
    with(project) {
        applicablePlugin(
            isEnabled = property { isEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.MavenPublish,
        )
        applicablePlugin(
            isEnabled = property { isEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.GradleSigning,
        )
        configurable(
            isEnabled = property { isEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
        ) {
            configurePublishingExtension()
            configureSigningForPublishing()
            if (configJavaExtension) {
                configure<JavaPluginExtension> {
                    withSourcesJar()
                    withJavadocJar()
                }
            }
            if (mavenPublicationName != null) {
                configureMavenPublication(mavenPublicationName)
            }
            if (configEmptyJavaDocs) {
                configureEmptyJavadocs()
            }
            additionalConfig()
        }
    }

private fun HubdleConfigurableExtension.configurePublishingExtension() =
    with(project) {
        configure<PublishingExtension> {
            configureRepositories(this)

            publications { container ->
                container.withType<MavenPublication> {
                    pom.name.set(getProperty(HubdleProperty.POM.name))
                    pom.description.set(getProperty(HubdleProperty.POM.description))
                    pom.url.set(getProperty(HubdleProperty.POM.url))
                    pom.licenses { licenses ->
                        licenses.license { license ->
                            license.name.set(getProperty(HubdleProperty.POM.licenseName))
                            license.url.set(getProperty(HubdleProperty.POM.licenseUrl))
                        }
                    }
                    pom.developers { developers ->
                        developers.developer { developer ->
                            developer.id.set(getProperty(HubdleProperty.POM.developerId))
                            developer.name.set(getProperty(HubdleProperty.POM.developerName))
                            developer.email.set(getProperty(HubdleProperty.POM.developerEmail))
                        }
                    }
                    pom.scm { scm ->
                        scm.url.set(getProperty(HubdleProperty.POM.scmUrl))
                        scm.connection.set(getProperty(HubdleProperty.POM.scmConnection))
                        scm.developerConnection.set(
                            getProperty(HubdleProperty.POM.scmDeveloperConnection)
                        )
                    }
                }
            }
        }

        configurePublishOnlySemver()
    }

private fun HubdleConfigurableExtension.configurePublishOnlySemver() =
    with(project) {
        val checkIsSemver: TaskCollection<Task> =
            tasks.maybeRegisterLazily("checkIsSemver") { task ->
                task.enabled = isTagPrefixProject
                task.doLast {
                    val publishNonSemver = getBooleanProperty(HubdleProperty.Publishing.nonSemver)
                    check(isSemver || publishNonSemver) {
                        // TODO: inject `$version` instead of getting it from the `project`
                        """|Only semantic versions can be published (current: $version)
                           |Use `"-Ppublishing.nonSemver=true"` to force the publication 
                        """
                            .trimMargin()
                    }
                }
            }

        tasks.namedLazily<Task>("initializeSonatypeStagingRepository").configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }
        tasks.namedLazily<Task>("publish").configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }
        tasks.withType<PublishToMavenLocal>().configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }
        tasks.namedLazily<Task>("publishToSonatype").configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }
        tasks.withType<PublishToMavenRepository>().configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }
        tasks.namedLazily<Task>("publishToMavenLocal").configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }
        tasks.namedLazily<Task>("publishPlugins").configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(checkIsSemver)
        }

        tasks
            .withType()
            .matching { task ->
                task.name.startsWith("publishTo") && task.name.endsWith("ToSonatypeRepository")
            }
            .configureEach { task ->
                task.enabled = isTagPrefixProject
                task.dependsOn(checkIsSemver)
            }
    }

private val Project.isSemver: Boolean
    get() = GradleVersion.regex.matches("$version")

private fun HubdleConfigurableExtension.configureRepositories(
    publishingExtension: PublishingExtension
) =
    with(project) {
        publishingExtension.repositories.configureEach { repository ->
            when (repository.name) {
                "mavenLocalTest" -> {
                    val childTask =
                        tasks.namedLazily<Task>("publishAllPublicationsToMavenLocalTestRepository")
                    tasks.maybeRegisterLazily<Task>("publishToMavenLocalTest").configureEach { task
                        ->
                        task.group = "publishing"
                        task.dependsOn(childTask)
                    }
                }
                "mavenLocalBuildTest" -> {
                    val childTask =
                        tasks.namedLazily<Task>(
                            "publishAllPublicationsToMavenLocalBuildTestRepository"
                        )
                    tasks.maybeRegisterLazily<Task>("publishToMavenLocalBuildTest").configureEach {
                        task ->
                        task.group = "publishing"
                        task.dependsOn(childTask)
                    }
                }
            }
        }
    }

private fun HubdleConfigurableExtension.configureMavenPublication(name: String) =
    with(project) {
        configure<PublishingExtension> {
            publications { container ->
                container.register<MavenPublication>(name) { from(components[name]) }
            }
        }
    }

private fun HubdleConfigurableExtension.configureSigningForPublishing() =
    with(project) {
        configure<SigningExtension> {
            val allTasks: List<String> = gradle.startParameter.taskRequests.flatMap { it.args }
            val hasPublishTask = allTasks.any { it.startsWith("publish") }
            val hasPublishToMavenLocalTasks =
                allTasks.any {
                    it == "publishToMavenLocal" ||
                        it == "publishToMavenLocalTest" ||
                        it == "publishToMavenLocalBuildTest" ||
                        it == "publishAllPublicationsToMavenLocalTestRepository" ||
                        it == "publishAllPublicationsToMavenLocalBuildTestRepository"
                }
            val shouldSign = getPropertyOrNull(HubdleProperty.Publishing.sign)?.toBoolean() ?: false

            val hasTaskCondition = (hasPublishTask && !hasPublishToMavenLocalTasks)
            val hasSemverCondition = project.isNotSnapshot.get() && project.isSemver

            isRequired = (hasTaskCondition && hasSemverCondition) || shouldSign

            if (isRequired) {
                signInMemory()
                sign(project.the<PublishingExtension>().publications)
            }
        }
        val signingTasks: TaskCollection<Sign> = tasks.withType<Sign>()
        tasks.withType<PublishToMavenRepository>().configureEach { task ->
            task.mustRunAfter(signingTasks)
        }
        tasks.withType<PublishToMavenLocal>().configureEach { task ->
            task.mustRunAfter(signingTasks)
        }
    }

private fun SigningExtension.signInMemory() =
    with(project) {
        val keyId = project.getPropertyOrNull(HubdleProperty.Signing.keyId)
        val gnupgKey =
            project.getPropertyOrNull(HubdleProperty.Signing.gnupgKey)?.replace("\\n", "\n")
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

private fun HubdleConfigurableExtension.configureEmptyJavadocs() =
    with(project) {
        val emptyJavadocsJar: Jar = tasks.maybeCreate("emptyJavadocsJar", Jar::class)
        emptyJavadocsJar.apply {
            group = "build"
            description = "Assembles an empty Javadoc jar file for publishing"
            archiveClassifier.set("javadoc")
        }
        the<PublishingExtension>().publications.withType<MavenPublication> {
            artifact(emptyJavadocsJar)
        }
    }
