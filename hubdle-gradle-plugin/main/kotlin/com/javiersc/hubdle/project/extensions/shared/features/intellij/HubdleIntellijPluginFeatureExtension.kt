package com.javiersc.hubdle.project.extensions.shared.features.intellij

import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.documentation.changelog.GENERATED_CHANGELOG_HTML_DIR_PATH
import com.javiersc.hubdle.project.extensions.config.documentation.changelog.GENERATED_CHANGELOG_HTML_FILE_PATH
import com.javiersc.hubdle.project.extensions.config.documentation.changelog.HubdleConfigDocumentationChangelogExtension.Companion.GENERATED_CHANGELOG_HTML_ATTRIBUTE
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.maven.hubdlePublishingMavenPom
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskCollection
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.jetbrains.intellij.IntelliJPluginExtension
import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.intellij.tasks.PublishPluginTask
import org.jetbrains.intellij.tasks.RunPluginVerifierTask
import org.jetbrains.intellij.tasks.SignPluginTask

@HubdleDslMarker
public open class HubdleIntellijPluginFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava, hubdleKotlinJvm)

    public val downloadSources: Property<Boolean> = property {
        getBooleanProperty(IntelliJ.downloadSources).orElse(true).get()
    }

    @HubdleDslMarker
    public fun downloadSources(value: Boolean) {
        downloadSources.set(value)
    }

    public val sinceBuild: Property<String> = property {
        getStringProperty(IntelliJ.sinceBuild).orElse("").get()
    }

    @HubdleDslMarker
    public fun sinceBuild(value: String) {
        sinceBuild.set(value)
    }

    public val untilBuild: Property<String> = property {
        getStringProperty(IntelliJ.untilBuild).orElse("").get()
    }

    @HubdleDslMarker
    public fun untilBuild(value: String) {
        untilBuild.set(value)
    }

    public val token: Property<String> = property {
        getStringProperty(IntelliJ.token).orElse("").get()
    }

    @HubdleDslMarker
    public fun token(value: String) {
        token.set(value)
    }

    public val type: Property<String> = property {
        getStringProperty(IntelliJ.type).orElse("").get()
    }

    @HubdleDslMarker
    public fun type(value: String) {
        type.set(value)
    }

    public val updateSinceUntilBuild: Property<Boolean> = property {
        getBooleanProperty(IntelliJ.updateSinceUntilBuild).orElse(true).get()
    }

    @HubdleDslMarker
    public fun updateSinceUntilBuild(value: Boolean) {
        updateSinceUntilBuild.set(value)
    }

    public val version: Property<String> = property {
        getStringProperty(IntelliJ.version).orElse("").get()
    }

    @HubdleDslMarker
    public fun version(value: String) {
        version.set(value)
    }

    public val jetbrainsMarketplaceCertificateChain: Property<String> = property {
        getStringProperty(JetBrains.marketplaceCertificateChain).orElse("").get()
    }

    @HubdleDslMarker
    public fun jetbrainsMarketplaceCertificateChain(value: String) {
        jetbrainsMarketplaceCertificateChain.set(value)
    }

    public val jetbrainsMarketplaceKey: Property<String> = property {
        getStringProperty(JetBrains.marketplaceKey).orElse("").get()
    }

    @HubdleDslMarker
    public fun jetbrainsMarketplaceKey(value: String) {
        jetbrainsMarketplaceKey.set(value)
    }

    public val jetbrainsMarketplaceKeyPassphrase: Property<String> = property {
        getStringProperty(JetBrains.marketplaceKeyPassphrase).orElse("").get()
    }

    @HubdleDslMarker
    public fun jetbrainsMarketplaceKeyPassphrase(value: String) {
        jetbrainsMarketplaceKeyPassphrase.set(value)
    }

    @HubdleDslMarker
    public fun intellij(action: Action<IntelliJPluginExtension> = Action {}) {
        lazyConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun patchPluginXml(action: Action<PatchPluginXmlTask> = Action {}) {
        lazyConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun publishPlugin(action: Action<PublishPluginTask> = Action {}) {
        lazyConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun signPlugin(action: Action<SignPluginTask> = Action {}) {
        lazyConfigurable { action.execute(the()) }
    }

    public object IntelliJ {
        public const val downloadSources: String = "intellij.downloadSources"
        public const val token: String = "intellij.token"
        public const val type: String = "intellij.type"
        public const val sinceBuild: String = "intellij.sinceBuild"
        public const val untilBuild: String = "intellij.untilBuild"
        public const val updateSinceUntilBuild: String = "intellij.updateSinceUntilBuild"
        public const val version: String = "intellij.version"
    }

    public object JetBrains {
        public const val marketplaceCertificateChain: String =
            "jetbrains.marketplace.certificateChain"
        public const val marketplaceKey: String = "jetbrains.marketplace.key"
        public const val marketplaceKeyPassphrase: String = "jetbrains.marketplace.keyPassphrase"
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsKotlinJvm)

        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsIntellij)

        lazyConfigurable {
            configureIntellijPluginExtension()
            configureGeneratedChangelogHtmlDependency()
            configurePatchPluginXml()
        }

        lazyConfigurable(
            isEnabled = property { isFullEnabled.get() && hubdlePublishing.isFullEnabled.get() },
        ) {
            configurePublishPlugin()
            configureSignPlugin()
        }
    }
}

