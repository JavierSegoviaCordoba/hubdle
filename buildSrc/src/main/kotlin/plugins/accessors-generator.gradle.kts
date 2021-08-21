val names =
    file("$projectDir/src/main/kotlin")
        .walkTopDown()
        .filter { it.isFile && it.name.contains("com.javiersc.gradle.plugins") }
        .map { it.name.replace("com.javiersc.gradle.plugins.", "") }
        .map { it.replace(".gradle.kts", "") }
        .toList()

names.forEach { name ->
    val fileName =
        "${name.split(".").joinToString { word -> word.capitalize() }.replace(",", "").replace(" ", "")}Accessors.kt"
    file("$projectDir/src/main/kotlin/$fileName").apply {
        if (!exists()) {
            parentFile.mkdirs()
            createNewFile()
        }
        writeText(buildAccessorFile(name))
    }
}

fun buildAccessorFile(name: String): String {
    val accessor = name.replace(".", "-")
    return """
        @file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")
        
        import org.gradle.plugin.use.PluginDependenciesSpec
        import org.gradle.plugin.use.PluginDependencySpec

        val PluginDependenciesSpec.`javiersc-$accessor`: PluginDependencySpec
            get() = javiersc("$name")

        fun PluginDependenciesSpec.`javiersc-$accessor`(
            version: String,
        ): PluginDependencySpec = javiersc("$name", version)

    """.trimIndent()
}
