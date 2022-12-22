package com.javiersc.hubdle.extensions.kotlin.intellij

import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.HubdleProperty.IntelliJ
import com.javiersc.hubdle.HubdleProperty.JetBrains
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions._internal.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions._internal.configureDependencies
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.documentation.changelog.GENERATED_CHANGELOG_HTML_DIR_PATH
import com.javiersc.hubdle.extensions.config.documentation.changelog.GENERATED_CHANGELOG_HTML_FILE_PATH
import com.javiersc.hubdle.extensions.config.documentation.changelog.HubdleConfigDocumentationChangelogExtension.Companion.GENERATED_CHANGELOG_HTML_ATTRIBUTE
import com.javiersc.hubdle.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlin
import com.javiersc.hubdle.extensions.kotlin.intellij.features.HubdleKotlinIntellijFeaturesExtension
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
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinIntellijExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val features: HubdleKotlinIntellijFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun kotlin(action: Action<KotlinJvmProjectExtension>) {
        userConfigurable { action.execute(the()) }
    }

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

    @HubdleDslMarker
    public fun main(action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("main", action::execute) }
        }
    }

    @HubdleDslMarker
    public fun test(action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("test", action::execute) }
        }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugins()
    }
}

private fun HubdleKotlinIntellijExtension.applicablePlugins() =
    with(project) {
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
            configureDependencies()
            configureDefaultJavaSourceSets()
            configureDefaultKotlinSourceSets()
        }

        configurable(
            isEnabled = property { isFullEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
        ) {
            configurePublishPlugin()
            configureSignPlugin()
        }
    }

private fun HubdleKotlinIntellijExtension.configureIntellijPluginExtension() =
    with(project) {
        val downloadSourcesProperty =
            getPropertyOrNull(IntelliJ.downloadSources)?.toBoolean() ?: true

        val updateSinceUntilBuildProperty =
            getPropertyOrNull(IntelliJ.updateSinceUntilBuild)?.toBoolean() ?: true

        configure<IntelliJPluginExtension> {
            pluginName.set(getProperty(HubdleProperty.POM.name))
            downloadSources.set(downloadSourcesProperty)
            type.set(getProperty(IntelliJ.type))
            version.set(getProperty(IntelliJ.version))
            updateSinceUntilBuild.set(updateSinceUntilBuildProperty)
        }
    }

private fun HubdleKotlinIntellijExtension.configurePatchPluginXml() =
    with(project) {
        tasks.withType<PatchPluginXmlTask>().configureEach { task ->
            task.dependsOn(tasks.named("copyGeneratedChangelogHtml"))

            task.version.set("$version")
            task.sinceBuild.set(getProperty(IntelliJ.sinceBuild))
            task.untilBuild.set(getProperty(IntelliJ.untilBuild))

            val changelogFile = layout.buildDirectory.file(GENERATED_CHANGELOG_HTML_FILE_PATH)
            val notes =
                changelogFile.flatMap {
                    provider {
                        if (it.asFile.exists()) it.asFile.readText() else "No changelog found"
                    }
                }
            task.changeNotes.set(notes)
        }
    }

private fun HubdleKotlinIntellijExtension.configurePublishPlugin() =
    with(project) {
        tasks.withType<PublishPluginTask>().configureEach { task ->
            task.token.set(project.getPropertyOrNull(IntelliJ.publishToken) ?: "")
        }
    }

private fun HubdleKotlinIntellijExtension.configureSignPlugin() =
    with(project) {
        tasks.withType<SignPluginTask>().configureEach { task ->
            val certificate = getPropertyOrNull(JetBrains.marketplaceCertificateChain) ?: ""
            val key =
                getPropertyOrNull(JetBrains.marketplaceKey)
                    ?: getPropertyOrNull(HubdleProperty.Signing.gnupgKey) ?: ""

            val passphrase =
                getPropertyOrNull(JetBrains.marketplaceKeyPassphrase)
                    ?: getPropertyOrNull(HubdleProperty.Signing.gnupgPassphrase) ?: ""

            task.certificateChain.set(certificate)
            task.privateKey.set(key)
            task.password.set(passphrase)
        }
    }

private fun HubdleKotlinIntellijExtension.configureGeneratedChangelogHtmlDependency() =
    with(project) {
        val generatedChangelogHtml =
            configurations.create("generatedChangelogHtml").apply {
                isCanBeConsumed = false
                isCanBeResolved = true
                attributes { attributes ->
                    attributes.attribute(
                        LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
                        objects.named(GENERATED_CHANGELOG_HTML_ATTRIBUTE)
                    )
                }
            }

        dependencies { generatedChangelogHtml(project(":")) }

        tasks.register<Copy>("copyGeneratedChangelogHtml") {
            from(generatedChangelogHtml)
            into(layout.buildDirectory.dir(GENERATED_CHANGELOG_HTML_DIR_PATH))
        }
    }

internal val HubdleEnableableExtension.hubdleIntellij: HubdleKotlinIntellijExtension
    get() = getHubdleExtension()
