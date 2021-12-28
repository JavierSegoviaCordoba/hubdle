package com.javiersc.gradle.plugins.projects.version.catalog.models

import java.io.File
import java.util.Locale
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugin.devel.PluginDeclaration
import org.tomlj.Toml

class VersionCatalog(
    val versions: List<Table.Version>,
    val libraries: List<Table.Library>,
    val bundles: List<Table.Bundle>,
    val plugins: List<Table.Plugin>,
) {

    sealed interface Table {
        data class Version(val alias: String, val version: String) : Table {
            companion object {
                const val name = "versions"
            }
        }

        data class Library(val alias: String, val module: String, val version: String) : Table {
            companion object {
                const val name = "libraries"
            }
        }

        data class Bundle(val alias: String, val libraries: List<String>) : Table {
            companion object {
                const val name = "bundles"
            }
        }

        data class Plugin(val alias: String, val id: String, val version: String) : Table {
            companion object {
                const val name = "plugins"
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun VersionCatalog.toToml(): String {
    val versionRef = versions.first().alias

    return buildList {
            if (versions.isEmpty().not()) {
                add("")
                add("[${VersionCatalog.Table.Version.name}]")
                versions.forEach { (alias, version) -> add("""$alias = "$version"""") }
            }

            if (libraries.isEmpty().not()) {
                add("")
                add("[${VersionCatalog.Table.Library.name}]")
                libraries.forEach { (alias, module, _) ->
                    add("""$alias = { module = "$module", version.ref = "$versionRef" }""")
                }
            }

            if (bundles.isEmpty().not()) {
                add("")
                add("[${VersionCatalog.Table.Bundle.name}]")
                bundles.groupBy { it.alias }
                bundles.forEach { (alias, libraries) ->
                    add("$alias = [")
                    libraries.forEach { library -> add(""""$library",""".prependIndent("    ")) }
                    add("]")
                }
            }
            if (plugins.isEmpty().not()) {
                add("")
                add("[${VersionCatalog.Table.Plugin.name}]")
                plugins.forEach { (alias, id, _) ->
                    add("""$alias = { id = "$id", version.ref = "$versionRef" }""")
                }
            }
            add("")
        }
        .drop(1)
        .joinToString("\n")
}

fun List<Project>.toVersionCatalog(
    librariesPrefix: String,
    removeAliasPrefix: String,
    tomlFile: File,
): VersionCatalog {
    val versions: List<VersionCatalog.Table.Version> =
        listOf(toVersionCatalogVersion(removeAliasPrefix))
    val libraries: List<VersionCatalog.Table.Library> = map { project ->
        project.toVersionCatalogLibrary(librariesPrefix)
    }
    val bundles: List<VersionCatalog.Table.Bundle> = tomlFile.toVersionCatalogBundles()
    val plugins: List<VersionCatalog.Table.Plugin> = mapNotNull(Project::toVersionCatalogPlugins)
    return VersionCatalog(versions, libraries, bundles, plugins)
}

fun List<Project>.toVersionCatalogVersion(removeAliasPrefix: String): VersionCatalog.Table.Version {
    when {
        distinct().isEmpty() -> {}
        map { project -> "${project.version}" }.distinct().count() != 1 -> {
            error("There is more than one version, all projects should use the same version")
        }
        map { project -> "${project.group}" }.distinct().count() != 1 -> {
            error("There is more than one group, all projects should use the same group")
        }
    }

    val project = first()

    val alias = "${project.group}".toCamelCaseAlias(removeAliasPrefix)
    return VersionCatalog.Table.Version(alias = alias, version = "${project.version}")
}

fun Project.toVersionCatalogLibrary(librariesPrefix: String): VersionCatalog.Table.Library {
    val alias =
        name.toCamelCaseAlias().run {
            if (librariesPrefix.isNotBlank()) "${librariesPrefix.firstLowerCase()}-$this" else this
        }
    return VersionCatalog.Table.Library(
        alias = alias,
        module = "$group:$name",
        version = "$version",
    )
}

fun File.toVersionCatalogBundles(): List<VersionCatalog.Table.Bundle> {
    val bundlesTable = Toml.parse(readText()).getTable(VersionCatalog.Table.Bundle.name)
    return bundlesTable
        ?.keySet()
        ?.map { alias ->
            val librariesArray = bundlesTable.getArrayOrEmpty(alias)
            val libraries =
                List(librariesArray.toList().size) { index -> librariesArray.getString(index) }
            VersionCatalog.Table.Bundle(alias, libraries)
        }
        .orEmpty()
}

fun Project.toVersionCatalogPlugins(): VersionCatalog.Table.Plugin? {
    val alias = name.toCamelCaseAlias()
    val extension = extensions.findByType<GradlePluginDevelopmentExtension>()
    val id = extension?.plugins?.asMap?.values?.map(PluginDeclaration::getId)?.firstOrNull()
    return id?.let { VersionCatalog.Table.Plugin(alias, id, "$version") }
}

private fun String.firstLowerCase() = replaceFirstChar(Char::lowercase)

private fun String.capitalize() = replaceFirstChar { char: Char ->
    if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
}

private fun String.toCamelCaseAlias(removeAliasPrefix: String = "") =
    replace(removeAliasPrefix, "")
        .split(".")
        .filter(String::isNotBlank)
        .joinToString("", transform = String::capitalize)
        .split("-")
        .joinToString("", transform = String::capitalize)
        .firstLowerCase()
