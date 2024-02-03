import java.io.File

public fun main() {
    val catalog =
        File("/Volumes/Dev/repos/gradle/hubdle/gradle/hubdle.libs.versions.toml").buildCatalog()
    catalog.checkIntegrity()
}

internal fun File.buildCatalog(): Catalog {
    val lines = readLines().filterNot(String::isNullOrBlank).filter { !it.startsWith("#") }
    val versionsIndex = lines.indexOfFirst { it.contains("[versions]") }
    val librariesIndex = lines.indexOfFirst { it.contains("[libraries]") }
    val pluginsIndex = lines.indexOfFirst { it.contains("[plugins]") }
    val versions = lines.subList(versionsIndex + 1, librariesIndex)
    val catalogVersions =
        versions
            .map { version ->
                val sanitizedVersion = version.replace(" ", "")
                val alias =
                    sanitizedVersion
                        .replaceAfter("=", "")
                        .dropLast(1)
                        .replace("=", "")
                        .removeSurrounding("\"")
                val isStrictly = sanitizedVersion.contains("strictly")
                val versionValue =
                    sanitizedVersion
                        .replaceAfterLast('"', "")
                        .dropLast(1)
                        .replaceBeforeLast('"', "")
                        .drop(1)
                Catalog.Version(alias = alias, strictly = isStrictly, version = versionValue)
            }
            .sortedBy { it.alias }
    val libraries = lines.subList(librariesIndex + 1, pluginsIndex)
    val catalogLibraries =
        libraries
            .map { library ->
                val sanitizedLibrary: String = library.replace(" ", "")
                val originalAlias: String = sanitizedLibrary.takeWhile { it != '=' }
                val module: String =
                    sanitizedLibrary
                        .replaceBefore('"', "")
                        .drop(1)
                        .replaceAfter('"', "")
                        .dropLast(1)
                val versionRef: String =
                    sanitizedLibrary
                        .replaceAfterLast('"', "")
                        .dropLast(1)
                        .replaceBeforeLast('"', "")
                        .drop(1)
                val sharedVersionRef = sanitizedLibrary.findTemplate("version.ref")
                Catalog.Library(
                    originalAlias = originalAlias,
                    module = module,
                    versionRef = versionRef,
                    sharedVersionRef = sharedVersionRef
                )
            }
            .sortedBy { it.alias }
    val plugins = lines.subList(pluginsIndex + 1, lines.size)
    val catalogPlugins =
        plugins
            .map { plugin ->
                val sanitizedPlugin = plugin.replace(" ", "")
                val originalAlias = sanitizedPlugin.takeWhile { it != '=' }
                val id =
                    sanitizedPlugin.replaceBefore('"', "").drop(1).replaceAfter('"', "").dropLast(1)
                val versionRef =
                    sanitizedPlugin
                        .replaceAfterLast('"', "")
                        .dropLast(1)
                        .replaceBeforeLast('"', "")
                        .drop(1)

                Catalog.Plugin(
                    originalAlias = originalAlias,
                    id = id,
                    versionRef = versionRef,
                    sharedVersionRef = sanitizedPlugin.findTemplate("version.ref"),
                )
            }
            .sortedBy { it.alias }

    return Catalog(catalogVersions, catalogLibraries, catalogPlugins)
}

