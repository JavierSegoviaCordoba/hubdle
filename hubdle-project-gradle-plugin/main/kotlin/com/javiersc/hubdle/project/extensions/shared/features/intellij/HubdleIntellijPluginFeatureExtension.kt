package com.javiersc.hubdle.project.extensions.shared.features.intellij

import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.documentation.changelog.HubdleConfigDocumentationChangelogExtension
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.intellij.IntelliJPluginExtension
import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.intellij.tasks.PublishPluginTask
import org.jetbrains.intellij.tasks.SignPluginTask

@HubdleDslMarker
public open class HubdleIntellijPluginFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    @HubdleDslMarker
    public fun intellij(action: Action<IntelliJPluginExtension> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun patchPluginXml(action: Action<PatchPluginXmlTask> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun publishPlugin(action: Action<PublishPluginTask> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun signPlugin(action: Action<SignPluginTask> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinJvm
        )

        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsIntellij
        )

        configurable {
            configureIntellijPluginExtension()
            configureGeneratedChangelogHtmlDependency()
            configurePatchPluginXml()
        }

        configurable(
            isEnabled = property { isFullEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
        ) {
            configurePublishPlugin()
            configureSignPlugin()
        }
    }
}

private fun HubdleIntellijPluginFeatureExtension.configureIntellijPluginExtension() =
    with(project) {
        val downloadSourcesProperty =
            getPropertyOrNull(com.javiersc.hubdle.project.HubdleProperty.IntelliJ.downloadSources)
                ?.toBoolean()
                ?: true

        val updateSinceUntilBuildProperty =
            getPropertyOrNull(
                    com.javiersc.hubdle.project.HubdleProperty.IntelliJ.updateSinceUntilBuild
                )
                ?.toBoolean()
                ?: true

        configure<IntelliJPluginExtension> {
            pluginName.set(getProperty(com.javiersc.hubdle.project.HubdleProperty.POM.name))
            downloadSources.set(downloadSourcesProperty)
            type.set(getProperty(com.javiersc.hubdle.project.HubdleProperty.IntelliJ.type))
            version.set(getProperty(com.javiersc.hubdle.project.HubdleProperty.IntelliJ.version))
            updateSinceUntilBuild.set(updateSinceUntilBuildProperty)
        }
    }

private fun HubdleIntellijPluginFeatureExtension.configurePatchPluginXml() =
    with(project) {
        tasks.withType<PatchPluginXmlTask>().configureEach { task ->
            task.dependsOn(tasks.named("copyGeneratedChangelogHtml"))

            task.version.set("$version")
            task.sinceBuild.set(
                getProperty(com.javiersc.hubdle.project.HubdleProperty.IntelliJ.sinceBuild)
            )
            task.untilBuild.set(
                getProperty(com.javiersc.hubdle.project.HubdleProperty.IntelliJ.untilBuild)
            )

            val changelogFile =
                layout.buildDirectory.file(
                    com.javiersc.hubdle.project.extensions.config.documentation.changelog
                        .GENERATED_CHANGELOG_HTML_FILE_PATH
                )
            val notes =
                changelogFile.flatMap {
                    provider {
                        if (it.asFile.exists()) it.asFile.readText() else "No changelog found"
                    }
                }
            task.changeNotes.set(notes)
        }
    }

private fun HubdleIntellijPluginFeatureExtension.configurePublishPlugin() =
    with(project) {
        tasks.withType<PublishPluginTask>().configureEach { task ->
            task.token.set(
                project.getPropertyOrNull(
                    com.javiersc.hubdle.project.HubdleProperty.IntelliJ.publishToken
                )
                    ?: ""
            )
        }
    }

private fun HubdleIntellijPluginFeatureExtension.configureSignPlugin() =
    with(project) {
        tasks.withType<SignPluginTask>().configureEach { task ->
            val certificate =
                getPropertyOrNull(
                    com.javiersc.hubdle.project.HubdleProperty.JetBrains.marketplaceCertificateChain
                )
                    ?: ""
            val key =
                getPropertyOrNull(
                    com.javiersc.hubdle.project.HubdleProperty.JetBrains.marketplaceKey
                )
                    ?: getPropertyOrNull(
                        com.javiersc.hubdle.project.HubdleProperty.Signing.gnupgKey
                    )
                        ?: ""

            val passphrase =
                getPropertyOrNull(
                    com.javiersc.hubdle.project.HubdleProperty.JetBrains.marketplaceKeyPassphrase
                )
                    ?: getPropertyOrNull(
                        com.javiersc.hubdle.project.HubdleProperty.Signing.gnupgPassphrase
                    )
                        ?: ""

            task.certificateChain.set(certificate)
            task.privateKey.set(key)
            task.password.set(passphrase)
        }
    }

private fun HubdleIntellijPluginFeatureExtension.configureGeneratedChangelogHtmlDependency() =
    with(project) {
        val generatedChangelogHtml =
            configurations.create("generatedChangelogHtml").apply {
                isCanBeConsumed = false
                isCanBeResolved = true
                attributes { attributes ->
                    attributes.attribute(
                        LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
                        objects.named(
                            HubdleConfigDocumentationChangelogExtension
                                .GENERATED_CHANGELOG_HTML_ATTRIBUTE
                        )
                    )
                }
            }

        dependencies { generatedChangelogHtml(project(":")) }

        tasks.register<Copy>("copyGeneratedChangelogHtml") {
            from(generatedChangelogHtml)
            into(
                layout.buildDirectory.dir(
                    com.javiersc.hubdle.project.extensions.config.documentation.changelog
                        .GENERATED_CHANGELOG_HTML_DIR_PATH
                )
            )
        }
    }

public interface HubdleIntellijPluginDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val plugin: HubdleIntellijPluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleIntellijPluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleGradlePluginFeature:
    HubdleIntellijPluginFeatureExtension
    get() = getHubdleExtension()
