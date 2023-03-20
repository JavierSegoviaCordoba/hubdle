package com.javiersc.hubdle.project.extensions.config.nexus

import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.project.HubdleProperty.Nexus
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import io.github.gradlenexus.publishplugin.NexusPublishException
import io.github.gradlenexus.publishplugin.NexusPublishExtension
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

    override val priority: Priority = Priority.P3

    @HubdleDslMarker
    public fun nexusPublishing(action: Action<NexusPublishException> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.GithubGradleNexusPublishPlugin
        )

        configurable {
            configure<NexusPublishExtension> {
                repositoryDescription.set("${rootProject.group} - ${rootProject.version}")

                repositories { container ->
                    container.sonatype { nexusRepository ->
                        val nexusUrl = getPropertyOrNull(Nexus.nexusUrl)
                        val snapshotRepositoryUrl =
                            getPropertyOrNull(Nexus.nexusSnapshotRepositoryUrl)
                        if (nexusUrl != null && snapshotRepositoryUrl != null) {
                            nexusRepository.nexusUrl.set(uri(nexusUrl))
                            nexusRepository.snapshotRepositoryUrl.set(uri(snapshotRepositoryUrl))
                        }
                        nexusRepository.username.set(getPropertyOrNull(Nexus.nexusUser))
                        nexusRepository.password.set(getPropertyOrNull(Nexus.nexusToken))
                        nexusRepository.stagingProfileId.set(
                            getPropertyOrNull(Nexus.nexusStagingProfileId)
                        )
                    }
                }

                connectTimeout.set(Duration.ofMinutes(DEFAULT_CONNECT_TIMEOUT))
                clientTimeout.set(Duration.ofMinutes(DEFAULT_CLIENT_TIMEOUT))

                transitionCheckOptions { options ->
                    options.maxRetries.set(DEFAULT_MAX_RETRIES)
                    options.delayBetween.set(Duration.ofSeconds(DEFAULT_DELAY_BETWEEN))
                }
            }
        }
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
