package com.javiersc.hubdle.project.extensions.config.publishing.maven

import PUBLISH_ALL_TO_MAVEN_LOCAL_TEST
import PUBLISH_TO_MAVEN_LOCAL_TEST
import com.javiersc.gradle.tasks.extensions.maybeNamed
import com.javiersc.gradle.tasks.extensions.maybeRegister
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.tasks.CheckIsSemverTask
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import com.javiersc.hubdle.project.extensions.shared.features.gradle.hubdleGradlePluginFeature
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

@HubdleDslMarker
public open class HubdleConfigPublishingMavenExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdlePublishing)

    public val pom: HubdleConfigPublishingMavenPomExtension
        get() = hubdlePublishingMavenPom

    @HubdleDslMarker
    public fun pom(action: Action<HubdleConfigPublishingMavenPomExtension> = Action {}) {
        pom.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun repositories(action: Action<RepositoryHandler> = Action {}) {
        lazyConfigurable { action.execute(the<PublishingExtension>().repositories) }
    }

    @HubdleDslMarker
    public fun publishing(action: Action<PublishingExtension> = Action {}): Unit =
        fallbackAction(action)
}

internal fun HubdleConfigurableExtension.configurableMavenPublishing(
    mavenPublicationName: String? = null,
    configJavaExtension: Boolean = false,
    configEmptyJavaDocs: Boolean = false,
    additionalConfig: Configurable.() -> Unit = {},
) {
    val isEnabled: Property<Boolean> = property {
        isEnabled.get() && hubdlePublishingMaven.isFullEnabled.get()
    }
    applicablePlugin(
        isEnabled = isEnabled,
        scope = Scope.CurrentProject,
        pluginId = PluginId.MavenPublish,
    )
    lazyConfigurable(isEnabled = isEnabled) {
        configurePublishingExtension()
        if (configJavaExtension) {
            configure<JavaPluginExtension> {
                withSourcesJar()
                withJavadocJar()
            }
        }
        val isNotGradlePlugin: Boolean = hubdleGradlePluginFeature.isFullEnabled.orNull == false
        if (isNotGradlePlugin && mavenPublicationName != null) {
            configureMavenPublication(mavenPublicationName)
        }
        if (configEmptyJavaDocs) {
            configureEmptyJavadocs()
        }
        additionalConfig()
    }
}

private fun HubdleConfigurableExtension.configurePublishingExtension() {
    configure<PublishingExtension> {
        configureRepositories(this)

        publications { container ->
            container.withType<MavenPublication>().configureEach { publication ->
                val pom = publication.pom
                pom.name.set(hubdlePublishingMavenPom.name)
                pom.description.set(hubdlePublishingMavenPom.description)
                pom.url.set(hubdlePublishingMavenPom.url)
                pom.licenses { licenses ->
                    licenses.license { license ->
                        license.name.set(hubdlePublishingMavenPom.licenseName)
                        license.url.set(hubdlePublishingMavenPom.licenseUrl)
                    }
                }
                pom.developers { developers ->
                    developers.developer { developer ->
                        developer.id.set(hubdlePublishingMavenPom.developerId)
                        developer.name.set(hubdlePublishingMavenPom.developerName)
                        developer.email.set(hubdlePublishingMavenPom.developerEmail)
                    }
                }
                pom.scm { scm ->
                    scm.url.set(hubdlePublishingMavenPom.scmUrl)
                    scm.connection.set(hubdlePublishingMavenPom.scmConnection)
                    scm.developerConnection.set(hubdlePublishingMavenPom.scmDeveloperConnection)
                }
            }
        }
    }

    configurePublishOnlySemver()
}

private fun HubdleConfigurableExtension.configurePublishOnlySemver() {
    tasks.named("publish").configure { task ->
        task.enabled = isTagPrefixProject
        task.dependsOn(CheckIsSemverTask.NAME)
    }
    tasks.withType<PublishToMavenLocal>().configureEach { task ->
        task.enabled = isTagPrefixProject
        task.dependsOn(CheckIsSemverTask.NAME)
    }
    tasks.maybeNamed("publishToSonatype") { task ->
        task.enabled = isTagPrefixProject
        task.dependsOn(CheckIsSemverTask.NAME)
    }
    tasks.withType<PublishToMavenRepository>().configureEach { task ->
        task.enabled = isTagPrefixProject
        task.dependsOn(CheckIsSemverTask.NAME)
    }
    tasks.named("publishToMavenLocal").configure { task ->
        task.enabled = isTagPrefixProject
        task.dependsOn(CheckIsSemverTask.NAME)
    }

    tasks
        .withType()
        .matching { task ->
            task.name.startsWith("publishTo") && task.name.endsWith("ToSonatypeRepository")
        }
        .configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(CheckIsSemverTask.NAME)
        }
}

private fun HubdleConfigurableExtension.configureRepositories(publishing: PublishingExtension) {
    publishing.repositories.configureEach { repository ->
        when (repository.name) {
            "mavenLocalTest" -> {
                val childTask: TaskProvider<Task> = tasks.named(PUBLISH_ALL_TO_MAVEN_LOCAL_TEST)
                tasks.maybeRegister(PUBLISH_TO_MAVEN_LOCAL_TEST).configure { task ->
                    task.group = "publishing"
                    task.dependsOn(childTask)
                }
            }
            "mavenLocalBuildTest" -> {
                val childTask: TaskProvider<Task> =
                    tasks.named("publishAllPublicationsToMavenLocalBuildTestRepository")
                tasks.maybeRegister("publishToMavenLocalBuildTest").configure { task ->
                    task.group = "publishing"
                    task.dependsOn(childTask)
                }
            }
        }
    }
}

private fun HubdleConfigurableExtension.configureMavenPublication(name: String) {
    configure<PublishingExtension> {
        publications { container ->
            container.register<MavenPublication>(name) { from(project.components[name]) }
        }
    }
}

private fun HubdleConfigurableExtension.configureEmptyJavadocs() {
    val emptyJavadocsJar: Jar = tasks.maybeCreate<Jar>("emptyJavadocsJar")
    emptyJavadocsJar.apply {
        group = "build"
        description = "Assembles an empty Javadoc jar file for publishing"
        archiveClassifier.set("javadoc")
    }
    the<PublishingExtension>().publications.withType<MavenPublication> {
        artifact(emptyJavadocsJar)
    }
}

internal val Project.hubdlePublishingMaven: HubdleConfigPublishingMavenExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdlePublishingMaven: HubdleConfigPublishingMavenExtension
    get() = getHubdleExtension()
