package com.javiersc.hubdle

import com.gradle.enterprise.gradleplugin.GradleEnterpriseExtension
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.extensions.extractedBuildProjects
import com.javiersc.hubdle.extensions.extractedProjects
import java.io.File
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType

public open class HubdleSettingsPlugin
@Inject
constructor(
    private val objects: ObjectFactory,
) : Plugin<Settings> {

    override fun apply(target: Settings) {
        target.rootProject.name = target.getProperty("root.project.name")
        target.enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

        target.extensions.create<HubdleSettingsExtension>("hubdle")

        target.gradle.settingsEvaluated {
            it.configureGradleEnterprise()
            it.configureAutoInclude()
            it.configureAutoIncludeVersionCatalogs(objects)
        }
    }
}

@DslMarker public annotation class HubdleSettingsDslMarker

internal val Settings.hubdle: HubdleSettingsExtension
    get() = checkNotNull(extensions.findByType())

internal fun Settings.configureAutoInclude() {
    val autoInclude = hubdle.autoInclude

    if (autoInclude.isEnabled) {
        println(extractedProjects())

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

private fun Settings.configureGradleEnterprise() {
    pluginManager.apply("com.gradle.enterprise")

    configure<GradleEnterpriseExtension> {
        buildScan {
            it.termsOfServiceUrl = "https://gradle.com/terms-of-service"
            it.termsOfServiceAgree = "yes"
        }
    }
}

private fun Settings.configureAutoIncludeVersionCatalogs(objects: ObjectFactory) {
    dependencyResolutionManagement { management ->
        management.versionCatalogs { container ->
            val catalogs: Map<String, File> = tomlFileInGradleDirs.getCatalogs()
            for ((name, file) in catalogs) {
                container.create(name) { it.from(objects.fileCollection().from(file.absolutePath)) }
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
