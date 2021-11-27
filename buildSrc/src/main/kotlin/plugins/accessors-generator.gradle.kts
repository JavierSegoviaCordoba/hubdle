import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

file("${rootProject.rootDir}/all-plugins/src/main/kotlin/").deleteRecursively()

allprojects {
    afterEvaluate {
        val extension = extensions.findByType<GradlePluginDevelopmentExtension>()
        val id = extension?.plugins?.asMap?.values?.map(PluginDeclaration::getId)?.firstOrNull()

        if (id != null && id.startsWith("com.javiersc.gradle.plugins")) {
            val name = id.replace("com.javiersc.gradle.plugins.", "")
            val fileName =
                "${name.split(".")
                    .joinToString(transform = String::capitalize)
                    .replace(",", "")
                    .replace(" ", "")}Accessors.kt"

            file("${rootProject.rootDir}/all-plugins/src/main/kotlin/$fileName").apply {
                ensureParentDirsCreated()
                createNewFile()
                writeText(buildAccessorFile(name))
            }
        }
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
