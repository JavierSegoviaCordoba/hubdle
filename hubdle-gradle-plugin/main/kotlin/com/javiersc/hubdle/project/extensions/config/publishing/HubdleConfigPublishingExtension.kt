package com.javiersc.hubdle.project.extensions.config.publishing

import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.config.publishing.maven.HubdleConfigPublishingMavenExtension
import com.javiersc.hubdle.project.extensions.config.publishing.signing.HubdleConfigPublishingSigningExtension
import com.javiersc.hubdle.project.extensions.config.publishing.tasks.CheckIsSemverTask
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleConfigPublishingExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    override val priority: Priority = Priority.P2

    public val publishNonSemver: Property<Boolean> = property {
        getBooleanProperty(Publishing.nonSemver).orElse(false).get()
    }

    @HubdleDslMarker
    public fun publishNonSemver(value: Boolean) {
        publishNonSemver.set(value)
    }

    public val maven: HubdleConfigPublishingMavenExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun maven(action: Action<HubdleConfigPublishingMavenExtension> = Action {}) {
        maven.enableAndExecute(action)
    }

    public val signing: HubdleConfigPublishingSigningExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun signing(action: Action<HubdleConfigPublishingSigningExtension> = Action {}) {
        signing.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        CheckIsSemverTask.register(this@HubdleConfigPublishingExtension)
    }

    public object Publishing {
        public const val nonSemver: String = "publishing.nonSemver"
        public const val sign: String = "publishing.sign"
    }
}

internal val Project.isSignificantSemver: Boolean
    get() = GradleVersion.significantRegex.matches("$version")

internal val Project.hubdlePublishing: HubdleConfigPublishingExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdlePublishing: HubdleConfigPublishingExtension
    get() = getHubdleExtension()
