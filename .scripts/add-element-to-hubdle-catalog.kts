import java.io.File

val catalogFile = File("").absoluteFile.resolve("gradle/hubdle.libs.versions.toml")
val catalog = catalogFile.buildCatalog()

println("Arguments: ${args.joinToString(" | ")}")

addLibraryOrPlugin()

fun addLibraryOrPlugin() {
    val module = findArg("module")
    val id = findArg("id")
    val version = findArg("version")
    val versionRef = findArg("versionRef")
    val ignore = findArg("ignore")?.toBoolean() ?: false

    if (module == null && id == null) error("You must provide a module or an id")
    if (module != null && id != null) error("You must provide a module or an id, not both")

    if (module != null) {
        require(module.count { it == ':' } == 1) { "The module must be in the format group:name" }
        addElement(
            module = module,
            id = null,
            version = version,
            versionRef = versionRef,
            ignore = ignore,
        )
    } else {
        addElement(
            module = null,
            id = id,
            version = version,
            versionRef = versionRef,
            ignore = ignore,
        )
    }
}

fun addElement(
    module: String?,
    id: String?,
    version: String?,
    versionRef: String?,
    ignore: Boolean
) {
    val isLibrary = module != null
    val isPlugin = id != null
    require(isLibrary || isPlugin) { "You must provide a module or an id" }

    val hasVersionRef = versionRef in catalog.versions.map(Version::alias)
    val hasVersion = version?.isNotBlank() ?: false
    val hasBoth = hasVersionRef && hasVersion
    require((hasVersionRef || hasVersion)) {
        "The versionRef $versionRef is not in the list or the version $version is not valid"
    }
    require(!hasBoth) { "You can't provide both a version and a versionRef" }

    val libraryAlias = module?.let(::buildLibraryAlias)
    val pluginAlias = id?.let(::buildPluginAlias)
    val alias = libraryAlias ?: pluginAlias ?: error("Impossible")
    val finalVersionRef = versionRef ?: alias
    val catalogVersion = version?.let { Version(finalVersionRef, false, it) }
    val catalogLibrary =
        module?.let {
            Library(
                originalAlias = alias,
                module = module,
                versionRef = finalVersionRef,
                sharedVersionRef = versionRef,
                ignore = ignore,
            )
        }
    val catalogPlugin =
        id?.let {
            Plugin(
                originalAlias = alias,
                id = id,
                versionRef = finalVersionRef,
                sharedVersionRef = versionRef,
                ignore = ignore,
            )
        }
    val updatedCatalog =
        catalog.copy(
            versions = (listOfNotNull(catalogVersion) + catalog.versions).distinct(),
            libraries = (listOfNotNull(catalogLibrary) + catalog.libraries).distinct(),
            plugins = (listOfNotNull(catalogPlugin) + catalog.plugins).distinct(),
        )
    println("------------------------------------------------")
    println("Updated catalog:\n$updatedCatalog")
    println("------------------------------------------------")
    catalogFile.writeText(updatedCatalog.toString())
}

fun findArg(name: String): String? =
    args.find { it.startsWith(name) }?.removePrefix("$name=").takeIf { it?.isNotBlank() == true }

fun File.buildCatalog(): Catalog {
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
                Version(alias = alias, strictly = isStrictly, version = versionValue)
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
                Library(
                    originalAlias = originalAlias,
                    module = module,
                    versionRef = versionRef,
                    sharedVersionRef = sharedVersionRef,
                    ignore = sanitizedLibrary.findTemplate("ignore") != null,
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

                Plugin(
                    originalAlias = originalAlias,
                    id = id,
                    versionRef = versionRef,
                    sharedVersionRef = sanitizedPlugin.findTemplate("version.ref"),
                    ignore = sanitizedPlugin.findTemplate("ignore") != null,
                )
            }
            .sortedBy { it.alias }

    return Catalog(catalogVersions, catalogLibraries, catalogPlugins)
}

data class Catalog(
    val versions: List<Version>,
    val libraries: List<Library>,
    val plugins: List<Plugin>,
) {

    override fun toString(): String = buildString {
        appendLine("[versions]")
        for (version in versions.sortedBy(Version::alias)) {
            appendLine(version)
        }
        appendLine()
        appendLine()
        appendLine("[libraries]")
        for (library in libraries.sortedBy(Library::alias)) {
            appendLine(library)
        }
        appendLine()
        appendLine()
        appendLine("[plugins]")
        for (plugin in plugins.sortedBy(Plugin::alias)) {
            appendLine(plugin)
        }
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

data class Version(
    val alias: String,
    val strictly: Boolean,
    val version: String,
) {

    override fun toString(): String =
        if (strictly) """$alias = { strictly = "$version" }""" else """$alias = "$version""""
}

data class Library(
    val originalAlias: String,
    val module: String,
    val versionRef: String,
    val sharedVersionRef: String?,
    val ignore: Boolean,
) {

    val alias: String = buildLibraryAlias(module)

    override fun toString(): String =
        when {
            ignore -> {
                """$originalAlias = { module = "$module", version.ref = "$versionRef" }"""
                    .appendTemplates("ignore" to ignore, "version.ref" to sharedVersionRef)
            }
            else -> {
                """$alias = { module = "$module", version.ref = "$versionRef" }"""
                    .appendTemplates("ignore" to ignore, "version.ref" to sharedVersionRef)
            }
        }
}

data class Plugin(
    val originalAlias: String,
    val id: String,
    val versionRef: String,
    val sharedVersionRef: String?,
    val ignore: Boolean,
) {

    val alias: String = buildPluginAlias(id)

    override fun toString(): String =
        when {
            ignore -> {
                """$originalAlias = { id = "$id", version.ref = "$versionRef" }"""
                    .appendTemplates("ignore" to ignore, "version.ref" to sharedVersionRef)
            }
            else -> {
                """$alias = { id = "$id", version.ref = "$versionRef" }"""
                    .appendTemplates("ignore" to ignore, "version.ref" to sharedVersionRef)
            }
        }
}

fun buildLibraryAlias(module: String): String =
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

fun buildPluginAlias(id: String): String =
    buildString {
            val isDomainDot = id.split(".").first().count() < 4
            val sanitizedId = if (isDomainDot) id.split(".").drop(1).joinToString(".") else id
            append(sanitizedId.replace(".", "-"))
        }
        .removeConsecutiveDuplicates()
        .fixAliasUsingReservedName()

fun String.removeConsecutiveDuplicates(): String =
    split("-").removeConsecutiveDuplicates().joinToString("-")

fun <T> List<T>.removeConsecutiveDuplicates(): List<T> {
    val result = mutableListOf<T>()
    for (element in this) {
        if (result.isEmpty() || result.last() != element) {
            result.add(element)
        }
    }
    return result
}

fun String.fixAliasUsingReservedName(): String = replace("class", "classs")

fun String.appendTemplates(vararg values: Pair<String, Any?>): String {
    val templates =
        values
            .mapNotNull { (name, value) ->
                when {
                    value == null -> null
                    name == "ignore" && value?.toString()?.toBoolean() == false -> null
                    else -> """{{ $name = "$value" }}"""
                }
            }
            .joinToString(" ")
    return when {
        templates.isBlank() -> this
        this.contains("#") -> "$this $templates"
        else -> "$this # $templates"
    }
}

fun String.findTemplate(name: String): String? {
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