internal data class Catalog(
    val versions: List<Version>,
    val libraries: List<Library>,
    val plugins: List<Plugin>,
) {

    data class Version(
        val alias: String,
        val strictly: Boolean,
        val version: String,
    )

    data class Library(
        val originalAlias: String,
        val module: String,
        val versionRef: String,
        val sharedVersionRef: String?,
    ) {
        val alias: String =
            buildString {
                    val (group, name) = module.split(":")
                    val isDomainDot = group.split(".").first().count() < 4
                    val sanitizedGroup =
                        if (isDomainDot) group.split(".").drop(1).joinToString(".") else group
                    append(sanitizedGroup.replace(".", "-"))
                    if (group != name) {
                        append("-")
                        append(name.replace(".", "-"))
                    }
                }
                .removeConsecutiveDuplicates()
                .fixAliasUsingReservedName()
    }

    data class Plugin(
        val originalAlias: String,
        val id: String,
        val versionRef: String,
        val sharedVersionRef: String?,
    ) {
        val alias: String =
            buildString {
                    val isDomainDot = id.split(".").first().count() < 4
                    val sanitizedId =
                        if (isDomainDot) id.split(".").drop(1).joinToString(".") else id
                    append(sanitizedId.replace(".", "-"))
                }
                .removeConsecutiveDuplicates()
                .fixAliasUsingReservedName()
    }

    fun checkIntegrity() {
        val versionErrors = buildList {
            for (version in versions) {
                val allVersionRefs: List<String> =
                    libraries.map(Library::versionRef) +
                        libraries.mapNotNull(Library::sharedVersionRef) +
                        plugins.map(Plugin::versionRef)
                if (version.alias !in allVersionRefs) {
                    add("Version ${version.version} with the alias ${version.alias} is not used")
                }
            }
        }
        require(versionErrors.isEmpty()) { versionErrors.joinToString("\n") }

        val libraryErrors = buildList {
            for (library in libraries) {
                if (library.module.endsWith("-gradle-plugin")) {
                    // add(
                    //    "Library ${library.module} is a Gradle plugin and should be in the " +
                    //        "plugins sections"
                    // )
                }
                if (library.versionRef !in versions.map(Version::alias)) {
                    val possibleFix =
                        """
                            version -> ${library.alias} = "${library.versionRef}"
                            library -> ${library.alias} = { module = "${library.module}", version.ref = "${library.alias}" }
                        """
                            .trimIndent()
                    add(
                        "Library ${library.module} has a versionRef ${library.versionRef} that is not " +
                            "in the versions list\n$possibleFix"
                    )
                }
                if (library.alias != library.originalAlias) {
                    add(
                        "Library with alias ${library.originalAlias} must use the " +
                            "alias ${library.alias}"
                    )
                }

                val librarySharedVersionRefOrAlias =
                    library.sharedVersionRef.takeIf { it == library.versionRef } ?: library.alias
                if (librarySharedVersionRefOrAlias !in versions.map(Version::alias)) {
                    add(
                        "Library with alias ${library.alias} has a versionRef " +
                            "${library.versionRef} that is not the same as the alias " +
                            "as is not shared"
                    )
                }
            }
        }
        val pluginErrors = buildList {
            for (plugin in plugins) {
                if (plugin.versionRef !in versions.map(Version::alias)) {
                    val possibleFix =
                        """
                            version -> ${plugin.alias} = "${plugin.versionRef}"
                            plugin -> ${plugin.alias} = { id = "${plugin.id}", version.ref = "${plugin.alias}" }
                        """
                            .trimIndent()
                    add(
                        "Plugin ${plugin.id} has a versionRef ${plugin.versionRef} that is not " +
                            "in the versions list\n$possibleFix"
                    )
                }

                if (plugin.alias != plugin.originalAlias) {
                    add(
                        "Plugin with alias ${plugin.originalAlias} must use the " +
                            "alias ${plugin.alias}"
                    )
                }

                val pluginSharedVersionRefOrAlias =
                    plugin.sharedVersionRef.takeIf { it == plugin.versionRef } ?: plugin.alias
                if (pluginSharedVersionRefOrAlias !in versions.map(Version::alias)) {
                    add(
                        "Plugin with alias ${plugin.alias} has a versionRef " +
                            "${plugin.versionRef} that is not the same as the alias as is " +
                            "not shared"
                    )
                }
            }
        }
        require(libraryErrors.isEmpty()) { (listOf("\n") + libraryErrors).joinToString("\n") }
        require(pluginErrors.isEmpty()) { (listOf("\n") + pluginErrors).joinToString("\n") }
    }
}

private fun String.removeConsecutiveDuplicates(): String =
    split("-").removeConsecutiveDuplicates().joinToString("-")

private fun <T> List<T>.removeConsecutiveDuplicates(): List<T> {
    val result = mutableListOf<T>()
    for (element in this) {
        if (result.isEmpty() || result.last() != element) {
            result.add(element)
        }
    }
    return result
}

private fun String.fixAliasUsingReservedName(): String = replace("class", "classs")

private fun String.findTemplate(name: String): String? {
    val string = this.replace(" ", "")
    val templateStartIndex = string.indexOf("{{$name")
    if (templateStartIndex == -1) return null
    val templateEndIndex = string.indexOf("}}", templateStartIndex)
    return string
        .substring(templateStartIndex, templateEndIndex)
        .replace("{", "")
        .replace(name, "")
        .replace("=", "")
        .replace("\"", "")
        .replace("}", "")
}
