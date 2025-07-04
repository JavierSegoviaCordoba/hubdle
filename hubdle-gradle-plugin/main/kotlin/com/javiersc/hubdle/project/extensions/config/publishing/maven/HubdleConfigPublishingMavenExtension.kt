package com.javiersc.hubdle.project.extensions.config.publishing.maven

import PUBLISH_ALL_TO_MAVEN_LOCAL_TEST
import PUBLISH_TO_MAVEN_LOCAL_TEST
import com.javiersc.gradle.tasks.extensions.maybeRegister
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.tasks.CheckIsSemverTask
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.tasks.TaskProvider

@HubdleDslMarker
public open class HubdleConfigPublishingMavenExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project) {

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
    block: Action<MavenPublishBaseExtension>
) {
    val isEnabled: Property<Boolean> = property {
        isEnabled.get() && hubdlePublishingMaven.isFullEnabled.get()
    }
    applicablePlugin(
        isEnabled = isEnabled,
        scope = Scope.CurrentProject,
        pluginId = PluginId.MavenPublish,
    )
    applicablePlugin(
        isEnabled = isEnabled,
        scope = Scope.CurrentProject,
        pluginId = PluginId.VanniktechMavenPublish,
    )
    lazyConfigurable(isEnabled = isEnabled) { configurePublishingExtension(block) }
}

private fun HubdleConfigurableExtension.configurePublishingExtension(
    block: Action<MavenPublishBaseExtension>
) {
    configure<MavenPublishBaseExtension> {
        runCatching { publishToMavenCentral() }
        configurePom(extension = this)
        block.execute(this)
    }
    configureRepositories()
    configurePublishOnlySemver()
}

private fun HubdleConfigurableExtension.configurePom(extension: MavenPublishBaseExtension) {
    extension.pom {
        val pom: MavenPom = it
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

private fun HubdleConfigurableExtension.configurePublishOnlySemver() {
    tasks
        .named { it.startsWith("publish") }
        .configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(CheckIsSemverTask.NAME)
        }
}

private fun HubdleConfigurableExtension.configureRepositories() {
    configure<PublishingExtension> {
        repositories.configureEach { repository ->
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
}

internal val Project.hubdlePublishingMaven: HubdleConfigPublishingMavenExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdlePublishingMaven: HubdleConfigPublishingMavenExtension
    get() = getHubdleExtension()
