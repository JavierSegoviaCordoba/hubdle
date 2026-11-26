package com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.publishing.maven.configurableMavenPublishing
import com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit._internal.configurableDevKitSrcDirs
import com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit._internal.devKitTestDataDirs
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.compiler.plugin.devkit.DevKitCompilerConfigExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * Base of the projects built with the
 * [Kotlin Compiler Plugin DevKit](https://github.com/Kotlin/compiler-plugin-dev-kit).
 *
 * The DevKit applies the Kotlin `multiplatform` plugin and registers one target per Kotlin compiler
 * version, so Hubdle adds no regular target (`jvm()`, `js()`…). The settings must apply the DevKit
 * settings plugin, with the Gradle property `hubdle.kotlin.compiler.plugin.devkit=true`.
 */
public abstract class HubdleKotlinCompilerDevKitExtension(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    override val targetName: String = "common"

    internal abstract val devKitPluginId: PluginId

    /** The lowest Kotlin CLI version to compile and test against. */
    public val cliMinVersion: Property<String> = propertyOptional()

    /** The highest Kotlin CLI version to compile and test against, unbounded if absent. */
    public val cliMaxVersion: Property<String> = propertyOptional()

    /**
     * Adds every stable Kotlin version from [min] to [max] (unbounded if `null`), and the Beta and
     * RC versions of the newest one, the release in preparation.
     *
     * Without it, the DevKit registers the Kotlin Gradle Plugin version of the build.
     */
    @HubdleDslMarker
    public fun cliVersions(min: String, max: String? = null) {
        cliMinVersion.set(min)
        cliMaxVersion.set(max)
    }

    /**
     * Compile and test against the Kotlin compilers bundled in the IDEs of the IDE range. Enabled
     * by default: without an IDE range, an internal compiler API break only shows up inside the
     * IDE.
     */
    public val ideaVersionsEnabled: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun ideaVersionsEnabled(value: Boolean) {
        ideaVersionsEnabled.set(value)
    }

    /** The lowest IDE build, for example `263` for IntelliJ IDEA 2026.3. */
    public val ideaMinVersion: Property<String> = property { DefaultIdeaMinVersion }

    /** The highest IDE major build, for example `263`, unbounded if absent. */
    public val ideaMaxVersion: Property<Int> = propertyOptional()

    public val ideaIncludeRc: Property<Boolean> = property { true }

    public val ideaIncludeEap: Property<Boolean> = property { true }

    /** Sets the IDE range. The default is IntelliJ IDEA 2026.3+, RC and EAP included. */
    @HubdleDslMarker
    public fun ideaVersions(
        min: String,
        max: Int? = null,
        includeRc: Boolean = true,
        includeEap: Boolean = true,
    ) {
        ideaMinVersion.set(min)
        ideaMaxVersion.set(max)
        ideaIncludeRc.set(includeRc)
        ideaIncludeEap.set(includeEap)
    }

    /** The plugin ID and the package of the generated classes. The DevKit default is the group. */
    public val pluginPackage: Property<String> = propertyOptional()

    @HubdleDslMarker
    public fun pluginPackage(value: String) {
        pluginPackage.set(value)
    }

    /** Configures the DevKit extension (`pluginDevKit { }`) directly. */
    @HubdleDslMarker
    public fun devKit(action: Action<DevKitCompilerConfigExtension>) {
        lazyConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun kotlin(action: Action<KotlinMultiplatformExtension>) {
        lazyConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = devKitPluginId)

        configurableDevKitSrcDirs()

        configurableMavenPublishing {
            it.configure(KotlinMultiplatform(javadocJar = JavadocJar.Empty(), sourcesJar = true))
        }

        lazyConfigurable {
            configureOptIns()
            configureDevKit(the())

            tasks
                .register<DeleteCompilerTextTestFiles>(DeleteCompilerTextTestFiles.NAME)
                .configure { task ->
                    task.testDataDirectories.from(devKitTestDataDirs())
                }
        }
    }

    internal open fun configureDevKit(devKit: DevKitCompilerConfigExtension) {
        pluginPackage.orNull?.let { devKit.pluginPackage.set(it) }

        val cliMin: String? = cliMinVersion.orNull
        if (cliMin != null) devKit.cliVersions(min = cliMin, max = cliMaxVersion.orNull)

        if (ideaVersionsEnabled.get()) {
            devKit.ideaVersions(
                min = ideaMinVersion.get(),
                max = ideaMaxVersion.orNull,
                includeRc = ideaIncludeRc.get(),
                includeEap = ideaIncludeEap.get(),
            )
        }
    }

    /** The DevKit already adds `ExperimentalCompilerApi` and `UnsafeDuringIrConstructionAPI`. */
    private fun configureOptIns() {
        the<KotlinProjectExtension>().sourceSets.configureEach { sourceSet ->
            sourceSet.languageSettings { settings ->
                for (optIn in CompilerOptIns) settings.optIn(optIn)
            }
        }
    }
}

private const val DefaultIdeaMinVersion = "263"

private val CompilerOptIns: List<String> =
    listOf(
        "org.jetbrains.kotlin.diagnostics.InternalDiagnosticFactoryMethod",
        "org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI",
        "org.jetbrains.kotlin.fir.PrivateForInline",
        "org.jetbrains.kotlin.fir.resolve.dfa.DfaInternals",
        "org.jetbrains.kotlin.fir.resolve.transformers.AdapterForResolveProcessor",
        "org.jetbrains.kotlin.fir.symbols.SymbolInternals",
    )
