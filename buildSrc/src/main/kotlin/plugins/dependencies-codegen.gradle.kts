import com.javiersc.gradle.extensions.version.catalogs.getLibraries
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import java.util.Locale
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val dependenciesCodegen: TaskCollection<Task> =
    tasks.maybeRegisterLazily<Task>("dependenciesCodegen")

the<KotlinProjectExtension>()
    .sourceSets["main"]
    .kotlin
    .srcDirs(buildDir.resolve("generated/main/kotlin"))

val catalog: VersionCatalog = the<VersionCatalogsExtension>().find("hubdleLibs").get()

val dependencies: List<MinimalExternalModuleDependency> =
    the<VersionCatalogsExtension>().getLibraries(catalog)

dependenciesCodegen.configureEach {
    group = "build"

    doLast {
        dependencies.forEach { minimalDependency ->
            val fileName = minimalDependency.module.toString().replace(":", "_")
            val dependencyVariableName =
                fileName.replace(".", "_").replace("-", "_").toUpperCase(Locale.getDefault())
            val dependencyVersion = minimalDependency.versionConstraint.displayName
            buildDir
                .resolve(
                    "generated/main/kotlin/" +
                        "com/javiersc/hubdle/extensions/dependencies/_internal/$fileName.kt"
                )
                .apply {
                    parentFile.mkdirs()
                    createNewFile()
                    writeText(
                        """
                            |package com.javiersc.hubdle.extensions.dependencies._internal
                            |
                            |internal const val ${dependencyVariableName}_LIBRARY: String =
                            |    "${minimalDependency.module}:$dependencyVersion"
                            |
                            |internal const val ${dependencyVariableName}_MODULE: String =
                            |    "${minimalDependency.module}"
                            |
                            |internal const val ${dependencyVariableName}_VERSION: String =
                            |    "$dependencyVersion"
                            |
                        """.trimMargin(),
                    )
                }
        }

        buildDir
            .resolve(
                "generated/main/kotlin/" +
                    "com/javiersc/hubdle/extensions/dependencies/_internal/hubdle_dependencies.kt"
            )
            .apply {
                parentFile.mkdirs()
                createNewFile()
                val dependenciesAsStringList =
                    dependencies
                        .map {
                            """"${it.module}:${it.versionConstraint.displayName}",""".prependIndent(
                                "        "
                            )
                        }
                writeText(
                    """
                        |package com.javiersc.hubdle.extensions.dependencies._internal
                        |
                        |internal val hubdleDependencies: List<String> =
                        |    listOf(
                        ${dependenciesAsStringList.map { "|$it" }.joinToString("\n")}
                        |    )
                        |    
                    """.trimMargin(),
                )
            }
    }
}

tasks.namedLazily<Task>(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configureEach {
    dependsOn(dependenciesCodegen)
}

tasks.withType<JavaCompile>().configureEach { dependsOn(dependenciesCodegen) }

tasks.withType<KotlinCompile>().configureEach { dependsOn(dependenciesCodegen) }
