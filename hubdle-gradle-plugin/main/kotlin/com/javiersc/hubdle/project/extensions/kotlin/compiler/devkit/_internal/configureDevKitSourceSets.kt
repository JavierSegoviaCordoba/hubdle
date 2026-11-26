package com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit._internal

import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.kotlin._internal.normalAndGeneratedDirs
import com.javiersc.kotlin.stdlib.decapitalize
import java.io.File
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Maps the source sets that the Kotlin Compiler Plugin DevKit creates to Hubdle's layout.
 *
 * Hubdle's layout has no `src` directory: a source set lives in `<prefix>/<compilation>`, for
 * example `common/main/kotlin`. The DevKit source sets map as follows:
 * - `<prefix>Main`, `<prefix>TestFixtures`, `<prefix>Test` → `<prefix>/<compilation>`, for example
 *   `commonTestFixtures` → `common/testFixtures`, `pre23DevMain` → `pre23Dev/main`.
 * - `generateTests` → `common/generateTests`, and `generateTests<Group>` → `<group>/generateTests`,
 *   for example `generateTestsCli` → `cli/generateTests`.
 * - The test source sets use `data` instead of `resources`, as the DevKit does: that is where the
 *   test data lives.
 *
 * The leaf source sets of each DevKit target (for example `k2421Main`) and `entryPoint` are not
 * touched: the DevKit leaves the former empty, and the latter only has the generated
 * `META-INF/services` files. The leaf `generateTests` source sets (for example
 * `k2421GenerateTests`) get the directory of their group (`src/generateTestsJs/kotlin` →
 * `js/generateTests/kotlin`) after the project is evaluated, because the DevKit sets their
 * directories after creating them.
 */
internal fun HubdleConfigurableExtension.configurableDevKitSrcDirs() = beforeConfigurable {
    val kotlin: KotlinProjectExtension =
        project.extensions.getByType(KotlinProjectExtension::class.java)

    kotlin.sourceSets.configureEach { sourceSet: KotlinSourceSet ->
        val directory: String = devKitSourceSetDirectory(sourceSet.name) ?: return@configureEach
        sourceSet.kotlin.setSrcDirs(emptySet<File>())
        sourceSet.kotlin.srcDirs(
            project.normalAndGeneratedDirs(project.provider { "$directory/kotlin" })
        )
        sourceSet.resources.setSrcDirs(emptySet<File>())
        if (sourceSet.isDevKitTest) {
            sourceSet.resources.srcDir("$directory/data")
        } else {
            sourceSet.resources.srcDirs(
                project.normalAndGeneratedDirs(project.provider { "$directory/resources" })
            )
        }
    }

    project.afterEvaluate { evaluatedProject ->
        kotlin.sourceSets
            .matching { DevKitLeafGenerateTestsRegex.matches(it.name) }
            .forEach { sourceSet ->
                sourceSet.kotlin.remapDevKitDefaultDirs(evaluatedProject)
                sourceSet.resources.remapDevKitDefaultDirs(evaluatedProject)
            }
    }
}

/**
 * The Hubdle directory of a DevKit source set, or `null` if Hubdle must not change its directories.
 */
internal fun devKitSourceSetDirectory(name: String): String? =
    when {
        name == DevKitEntryPoint -> null
        DevKitLeafRegex.matches(name) -> null
        name == DevKitGenerateTests -> "common/$DevKitGenerateTests"
        name.startsWith(DevKitGenerateTests) -> {
            val group: String = name.removePrefix(DevKitGenerateTests).decapitalize()
            "$group/$DevKitGenerateTests"
        }
        else -> {
            val compilation: String? = DevKitCompilationSuffixes.firstOrNull { suffix ->
                name.endsWith(suffix) && name != suffix
            }
            if (compilation != null) {
                "${name.removeSuffix(compilation)}/${compilation.decapitalize()}"
            } else {
                name
            }
        }
    }

/** The test data directories of the DevKit test source sets, for example `common/test/data`. */
internal fun Project.devKitTestDataDirs(): Provider<List<File>> = provider {
    extensions
        .getByType(KotlinProjectExtension::class.java)
        .sourceSets
        .filter { it.isDevKitTest && devKitSourceSetDirectory(it.name) != null }
        .flatMap { it.resources.srcDirs }
}

/** Same rule as the DevKit, which puts the test data in the resources of these source sets. */
private val KotlinSourceSet.isDevKitTest: Boolean
    get() = name.endsWith("Test")

/**
 * Replaces each DevKit default directory (`src/<sourceSetName>/<kind>`) with its Hubdle directory,
 * keeping any other directory.
 */
private fun SourceDirectorySet.remapDevKitDefaultDirs(project: Project) {
    val srcDir: File = project.projectDir.resolve("src")
    val remapped: List<File> = srcDirs.map { dir: File ->
        if (!dir.startsWith(srcDir)) return@map dir
        val relativePath: String = dir.relativeTo(srcDir).invariantSeparatorsPath
        val sourceSetName: String = relativePath.substringBefore('/')
        val kind: String = relativePath.substringAfter('/', missingDelimiterValue = "")
        val directory: String = devKitSourceSetDirectory(sourceSetName) ?: return@map dir
        project.projectDir.resolve("$directory/$kind")
    }
    setSrcDirs(remapped)
}

private const val DevKitEntryPoint = "entryPoint"

private const val DevKitGenerateTests = "generateTests"

/** The order matters: `TestFixtures` must be checked before `Test`. */
private val DevKitCompilationSuffixes: List<String> = listOf("TestFixtures", "Test", "Main")

/**
 * The DevKit target names are `k` + the version, lowercased, without `.` and with `-` replaced by
 * `_`, for example `k2420_dev_7885`.
 */
private const val DevKitTargetPattern = """k\d[0-9a-z_]*"""

private val DevKitLeafRegex =
    Regex("""^$DevKitTargetPattern(Main|TestFixtures|Test|GenerateTests)$""")

private val DevKitLeafGenerateTestsRegex = Regex("""^${DevKitTargetPattern}GenerateTests$""")
