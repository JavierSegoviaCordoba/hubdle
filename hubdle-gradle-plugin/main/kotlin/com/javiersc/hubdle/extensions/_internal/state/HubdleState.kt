@file:Suppress("MagicNumber")

package com.javiersc.hubdle.extensions._internal.state

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.javiersc.hubdle.extensions._internal.state.LaterConfigurable.Priority
import com.javiersc.hubdle.extensions.config.analysis._internal.configureAnalysis
import com.javiersc.hubdle.extensions.config.analysis._internal.configureConfigAnalysisRawConfig
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator._internal.configureBinaryCompatibilityValidator
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator._internal.configureConfigBinaryCompatibilityValidatorRawConfig
import com.javiersc.hubdle.extensions.config.coverage._internal.configureCoverage
import com.javiersc.hubdle.extensions.config.coverage._internal.configureKotlinCoverageRawConfig
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.configureChangelog
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.configureConfigDocumentationChangelogRawConfig
import com.javiersc.hubdle.extensions.config.documentation.readme._internal.configureReadmeBadges
import com.javiersc.hubdle.extensions.config.documentation.site._internal.configureConfigDocumentationSiteRawConfig
import com.javiersc.hubdle.extensions.config.documentation.site._internal.configureSite
import com.javiersc.hubdle.extensions.config.format._internal.configureFormat
import com.javiersc.hubdle.extensions.config.format._internal.configureKotlinFormatRawConfig
import com.javiersc.hubdle.extensions.config.install._internal.configureInstallPreCommits
import com.javiersc.hubdle.extensions.config.language.settings._internal.configureConfigLanguageSettings
import com.javiersc.hubdle.extensions.config.nexus._internal.configureNexus
import com.javiersc.hubdle.extensions.config.publishing._internal.configureKotlinPublishingRawConfig
import com.javiersc.hubdle.extensions.config.versioning._internal.configureConfigVersioningRawConfig
import com.javiersc.hubdle.extensions.config.versioning._internal.configureVersioning
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_FACEBOOK_KTFMT_VERSION
import com.javiersc.hubdle.extensions.kotlin.android.application._internal.configureAndroidApplication
import com.javiersc.hubdle.extensions.kotlin.android.application._internal.configureKotlinAndroidApplicationRawConfig
import com.javiersc.hubdle.extensions.kotlin.android.library._internal.configureAndroidLibrary
import com.javiersc.hubdle.extensions.kotlin.android.library._internal.configureKotlinAndroidLibraryRawConfig
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureGradlePlugin
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureKotlinGradlePluginRawConfig
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog._internal.configureGradleVersionCatalog
import com.javiersc.hubdle.extensions.kotlin.intellij._internal.configureIntelliJ
import com.javiersc.hubdle.extensions.kotlin.intellij._internal.configureKotlinIntellijRawConfig
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions.Companion.JVM_VERSION
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.configureJvm
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.configureKotlinJvmRawConfig
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatform
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformAndroid
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformAndroidRawConfig
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformDarwin
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSArm32
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSSimulatorArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformJs
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformJvm
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformJvmAndAndroid
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformLinux
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformLinuxArm32Hfp
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformLinuxArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformLinuxMips32
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformLinuxMipsel32
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformLinuxX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformMacOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformMacOSArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformMacOSX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformMinGW
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformMinGWX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformMinGWX86
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformNative
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformRawConfig
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformTvOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformTvOSArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformTvOSSimulatorArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformTvOSX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWAsm
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWAsm32
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWatchOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWatchOSArm32
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWatchOSArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWatchOSSimulatorArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWatchOSX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformWatchOSX86
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJsExtension
import com.javiersc.semver.gradle.plugin.SemverExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import kotlinx.kover.api.KoverExtension
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.plugins.JavaApplication
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension as GradlePublishingExtension
import org.gradle.internal.os.OperatingSystem
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.intellij.IntelliJPluginExtension
import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.intellij.tasks.PublishPluginTask
import org.jetbrains.intellij.tasks.SignPluginTask
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KotlinMultiplatformProjectExtension
import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

