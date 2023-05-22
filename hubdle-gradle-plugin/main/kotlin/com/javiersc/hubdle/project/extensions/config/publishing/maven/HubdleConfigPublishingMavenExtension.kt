package com.javiersc.hubdle.project.extensions.config.publishing.maven

import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.isSemver
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
import org.gradle.api.tasks.TaskCollection
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
        userConfigurable { action.execute(the<PublishingExtension>().repositories) }
    }

    @HubdleDslMarker
    public fun publishing(action: Action<PublishingExtension> = Action {}) {
        userConfigurable { action.execute(the()) }
    }
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
        priority = Priority.P3,
        scope = Scope.CurrentProject,
        pluginId = PluginId.MavenPublish,
    )
    configurable(isEnabled = isEnabled, priority = Priority.P3) {
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
    val checkIsSemver: TaskCollection<Task> =
        tasks.maybeRegisterLazily("checkIsSemver") { task ->
            task.enabled = isTagPrefixProject
            task.doLast {
                val allTaskNames: List<String> = project.gradle.taskGraph.allTasks.map { it.name }
                val hasPublishToMavenLocalTest: Boolean =
                    allTaskNames.any { name -> name == "publishToMavenLocalTest" }
                val publishNonSemver: Boolean = hubdlePublishing.publishNonSemver.get()
                val isPublishException: Boolean = publishNonSemver || hasPublishToMavenLocalTest
                check(project.isSemver || isPublishException) {
                    // TODO: inject `$version` instead of getting it from the `project`
                    """|Only semantic versions can be published (current: ${project.version})
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

private fun HubdleConfigurableExtension.configureRepositories(publishing: PublishingExtension) {
    publishing.repositories.configureEach { repository ->
        when (repository.name) {
            "mavenLocalTest" -> {
                val childTask =
                    tasks.namedLazily<Task>("publishAllPublicationsToMavenLocalTestRepository")
                tasks.maybeRegisterLazily<Task>("publishToMavenLocalTest").configureEach { task ->
                    task.group = "publishing"
                    task.dependsOn(childTask)
                }
            }
            "mavenLocalBuildTest" -> {
                val childTask =
                    tasks.namedLazily<Task>("publishAllPublicationsToMavenLocalBuildTestRepository")
                tasks.maybeRegisterLazily<Task>("publishToMavenLocalBuildTest").configureEach { task
                    ->
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
