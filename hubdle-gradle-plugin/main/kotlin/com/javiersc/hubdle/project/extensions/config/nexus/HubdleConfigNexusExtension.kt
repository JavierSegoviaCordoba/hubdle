package com.javiersc.hubdle.project.extensions.config.nexus

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.config.publishing.tasks.CheckIsSemverTask
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import io.github.gradlenexus.publishplugin.NexusPublishException
import io.github.gradlenexus.publishplugin.NexusPublishExtension
import java.net.URI
import java.time.Duration
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure

@HubdleDslMarker
public open class HubdleConfigNexusExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val snapshotRepositoryUrl: Property<URI?> = property {
        getStringProperty(Nexus.nexusSnapshotRepositoryUrl).orNull?.let(project::uri)
    }

    @HubdleDslMarker
    public fun snapshotRepositoryUrl(value: String) {
        snapshotRepositoryUrl.set(project.uri(value))
    }

    public val stagingProfileId: Property<String?> = property {
        getStringProperty(Nexus.nexusStagingProfileId).orNull
    }

    @HubdleDslMarker
    public fun stagingProfileId(value: String) {
        stagingProfileId.set(value)
    }

    public val token: Property<String?> = property { getStringProperty(Nexus.nexusToken).orNull }

    @HubdleDslMarker
    public fun token(value: String) {
        token.set(value)
    }

    public val url: Property<URI?> = property {
        getStringProperty(Nexus.nexusUrl).orNull?.let(project::uri)
    }

    @HubdleDslMarker
    public fun url(value: String) {
        url.set(project.uri(value))
    }

    public val user: Property<String?> = property { getStringProperty(Nexus.nexusUser).orNull }

    @HubdleDslMarker
    public fun user(value: String) {
        user.set(value)
    }

    @HubdleDslMarker
    public fun nexusPublishing(action: Action<NexusPublishException> = Action {}): Unit =
        fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject, pluginId = PluginId.GithubGradleNexusPublishPlugin)

        lazyConfigurable {
            configure<NexusPublishExtension> {
                repositoryDescription.set("${rootProject.group} - ${rootProject.version}")

                repositories { container ->
                    container.sonatype { nexusRepository ->
                        if (url.orNull != null && snapshotRepositoryUrl.orNull != null) {
                            nexusRepository.nexusUrl.set(url)
                            nexusRepository.snapshotRepositoryUrl.set(snapshotRepositoryUrl)
                        }
                        nexusRepository.username.set(user)
                        nexusRepository.password.set(token)
                        nexusRepository.stagingProfileId.set(stagingProfileId)
                    }
                }

                connectTimeout.set(Duration.ofMinutes(DEFAULT_CONNECT_TIMEOUT))
                clientTimeout.set(Duration.ofMinutes(DEFAULT_CLIENT_TIMEOUT))

                transitionCheckOptions { options ->
                    options.maxRetries.set(DEFAULT_MAX_RETRIES)
                    options.delayBetween.set(Duration.ofSeconds(DEFAULT_DELAY_BETWEEN))
                }
            }

            tasks.named("initializeSonatypeStagingRepository").configure { task ->
                task.enabled = isTagPrefixProject
                task.dependsOn(CheckIsSemverTask.NAME)
            }
        }
    }

    public object Nexus {
        public const val nexusSnapshotRepositoryUrl: String = "nexus.snapshotRepositoryUrl"
        public const val nexusStagingProfileId: String = "nexus.stagingProfileId"
        public const val nexusToken: String = "nexus.token"
        public const val nexusUrl: String = "nexus.url"
        public const val nexusUser: String = "nexus.user"
    }

    internal companion object {
        private const val DEFAULT_CONNECT_TIMEOUT = 30L
        private const val DEFAULT_CLIENT_TIMEOUT = 30L
        private const val DEFAULT_MAX_RETRIES = 200
        private const val DEFAULT_DELAY_BETWEEN = 10L
    }
}

internal val HubdleEnableableExtension.hubdleNexus: HubdleConfigNexusExtension
    get() = getHubdleExtension()