private fun HubdleIntellijPluginFeatureExtension.configureIntellijPluginExtension() =
    with(project) {
        val hubdleIntellij = this@configureIntellijPluginExtension

        configure<IntelliJPluginExtension> {
            pluginName.set(hubdlePublishingMavenPom.name)
            downloadSources.set(hubdleIntellij.downloadSources)
            type.set(hubdleIntellij.type)
            version.set(hubdleIntellij.version)
            updateSinceUntilBuild.set(hubdleIntellij.updateSinceUntilBuild)
        }

        val runPluginVerifierTask: TaskCollection<RunPluginVerifierTask> = tasks.withType()
        tasks.named(CHECK_TASK_NAME).configure { it.dependsOn(runPluginVerifierTask) }
    }

private fun HubdleIntellijPluginFeatureExtension.configurePatchPluginXml() =
    with(project) {
        tasks.withType<PatchPluginXmlTask>().configureEach { task ->
            task.dependsOn(tasks.named("copyGeneratedChangelogHtml"))

            task.version.set("$version")
            task.sinceBuild.set(sinceBuild)
            task.untilBuild.set(untilBuild)

            val changelogFile: Provider<RegularFile> =
                layout.buildDirectory.file(GENERATED_CHANGELOG_HTML_FILE_PATH)
            val notes: Provider<String> =
                changelogFile.flatMap {
                    val file: File = it.asFile
                    provider { if (file.exists()) file.readText() else "No changelog found" }
                }
            task.changeNotes.set(notes)
        }
    }

private fun HubdleIntellijPluginFeatureExtension.configurePublishPlugin() =
    with(project) {
        tasks.withType<PublishPluginTask>().configureEach { task -> task.token.set(token) }
    }

private fun HubdleIntellijPluginFeatureExtension.configureSignPlugin() =
    with(project) {
        tasks.withType<SignPluginTask>().configureEach { task ->
            task.certificateChain.set(jetbrainsMarketplaceCertificateChain)
            task.privateKey.set(jetbrainsMarketplaceKey)
            task.password.set(jetbrainsMarketplaceKeyPassphrase)
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
                        objects.named(GENERATED_CHANGELOG_HTML_ATTRIBUTE))
                }
            }

        dependencies { generatedChangelogHtml(project(":")) }

        tasks.register<Copy>("copyGeneratedChangelogHtml") {
            from(generatedChangelogHtml)
            into(layout.buildDirectory.dir(GENERATED_CHANGELOG_HTML_DIR_PATH))
        }
    }

public interface HubdleIntellijPluginDelegateFeatureExtension : BaseHubdleExtension {

    public val plugin: HubdleIntellijPluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleIntellijPluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }
}
