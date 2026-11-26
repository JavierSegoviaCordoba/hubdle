package com.javiersc.hubdle.project.extensions.gradle

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.PluginId
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.compiler.plugin.devkit.GradlePluginConfigExtension

/**
 * The Gradle plugin of a Kotlin compiler plugin, the one that applies the compiler plugin to the
 * users' projects, built with the DevKit `gradle-plugin` plugin.
 *
 * The DevKit runs the functional tests against every registered Kotlin version, one
 * `<version>FunctionalTest` task each, and `allVersionedFunctionalTests` runs all of them. The
 * functional tests are in `testFunctional/kotlin`, as in any other Hubdle project. The settings
 * must apply the DevKit settings plugin, with the Gradle property
 * `hubdle.kotlin.compiler.plugin.devkit=true`.
 */
@HubdleDslMarker
public open class HubdleGradlePluginDevKitExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleGradlePlugin, hubdleKotlinJvm)

    /**
     * The compiler plugin that this Gradle plugin applies: a project path (`:my-compiler-plugin`),
     * the artifact of a project with the same group and version (`my-compiler-plugin`), or the
     * coordinates (`com.example:my-compiler-plugin:1.0.0`).
     */
    public val compilerPlugin: Property<String> = propertyOptional()

    @HubdleDslMarker
    public fun compilerPlugin(value: String) {
        compilerPlugin.set(value)
    }

    @HubdleDslMarker
    public fun compilerPlugin(project: ProjectDependency) {
        compilerPlugin.set(project.path)
    }

    /** The lowest Kotlin version to run the functional tests against. */
    public val cliMinVersion: Property<String> = propertyOptional()

    /** The highest Kotlin version to run the functional tests against, unbounded if absent. */
    public val cliMaxVersion: Property<String> = propertyOptional()

    /**
     * Runs the functional tests against every stable Kotlin version from [min] to [max] (unbounded
     * if `null`), and the Beta and RC versions of the newest one, the release in preparation.
     *
     * Without it, the DevKit only uses the Kotlin Gradle Plugin version of the build.
     */
    @HubdleDslMarker
    public fun cliVersions(min: String, max: String? = null) {
        cliMinVersion.set(min)
        cliMaxVersion.set(max)
    }

    /** The plugin ID and the package of the generated `PluginInfo`. The default is the group. */
    public val pluginPackage: Property<String> = propertyOptional()

    @HubdleDslMarker
    public fun pluginPackage(value: String) {
        pluginPackage.set(value)
    }

    /** Configures the DevKit extension (`pluginDevKit { }`) directly. */
    @HubdleDslMarker
    public fun devKit(action: Action<GradlePluginConfigExtension>) {
        lazyConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinCompilerPluginDevKitGradlePlugin,
        )

        beforeConfigurable {
            check(!hubdleKotlinJvm.isTestFunctionalEnabled.get()) {
                """
                |The Kotlin Compiler Plugin DevKit already creates the functional tests, in
                |`$TEST_FUNCTIONAL/kotlin`. Remove `testFunctional { }`, and configure them with
                |`kotlin { jvm { kotlin { sourceSets.named("$DevKitFunctionalTest") { } } } }`.
                """
                    .trimMargin()
            }
        }

        lazyConfigurable {
            remapFunctionalTestDirs()
            configureDevKit(the())
        }
    }

    private fun configureDevKit(devKit: GradlePluginConfigExtension) {
        pluginPackage.orNull?.let { devKit.pluginPackage.set(it) }

        val cliMin: String? = cliMinVersion.orNull
        if (cliMin != null) devKit.cliVersions(min = cliMin, max = cliMaxVersion.orNull)

        val compilerPlugin: String? = compilerPlugin.orNull
        when {
            compilerPlugin == null -> {}
            compilerPlugin.startsWith(":") -> devKit.compilerPlugin(project.project(compilerPlugin))
            else -> devKit.compilerPlugin(compilerPlugin)
        }
    }

    /**
     * The testkit plugin of the DevKit creates the `functionalTest` source set, that Hubdle puts in
     * `functionalTest`. This moves it to `testFunctional`, the directory of the functional tests in
     * Hubdle.
     */
    private fun Project.remapFunctionalTestDirs() {
        val sourceSet: SourceSet =
            the<SourceSetContainer>().findByName(DevKitFunctionalTest) ?: return
        val kotlin: SourceDirectorySet? =
            (sourceSet as ExtensionAware).extensions.findByName("kotlin") as? SourceDirectorySet
        for (directories in listOfNotNull(sourceSet.java, kotlin, sourceSet.resources)) {
            directories.setSrcDirs(directories.srcDirs.map(::remapFunctionalTestDir))
        }
    }

    private fun remapFunctionalTestDir(dir: File): File {
        val generatedDir: File = project.layout.buildDirectory.dir("generated").get().asFile
        for (root in listOf(project.projectDir, generatedDir)) {
            val relativePath: String =
                dir.relativeToOrNull(root)?.invariantSeparatorsPath ?: continue
            if (relativePath.startsWith("$DevKitFunctionalTest/")) {
                return root.resolve(
                    TEST_FUNCTIONAL + relativePath.removePrefix(DevKitFunctionalTest)
                )
            }
        }
        return dir
    }
}

/** The functional test source set of the DevKit, created by the testkit plugin. */
internal const val DevKitFunctionalTest = "functionalTest"

internal val HubdleEnableableExtension.hubdleGradlePluginDevKit: HubdleGradlePluginDevKitExtension
    get() = getHubdleExtension()
