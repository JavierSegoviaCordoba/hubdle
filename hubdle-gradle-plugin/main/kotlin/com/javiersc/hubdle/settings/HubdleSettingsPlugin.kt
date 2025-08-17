package com.javiersc.hubdle.settings

import JetBrainsComposeDevRepo
import JetBrainsKotlinBootstrapRepo
import JetBrainsKotlinDevRepo
import SonatypeSnapshotsRepo
import com.gradle.develocity.agent.gradle.DevelocityConfiguration
import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.properties.extensions.provider
import com.javiersc.hubdle.project.extensions._internal.hubdleCatalog
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.config.HubdleConfigExtension.ProjectConfig
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.hubdle.project.extensions.shared.PluginId.JetbrainsDokka
import com.javiersc.hubdle.project.extensions.shared.PluginId.JetbrainsKotlinxKover
import com.javiersc.hubdle.settings.extensions.extractedBuildProjects
import com.javiersc.hubdle.settings.extensions.extractedProjects
import com.javiersc.hubdle.settings.tasks.GenerateHubdleCatalogTask
import java.io.File
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ModuleIdentifier
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.initialization.Settings
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.language.base.plugins.LifecycleBasePlugin.ASSEMBLE_TASK_NAME

public open class HubdleSettingsPlugin @Inject constructor(private val objects: ObjectFactory) :
    Plugin<Settings> {

    override fun apply(target: Settings) {
        target.enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

        target.extensions.create<HubdleSettingsExtension>("hubdleSettings")

        target.configureRootProjectName()
        target.configureRepositories()
        target.configureGradleDevelocity()
        target.configureKoverMergeReports()
        target.configureDokkaMerge()

        target.gradle.settingsEvaluated { settings ->
            target.createHubdleVersionCatalog()
            target.configureHubdleCatalogTask()
            target.useHubdleOnAllProjects()
            settings.configureAutoInclude()
            settings.configureAutoIncludeVersionCatalogs(objects)
        }
    }

    private val Settings.hubdleExtension: HubdleSettingsExtension
        get() = the()

    private fun Settings.configureRootProjectName() {
        val rootProjectName: Provider<String> = provider {
            getStringProperty(ProjectConfig.rootProjectName).orNull ?: ""
        }
        hubdleExtension.rootProjectName.set(rootProjectName)
        rootProject.name = hubdleExtension.rootProjectName.get()
    }

    private fun Settings.createHubdleVersionCatalog() {
        if (hubdleSettings.catalog.isEnabled.orNull == false) return

        dependencyResolutionManagement.versionCatalogs.register("hubdle") { builder ->
            val hubdleCatalogVersion: String = hubdleSettings.catalog.version.get()
            builder.from("com.javiersc.hubdle:hubdle-version-catalog:$hubdleCatalogVersion")
            val catalog = hubdleSettings.catalog
            for ((alias, version) in catalog.replaceableVersions.get().entries) {
                builder.version(alias, version)
            }
            for ((alias, version) in catalog.replaceableStrictVersions.get().entries) {
                builder.version(alias) { it.strictly(version) }
            }
        }
    }

    private fun Settings.configureKoverMergeReports() {
        gradle.lifecycle.beforeProject { project ->
            val rootProject: Project = project.rootProject
            rootProject.pluginManager.withPlugin(JetbrainsKotlinxKover.id) {
                project.pluginManager.withPlugin(JetbrainsKotlinxKover.id) {
                    rootProject.dependencies { "kover"(project(project.path)) }
                }
            }
        }
    }

    private fun Settings.configureDokkaMerge() {
        gradle.lifecycle.beforeProject { project ->
            val rootProject: Project = project.rootProject
            rootProject.pluginManager.withPlugin(JetbrainsDokka.id) {
                project.pluginManager.withPlugin(JetbrainsDokka.id) {
                    rootProject.dependencies { "dokka"(project(project.path)) }
                }
            }
        }
    }
}

@DslMarker public annotation class HubdleSettingsDslMarker

internal val Settings.hubdleSettings: HubdleSettingsExtension
    get() = checkNotNull(extensions.findByType())

private fun Settings.useHubdleOnAllProjects() {
    val useOnAllProjects: Boolean = hubdleSettings.useOnAllProjects.get()
    settings.gradle.lifecycle.beforeProject { project: Project ->
        if (useOnAllProjects) {
            project.pluginManager.apply(PluginId.JavierscHubdle.id)
        }
    }
}

private fun Settings.configureRepositories() {
    dependencyResolutionManagement { management ->
        management.repositories { repository ->
            repository.google { google ->
                google.mavenContent { content ->
                    content.includeGroupByRegex("androidx.*")
                    content.includeGroupByRegex("com\\.android.*")
                    content.includeGroupByRegex("com\\.google.*")
                }
            }
            repository.mavenCentral()
            repository.gradlePluginPortal()
        }
    }
    addRepositoriesBasedOnProperties()
}