private val hubdleStateCache: MutableMap<Project, HubdleState> = mutableMapOf()

internal val Project.hubdleState: HubdleState
    get() {
        if (hubdleStateCache[this] == null) hubdleStateCache[this] = HubdleState()
        return checkNotNull(hubdleStateCache[this]) {
            "HubdleState for the project $path doesn't exist"
        }
    }

internal data class HubdleState(
    val config: Config = Config(),
    val kotlin: Kotlin = Kotlin(),
) : Configurable {

    private val laterConfigurables: MutableList<LaterConfigurable> = mutableListOf()

    override fun configure(project: Project) {
        config.configure(project)
        kotlin.configure(project)
        laterConfigurables.sortedBy(LaterConfigurable::priority).forEach {
            it.configureLater().configure(project)
        }
    }

    data class Config(
        val analysis: Analysis = Analysis(),
        val binaryCompatibilityValidator: BinaryCompatibilityValidator =
            BinaryCompatibilityValidator(),
        val coverage: Coverage = Coverage(),
        val documentation: Documentation = Documentation(),
        var explicitApiMode: ExplicitApiMode = ExplicitApiMode.Disabled,
        val languageSettings: LanguageSettings = LanguageSettings(),
        val install: Install = Install(),
        val format: Format = Format(),
        val nexus: Nexus = Nexus(),
        val publishing: Publishing = Publishing(),
        val versioning: Versioning = Versioning(),
    ) : Configurable {

        override fun configure(project: Project) {
            versioning.configure(project)
            analysis.configure(project)
            binaryCompatibilityValidator.configure(project)
            coverage.configure(project)
            documentation.configure(project)
            install.configure(project)
            nexus.configure(project)
            publishing.configure(project)
            project.hubdleState.laterConfigurables += format
            project.hubdleState.laterConfigurables += languageSettings
        }

        data class Analysis(
            override var isEnabled: Boolean = false,
            var ignoreFailures: Boolean = true,
            val includes: MutableList<String> = mutableListOf("**/*.kt", "**/*.kts"),
            val excludes: MutableList<String> = mutableListOf("**/resources/**", "**/build/**"),
            val reports: Reports = Reports(),
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) {
                configureAnalysis(project)
                rawConfig.configure(project)
            }

            data class Reports(
                var html: Boolean = true,
                var sarif: Boolean = true,
                var txt: Boolean = false,
                var xml: Boolean = true,
            )

            data class RawConfig(
                var detekt: Action<DetektExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureConfigAnalysisRawConfig(project)
            }
        }

        data class BinaryCompatibilityValidator(
            override var isEnabled: Boolean = false,
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) {
                configureBinaryCompatibilityValidator(project)
            }

            data class RawConfig(
                var apiValidation: Action<ApiValidationExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) =
                    configureConfigBinaryCompatibilityValidatorRawConfig(project)
            }
        }

        data class Coverage(
            override var isEnabled: Boolean = false,
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) {
                configureCoverage(project)
                rawConfig.configure(project)
            }

            data class RawConfig(
                var kover: Action<KoverExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureKotlinCoverageRawConfig(project)
            }
        }

        data class Documentation(
            val changelog: Changelog = Changelog(),
            val readme: Readme = Readme(),
            val site: Site = Site()
        ) : Configurable {

            override fun configure(project: Project) {
                changelog.configure(project)
                readme.configure(project)
                site.configure(project)
            }

            data class Changelog(
                override var isEnabled: Boolean = false,
                val rawConfig: RawConfig = RawConfig(),
            ) : Enableable, Configurable {
                override fun configure(project: Project) {
                    configureChangelog(project)
                    rawConfig.configure(project)
                }

                data class RawConfig(
                    var changelog: Action<ChangelogPluginExtension>? = null,
                ) : Configurable {
                    override fun configure(project: Project) =
                        configureConfigDocumentationChangelogRawConfig(project)
                }
            }

            data class Readme(
                val badges: Badges = Badges(),
            ) : Configurable {

                override fun configure(project: Project) = badges.configure(project)

                data class Badges(
                    override var isEnabled: Boolean = false,
                    var kotlin: Boolean = true,
                    var mavenCentral: Boolean = true,
                    var snapshots: Boolean = true,
                    var build: Boolean = true,
                    var coverage: Boolean = true,
                    var quality: Boolean = true,
                    var techDebt: Boolean = true,
                ) : Enableable, Configurable {

                    override fun configure(project: Project) = configureReadmeBadges(project)
                }
            }

            data class Site(
                override var isEnabled: Boolean = false,
                val excludes: MutableList<ProjectDependency> = mutableListOf(),
                val reports: Reports = Reports(),
                val rawConfig: RawConfig = RawConfig(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) {
                    configureSite(project)
                    rawConfig.configure(project)
                }

                data class Reports(
                    var allTests: Boolean = true,
                    var codeAnalysis: Boolean = true,
                    var codeCoverage: Boolean = true,
                    var codeQuality: Boolean = true,
                )

                data class RawConfig(
                    var mkdocs: Action<MkdocsExtension>? = null,
                ) : Configurable {
                    override fun configure(project: Project) =
                        configureConfigDocumentationSiteRawConfig(project)
                }
            }
        }

        data class Format(
            override var isEnabled: Boolean = true,
            val includes: MutableList<String> = mutableListOf(),
            val excludes: MutableList<String> = mutableListOf(),
            var ktfmtVersion: String = COM_FACEBOOK_KTFMT_VERSION,
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, LaterConfigurable {

            override val priority: Priority = Priority.VeryLow

            override fun configureLater(): Configurable = Configurable {
                configureFormat(it)
                rawConfig.configure(it)
            }

            data class RawConfig(
                var spotless: Action<SpotlessExtension>? = null,
                var spotlessPredeclare: Action<SpotlessExtensionPredeclare>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureKotlinFormatRawConfig(project)
            }
        }

        data class LanguageSettings(
            var experimentalContracts: Boolean = false,
            var experimentalCoroutinesApi: Boolean = false,
            var experimentalSerializationApi: Boolean = false,
            var experimentalStdlibApi: Boolean = true,
            var experimentalTime: Boolean = false,
            var flowPreview: Boolean = false,
            val optIns: MutableList<String> = mutableListOf(),
            var requiresOptIn: Boolean = true,
            var rawConfig: RawConfig = RawConfig(),
        ) : LaterConfigurable {
            override fun configureLater(): Configurable = Configurable {
                configureConfigLanguageSettings(it)
            }

            data class RawConfig(
                var languageSettings: Action<LanguageSettingsBuilder>? = null,
            )
        }

        data class Install(
            val preCommits: PreCommits = PreCommits(),
        ) : Configurable {

            override fun configure(project: Project) = configureInstallPreCommits(project)

            data class PreCommits(
                override var isEnabled: Boolean = false,
                var allTests: Boolean = false,
                var applyFormat: Boolean = false,
                var assemble: Boolean = false,
                var checkAnalysis: Boolean = false,
                var checkFormat: Boolean = false,
                var checkApi: Boolean = false,
                var dumpApi: Boolean = false,
            ) : Enableable
        }

        data class Nexus(
            override var isEnabled: Boolean = false,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureNexus(project)
        }

        data class Publishing(
            override var isEnabled: Boolean = false,
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {
            override fun configure(project: Project) {
                rawConfig.configure(project)
            }

            data class RawConfig(
                var publishing: Action<GradlePublishingExtension>? = null,
                var signing: Action<SigningExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) =
                    configureKotlinPublishingRawConfig(project)
            }
        }

        data class Versioning(
            override var isEnabled: Boolean = true,
            var tagPrefix: String = "",
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {
            override fun configure(project: Project) {
                configureVersioning(project)
                rawConfig.configure(project)
            }

            data class RawConfig(
                var semver: Action<SemverExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) =
                    configureConfigVersioningRawConfig(project)
            }
        }
    }

    data class Kotlin(
        val android: Android = Android(),
        val gradle: Gradle = Gradle(),
        val intellij: IntelliJ = IntelliJ(),
        val jvm: Jvm = Jvm(),
        val multiplatform: Multiplatform = Multiplatform(),
        var jvmVersion: Int = JVM_VERSION,
    ) : Configurable {

        val isEnabled: Boolean
            get() =
                android.isEnabled || gradle.isEnabled || jvm.isEnabled || multiplatform.isEnabled

        override fun configure(project: Project) {
            android.configure(project)
            gradle.configure(project)
            intellij.configure(project)
            jvm.configure(project)
            multiplatform.configure(project)
        }

        data class Android(
            var compileSdk: Int = 32,
            val application: Application = Application(),
            val library: Library = Library(),
            var minSdk: Int = 21,
        ) : Configurable {

            val isEnabled: Boolean
                get() = library.isEnabled || application.isEnabled

            override fun configure(project: Project) {
                application.configure(project)
                library.configure(project)
            }

            data class Application(
                override var isEnabled: Boolean = false,
                var applicationId: String? = null,
                var versionCode: Int? = null,
                var versionName: String? = null,
                var features: Features = Features(),
                var rawConfig: RawConfig = RawConfig(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) {
                    configureAndroidApplication(project)
                    rawConfig.configure(project)
                }

                data class RawConfig(
                    var android: Action<ApplicationExtension>? = null,
                ) : Configurable {
                    override fun configure(project: Project) =
                        configureKotlinAndroidApplicationRawConfig(project)
                }

                data class Features(
                    var coroutines: Boolean = false,
                    var extendedStdlib: Boolean = true,
                    var extendedTesting: Boolean = true,
                    val serialization: Serialization = Serialization(),
                ) {
                    data class Serialization(
                        override var isEnabled: Boolean = false,
                        var useJson: Boolean = false,
                    ) : Enableable
                }
            }

            data class Library(
                override var isEnabled: Boolean = false,
                var features: Features = Features(),
                var rawConfig: RawConfig = RawConfig(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) {
                    configureAndroidLibrary(project)
                    rawConfig.configure(project)
                }

                data class RawConfig(
                    var android: Action<LibraryExtension>? = null,
                ) : Configurable {
                    override fun configure(project: Project) =
                        configureKotlinAndroidLibraryRawConfig(project)
                }

                data class Features(
                    var coroutines: Boolean = false,
                    var extendedStdlib: Boolean = true,
                    var extendedTesting: Boolean = true,
                    val serialization: Serialization = Serialization(),
                ) {
                    data class Serialization(
                        override var isEnabled: Boolean = false,
                        var useJson: Boolean = false,
                    ) : Enableable
                }
            }
        }

        data class Gradle(
            val plugin: Plugin = Plugin(),
            val versionCatalog: VersionCatalog = VersionCatalog(),
        ) : Configurable {

            val isEnabled: Boolean
                get() = plugin.isEnabled || versionCatalog.isEnabled

            override fun configure(project: Project) {
                plugin.configure(project)
                versionCatalog.configure(project)
            }

            data class Plugin(
                override var isEnabled: Boolean = false,
                val tags: MutableList<String> = mutableListOf(),
                var gradlePlugin: Action<GradlePluginDevelopmentExtension>? = null,
                var pluginUnderTestDependencies:
                    MutableList<Provider<MinimalExternalModuleDependency>> =
                    mutableListOf(),
                var features: Features = Features(),
                var rawConfig: RawConfig = RawConfig()
            ) : Enableable, Configurable {

                override fun configure(project: Project) {
                    configureGradlePlugin(project)
                    rawConfig.configure(project)
                }

                data class Features(
                    var extendedGradle: Boolean = true,
                    var extendedStdlib: Boolean = true,
                    var extendedTesting: Boolean = true,
                )

                data class RawConfig(
                    var kotlin: Action<KotlinJvmProjectExtension>? = null,
                    var gradlePlugin: Action<GradlePluginDevelopmentExtension>? = null,
                ) : Configurable {
                    override fun configure(project: Project) =
                        configureKotlinGradlePluginRawConfig(project)
                }
            }

            data class VersionCatalog(
                override var isEnabled: Boolean = false,
                val catalogs: MutableList<File> = mutableListOf()
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureGradleVersionCatalog(project)
            }
        }

        data class IntelliJ(
            override var isEnabled: Boolean = false,
            var intellij: Action<IntelliJPluginExtension>? = null,
            var patchPluginXml: Action<PatchPluginXmlTask>? = null,
            var publishPlugin: Action<PublishPluginTask>? = null,
            var signPlugin: Action<SignPluginTask>? = null,
            val features: Features = Features(),
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {
            override fun configure(project: Project) {
                configureIntelliJ(project)
                rawConfig.configure(project)
            }

            data class Features(
                var coroutines: Boolean = false,
                var extendedStdlib: Boolean = true,
                var extendedGradle: Boolean = false,
                var extendedTesting: Boolean = true,
                val serialization: Serialization = Serialization(),
            ) {
                data class Serialization(
                    override var isEnabled: Boolean = false,
                    var useJson: Boolean = false,
                ) : Enableable
            }

            data class RawConfig(
                var kotlin: Action<KotlinJvmProjectExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureKotlinIntellijRawConfig(project)
            }
        }

        data class Jvm(
            override var isEnabled: Boolean = false,
            var application: Action<JavaApplication>? = null,
            val features: Features = Features(),
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {
            override fun configure(project: Project) {
                configureJvm(project)
                rawConfig.configure(project)
            }

            data class Features(
                var coroutines: Boolean = false,
                var extendedStdlib: Boolean = true,
                var extendedGradle: Boolean = false,
                var extendedTesting: Boolean = true,
                val serialization: Serialization = Serialization(),
            ) {
                data class Serialization(
                    override var isEnabled: Boolean = false,
                    var useJson: Boolean = false,
                ) : Enableable
            }

            data class RawConfig(
                var kotlin: Action<KotlinJvmProjectExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureKotlinJvmRawConfig(project)
            }
        }

        data class Multiplatform(
            override var isEnabled: Boolean = false,
            val android: Android = Android(),
            val darwin: Darwin = Darwin(),
            val ios: IOS = IOS(),
            val iosArm32: IOSArm32 = IOSArm32(),
            val iosArm64: IOSArm64 = IOSArm64(),
            val iosX64: IOSX64 = IOSX64(),
            val iosSimulatorArm64: IOSSimulatorArm64 = IOSSimulatorArm64(),
            val jvm: Jvm = Jvm(),
            val jvmAndAndroid: JvmAndAndroid = JvmAndAndroid(),
            val js: Js = Js(),
            val linux: Linux = Linux(),
            val linuxArm32Hfp: LinuxArm32Hfp = LinuxArm32Hfp(),
            val linuxArm64: LinuxArm64 = LinuxArm64(),
            val linuxMips32: LinuxMips32 = LinuxMips32(),
            val linuxMipsel32: LinuxMipsel32 = LinuxMipsel32(),
            val linuxX64: LinuxX64 = LinuxX64(),
            val macos: MacOS = MacOS(),
            val macosArm64: MacOSArm64 = MacOSArm64(),
            val macosX64: MacOSX64 = MacOSX64(),
            val mingw: MinGW = MinGW(),
            val mingwX64: MinGWX64 = MinGWX64(),
            val mingwX86: MinGWX86 = MinGWX86(),
            val native: Native = Native(),
            val tvos: TvOS = TvOS(),
            val tvosArm64: TvOSArm64 = TvOSArm64(),
            val tvosSimulatorArm64: TvOSSimulatorArm64 = TvOSSimulatorArm64(),
            val tvosX64: TvOSX64 = TvOSX64(),
            val wasm: WAsm = WAsm(),
            val wasm32: WAsm32 = WAsm32(),
            val watchos: WatchOS = WatchOS(),
            val watchosArm32: WatchOSArm32 = WatchOSArm32(),
            val watchosArm64: WatchOSArm64 = WatchOSArm64(),
            val watchosX64: WatchOSX64 = WatchOSX64(),
            val watchosSimulatorArm64: WatchOSSimulatorArm64 = WatchOSSimulatorArm64(),
            val watchosX86: WatchOSX86 = WatchOSX86(),
            val features: Features = Features(),
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) {
                configureMultiplatform(project)

                configureCommonsTargets(project)

                if (features.minimumTargetsPerOS) {
                    val currentOS = OperatingSystem.current()
                    when {
                        currentOS.isLinux -> {
                            configureLinux(project)
                        }
                        currentOS.isMacOsX -> {
                            configureIOS(project)
                            configureMacOS(project)
                            configureTvOS(project)
                            configureWatchOS(project)
                        }
                        currentOS.isWindows -> {
                            configureMinGW(project)
                        }
                    }
                } else {
                    configureIOS(project)
                    configureMacOS(project)
                    configureLinux(project)
                    configureMinGW(project)
                    configureTvOS(project)
                    configureWatchOS(project)
                }

                darwin.configure(project)

                native.configure(project)

                rawConfig.configure(project)
            }

            private fun configureCommonsTargets(project: Project) {
                android.configure(project)
                jvm.configure(project)
                jvmAndAndroid.configure(project)
                js.configure(project)
                configureWasm(project)
            }

            private fun configureIOS(project: Project) {
                iosArm32.configure(project)
                iosArm64.configure(project)
                iosX64.configure(project)
                iosSimulatorArm64.configure(project)
                ios.configure(project)
            }

            private fun configureLinux(project: Project) {
                linuxArm32Hfp.configure(project)
                linuxArm64.configure(project)
                linuxMips32.configure(project)
                linuxMipsel32.configure(project)
                linuxX64.configure(project)
                linux.configure(project)
            }

            private fun configureMacOS(project: Project) {
                macosArm64.configure(project)
                macosX64.configure(project)
                macos.configure(project)
            }

            private fun configureMinGW(project: Project) {
                mingwX64.configure(project)
                mingwX86.configure(project)
                mingw.configure(project)
            }

            private fun configureTvOS(project: Project) {
                tvosArm64.configure(project)
                tvosSimulatorArm64.configure(project)
                tvosX64.configure(project)
                tvos.configure(project)
            }

            private fun configureWasm(project: Project) {
                wasm32.configure(project)
                wasm.configure(project)
            }

            private fun configureWatchOS(project: Project) {
                watchosArm32.configure(project)
                watchosArm64.configure(project)
                watchosX64.configure(project)
                watchosSimulatorArm64.configure(project)
                watchosX86.configure(project)
                watchos.configure(project)
            }

            data class Android(
                override var isEnabled: Boolean = false,
                var allLibraryVariants: Boolean = true,
                var compileSdk: Int = 32,
                var minSdk: Int = 21,
                val publishLibraryVariants: MutableList<String> = mutableListOf(),
                val rawConfig: RawConfig = RawConfig()
            ) : Enableable, Configurable {

                override fun configure(project: Project) {
                    configureMultiplatformAndroid(project)
                    rawConfig.configure(project)
                }

                data class RawConfig(
                    var android: Action<LibraryExtension>? = null,
                ) : Configurable {
                    override fun configure(project: Project) =
                        configureMultiplatformAndroidRawConfig(project)
                }
            }

            data class Darwin(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformDarwin(project)
            }

            data class IOS(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOS(project)
            }

            data class IOSArm32(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOSArm32(project)
            }

            data class IOSArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOSArm64(project)
            }

            data class IOSX64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOSX64(project)
            }

            data class IOSSimulatorArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) =
                    configureMultiplatformIOSSimulatorArm64(project)
            }

            data class Jvm(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformJvm(project)
            }

            data class JvmAndAndroid(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) =
                    configureMultiplatformJvmAndAndroid(project)
            }

            data class Js(
                override var isEnabled: Boolean = false,
                var browser: Browser = Browser(),
                var nodejs: NodeJS = NodeJS(),
            ) : Enableable, Configurable {

                data class Browser(
                    override var isEnabled: Boolean = KotlinMultiplatformJsExtension.BROWSER,
                    var action: Action<KotlinJsBrowserDsl>? = null
                ) : Enableable

                data class NodeJS(
                    override var isEnabled: Boolean = KotlinMultiplatformJsExtension.NODE_JS,
                    var action: Action<KotlinJsNodeDsl>? = null
                ) : Enableable

                override fun configure(project: Project) = configureMultiplatformJs(project)
            }

            data class Linux(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformLinux(project)
            }

            data class LinuxArm32Hfp(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformLinuxArm32Hfp(project)
            }

            data class LinuxArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformLinuxArm64(project)
            }

            data class LinuxMips32(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformLinuxMips32(project)
            }

            data class LinuxMipsel32(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformLinuxMipsel32(project)
            }

            data class LinuxX64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformLinuxX64(project)
            }

            data class MacOS(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMacOS(project)
            }

            data class MacOSX64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMacOSX64(project)
            }

            data class MacOSArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMacOSArm64(project)
            }

            data class MinGW(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMinGW(project)
            }

            data class MinGWX64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMinGWX64(project)
            }

            data class MinGWX86(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMinGWX86(project)
            }

            data class Native(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformNative(project)
            }

            data class TvOS(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformTvOS(project)
            }

            data class TvOSArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformTvOSArm64(project)
            }

            data class TvOSSimulatorArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformTvOSSimulatorArm64(project)
            }

            data class TvOSX64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformTvOSX64(project)
            }

            data class WAsm(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWAsm(project)
            }

            data class WAsm32(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWAsm32(project)
            }

            data class WatchOS(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWatchOS(project)
            }

            data class WatchOSArm32(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformWatchOSArm32(project)
            }

            data class WatchOSArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformWatchOSArm64(project)
            }

            data class WatchOSSimulatorArm64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformWatchOSSimulatorArm64(project)
            }

            data class WatchOSX64(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWatchOSX64(project)
            }

            data class WatchOSX86(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWatchOSX86(project)
            }

            data class Features(
                var coroutines: Boolean = false,
                var extendedStdlib: Boolean = true,
                var extendedTesting: Boolean = true,
                var minimumTargetsPerOS: Boolean = false,
                val serialization: Serialization = Serialization(),
            ) {
                data class Serialization(
                    override var isEnabled: Boolean = false,
                    var useJson: Boolean = false,
                ) : Enableable
            }

            data class RawConfig(
                var kotlin: Action<KotlinMultiplatformProjectExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureMultiplatformRawConfig(project)
            }
        }
    }
}

internal fun interface LaterConfigurable {

    sealed class Priority(private val value: Int) : Comparable<Priority> {

        object VeryHigh : Priority(1) {
            override fun toString(): String = "VeryHigh"
        }
        object High : Priority(2) {
            override fun toString(): String = "High"
        }
        object Medium : Priority(3) {
            override fun toString(): String = "Medium"
        }
        object Low : Priority(4) {
            override fun toString(): String = "Low"
        }
        object VeryLow : Priority(5) {
            override fun toString(): String = "VeryLow"
        }

        override fun compareTo(other: Priority): Int =
            when {
                value > other.value -> 1
                value == other.value -> 0
                else -> -1
            }
    }

    val priority: Priority
        get() = Priority.Medium

    fun configureLater(): Configurable
}

internal fun interface Configurable {
    fun configure(project: Project)
}

internal interface Enableable {
    var isEnabled: Boolean
}
