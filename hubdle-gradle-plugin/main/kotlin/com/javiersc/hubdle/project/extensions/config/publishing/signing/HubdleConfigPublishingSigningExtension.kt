package com.javiersc.hubdle.project.extensions.config.publishing.signing

import PUBLISH_ALL_TO_MAVEN_LOCAL_TEST
import PUBLISH_TO_MAVEN_LOCAL_TEST
import com.javiersc.gradle.project.extensions.isNotSnapshot
import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.isSignificantSemver
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.api.tasks.TaskCollection
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

@HubdleDslMarker
public open class HubdleConfigPublishingSigningExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdlePublishing)

    public val gnupgKey: Property<String> = property {
        getStringProperty(Signing.gnupgKey).orElse("").get().replace("\\n", "\n")
    }

    @HubdleDslMarker
    public fun gnupgKey(value: String) {
        gnupgKey.set(value)
    }

    public val gnupgPassphrase: Property<String> = property {
        getStringProperty(Signing.gnupgPassphrase).orElse("").get()
    }

    @HubdleDslMarker
    public fun gnupgPassphrase(value: String) {
        gnupgPassphrase.set(value)
    }

    public val shouldSign: Property<Boolean> = property {
        getBooleanProperty(Signing.sign).orElse(false).get()
    }

    @HubdleDslMarker
    public fun shouldSign(value: Boolean) {
        shouldSign.set(value)
    }

    @HubdleDslMarker
    public fun signing(action: Action<SigningExtension> = Action {}): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.GradleSigning,
        )
        lazyConfigurable { configureSigningForPublishing() }
    }

    public object Signing {
        public const val gnupgKey: String = "signing.gnupg.key"
        public const val gnupgPassphrase: String = "signing.gnupg.passphrase"
        public const val sign: String = "publishing.sign"
    }

    private fun HubdleConfigurableExtension.configureSigningForPublishing() {
        configure<SigningExtension> {
            val allTasks: List<String> =
                project.gradle.startParameter.taskRequests.flatMap { it.args }
            val hasPublishTask: Boolean = allTasks.any { it.startsWith("publish") }
            val hasPublishToMavenLocalTasks: Boolean =
                allTasks.any { task ->
                    task == "publishToMavenLocal" ||
                        task == PUBLISH_TO_MAVEN_LOCAL_TEST ||
                        task == "publishToMavenLocalBuildTest" ||
                        task == PUBLISH_ALL_TO_MAVEN_LOCAL_TEST ||
                        task == "publishAllPublicationsToMavenLocalBuildTestRepository"
                }

            val hasTaskCondition: Boolean = (hasPublishTask && !hasPublishToMavenLocalTasks)
            val hasSemverCondition: Boolean =
                project.isNotSnapshot.get() && project.isSignificantSemver

            isRequired = (hasTaskCondition && hasSemverCondition) || shouldSign.get()

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

    private fun SigningExtension.signInMemory() {
        val gnupgKey: String? = gnupgKey.orNull
        val gnupgPassphrase: String? = gnupgPassphrase.orNull

        if (gnupgKey != null && gnupgPassphrase != null) {
            useInMemoryPgpKeys(gnupgKey, gnupgPassphrase)
        }
    }
}