private fun Settings.configureHubdleCatalogTask() {
    gradle.rootProject { project ->
        project.pluginManager.apply(BasePlugin::class)

        val generateHubdleCatalogTask: TaskProvider<GenerateHubdleCatalogTask> =
            project.tasks.register<GenerateHubdleCatalogTask>("generateHubdleCatalog")

        val hubdleAliasToLibraries: Provider<Map<String, String>> =
            project.provider {
                project.hubdleCatalog?.libraryAliases.orEmpty().associateWith { alias ->
                    val library: MinimalExternalModuleDependency? = project.library(alias).orNull
                    val module: ModuleIdentifier? = library?.module
                    val version: String? = library?.version
                    when {
                        module != null && version != null -> "$module:$version"
                        module != null && version == null -> "$module"
                        else -> ""
                    }
                }
            }

        generateHubdleCatalogTask.configure { task -> task.libraries.set(hubdleAliasToLibraries) }

        project.tasks.named(ASSEMBLE_TASK_NAME).configure { task ->
            task.dependsOn(generateHubdleCatalogTask)
        }
    }
}

private fun Settings.configureAutoInclude() {
    val autoInclude = hubdleSettings.autoInclude

    if (autoInclude.isEnabled.get()) {
        autoInclude.includes(*extractedProjects().toTypedArray())
        autoInclude.includedBuilds(*extractedBuildProjects().toTypedArray())

        for (include in autoInclude.includableProjects) {
            include(include)
        }

        for (includeBuild in autoInclude.includableBuilds) {
            includeBuild(includeBuild)
        }
    }
}

private fun Settings.configureGradleDevelocity() {
    pluginManager.apply("com.gradle.develocity")

    val publishAlways = hubdleSettings.buildScan.publishAlways
    configure<DevelocityConfiguration> {
        buildScan { scan ->
            scan.publishing.onlyIf { publishAlways.get() }
            scan.termsOfUseUrl.set("https://gradle.com/terms-of-service")
            scan.termsOfUseAgree.set("yes")
        }
    }
}

private fun Settings.configureAutoIncludeVersionCatalogs(objects: ObjectFactory) {
    dependencyResolutionManagement { management ->
        management.versionCatalogs { container ->
            val catalogs: Map<String, File> = tomlFileInGradleDirs.getCatalogs()
            for ((name, file) in catalogs) {
                container.create(name) { catalog ->
                    catalog.from(objects.fileCollection().from(file.absolutePath))
                }
            }
        }
    }
}

private val Settings.tomlFileInGradleDirs: Set<File>
    get() =
        (rootDir.resolve("gradle").listFiles().orEmpty().toSet() +
                settingsDir.resolve("gradle").listFiles().orEmpty().toSet())
            .filter { it.extension == "toml" }
            .toSet()

private fun Set<File>.getCatalogs(): Map<String, File> =
    mapNotNull { file -> if (file.isCatalog) file else null }
        .mapNotNull { catalog ->
            val catalogName =
                when {
                    catalog.name.contains("-libs") -> catalog.name.substringBefore("-")
                    catalog.name.contains(".libs") -> catalog.name.substringBefore(".")
                    else -> null
                }
            when {
                catalog.name == "libs.versions.catalog" -> null
                catalogName?.isNotBlank() == true -> "${catalogName}Libs" to catalog
                else -> null
            }
        }
        .toMap()

private val File.isCatalog: Boolean
    get() = name.endsWith("libs.versions.toml")

private fun Settings.addRepositoriesBasedOnProperties() {
    fun getPropOrFalse(prop: String): Boolean =
        getBooleanProperty("hubdle.repositories.$prop").getOrElse(false)

    val shouldUseComposeDeb: Boolean = getPropOrFalse("jetbrains.compose.dev")
    val shouldUseKotlinBootstrap: Boolean = getPropOrFalse("jetbrains.kotlin.bootstrap")
    val shouldUseKotlinDev: Boolean = getPropOrFalse("jetbrains.kotlin.dev")
    val shouldUseSonatypeSnapshot: Boolean = getPropOrFalse("sonatype.snapshots")
    val shouldUseMavenLocal: Boolean = getPropOrFalse("mavenLocal")

    fun RepositoryHandler.configure() {
        if (shouldUseComposeDeb) maven(url = JetBrainsComposeDevRepo)
        if (shouldUseKotlinBootstrap) maven(url = JetBrainsKotlinBootstrapRepo)
        if (shouldUseKotlinDev) maven(url = JetBrainsKotlinDevRepo)
        if (shouldUseSonatypeSnapshot) maven(url = SonatypeSnapshotsRepo)
        if (shouldUseMavenLocal) mavenLocal()
    }

    pluginManagement.repositories(RepositoryHandler::configure)
    dependencyResolutionManagement.repositories(RepositoryHandler::configure)
}
