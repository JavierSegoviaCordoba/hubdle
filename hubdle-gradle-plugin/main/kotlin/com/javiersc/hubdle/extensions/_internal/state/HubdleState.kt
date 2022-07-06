package com.javiersc.hubdle.extensions._internal.state

import com.android.build.api.dsl.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.javiersc.hubdle.extensions._internal.state.LaterConfigurable.Priority
import com.javiersc.hubdle.extensions.config.analysis.AnalysisExtension
import com.javiersc.hubdle.extensions.config.analysis._internal.configureAnalysis
import com.javiersc.hubdle.extensions.config.analysis._internal.configureConfigAnalysisRawConfig
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator.BinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator._internal.configureBinaryCompatibilityValidator
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator._internal.configureConfigBinaryCompatibilityValidatorRawConfig
import com.javiersc.hubdle.extensions.config.coverage.CoverageExtension
import com.javiersc.hubdle.extensions.config.coverage._internal.configureCoverage
import com.javiersc.hubdle.extensions.config.coverage._internal.configureKotlinCoverageRawConfig
import com.javiersc.hubdle.extensions.config.documentation.changelog.ChangelogExtension
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.configureChangelog
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.configureConfigDocumentationChangelogRawConfig
import com.javiersc.hubdle.extensions.config.documentation.readme.ReadmeBadgesExtension
import com.javiersc.hubdle.extensions.config.documentation.readme._internal.configureReadmeBadges
import com.javiersc.hubdle.extensions.config.documentation.site.SiteExtension
import com.javiersc.hubdle.extensions.config.documentation.site._internal.configureConfigDocumentationSiteRawConfig
import com.javiersc.hubdle.extensions.config.documentation.site._internal.configureSite
import com.javiersc.hubdle.extensions.config.format.FormatExtension
import com.javiersc.hubdle.extensions.config.format._internal.configureFormat
import com.javiersc.hubdle.extensions.config.format._internal.configureKotlinFormatRawConfig
import com.javiersc.hubdle.extensions.config.install.InstallExtension
import com.javiersc.hubdle.extensions.config.install._internal.configureInstallPreCommits
import com.javiersc.hubdle.extensions.config.language.settings._internal.configureConfigLanguageSettings
import com.javiersc.hubdle.extensions.config.nexus.NexusExtension
import com.javiersc.hubdle.extensions.config.nexus._internal.configureNexus
import com.javiersc.hubdle.extensions.config.publishing.PublishingExtension
import com.javiersc.hubdle.extensions.config.publishing._internal.configureKotlinPublishingRawConfig
import com.javiersc.hubdle.extensions.config.versioning.VersioningExtension
import com.javiersc.hubdle.extensions.config.versioning._internal.configureConfigVersioningRawConfig
import com.javiersc.hubdle.extensions.config.versioning._internal.configureVersioning
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.android.library.KotlinAndroidLibraryExtension
import com.javiersc.hubdle.extensions.kotlin.android.library._internal.configureAndroidLibrary
import com.javiersc.hubdle.extensions.kotlin.android.library._internal.configureKotlinAndroidLibraryRawConfig
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.KotlinGradlePluginExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureGradlePlugin
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureKotlinGradlePluginRawConfig
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.KotlinGradleVersionCatalogExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog._internal.configureGradleVersionCatalog
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions.Companion.JVM_VERSION
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.configureJvm
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.configureKotlinJvmRawConfig
import com.javiersc.hubdle.extensions.kotlin.multiplatform.KotlinMultiplatformExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatform
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformAndroid
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformAndroidRawConfig
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSArm32
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSSimulatorArm64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformIOSX64
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformJs
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformJvm
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
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJsExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJvmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformLinuxExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformMacOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformMinGWExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformNativeExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTvOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformWAsmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformWatchOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformiOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSArm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm32HfpExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMips32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMipsel32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.KotlinMultiplatformMacOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.KotlinMultiplatformMacOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw.KotlinMultiplatformMinGWX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw.KotlinMultiplatformMinGWX86Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.KotlinMultiplatformTvOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.KotlinMultiplatformTvOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.KotlinMultiplatformTvOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.wasm.KotlinMultiplatformWAsm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSArm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSX86Extension
import com.javiersc.semver.gradle.plugin.SemverExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import kotlinx.kover.api.KoverExtension
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension as GradlePublishingExtension
import org.gradle.internal.os.OperatingSystem
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KotlinMultiplatformProjectExtension
import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder
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
            override var isEnabled: Boolean = AnalysisExtension.IS_ENABLED,
            var isIgnoreFailures: Boolean = AnalysisExtension.IGNORE_FAILURES,
            val includes: MutableList<String> = AnalysisExtension.INCLUDES,
            val excludes: MutableList<String> = AnalysisExtension.EXCLUDES,
            val reports: Reports = Reports(),
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) {
                configureAnalysis(project)
                rawConfig.configure(project)
            }

            data class Reports(
                var html: Boolean = AnalysisExtension.ReportsExtension.HTML,
                var sarif: Boolean = AnalysisExtension.ReportsExtension.SARIF,
                var txt: Boolean = AnalysisExtension.ReportsExtension.TXT,
                var xml: Boolean = AnalysisExtension.ReportsExtension.XML,
            )

            data class RawConfig(
                var detekt: Action<DetektExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureConfigAnalysisRawConfig(project)
            }
        }

        data class BinaryCompatibilityValidator(
            override var isEnabled: Boolean = BinaryCompatibilityValidatorExtension.IS_ENABLED,
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
            override var isEnabled: Boolean = CoverageExtension.IS_ENABLED,
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
                override var isEnabled: Boolean = ChangelogExtension.IS_ENABLED,
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
                    override var isEnabled: Boolean = ReadmeBadgesExtension.IS_ENABLED,
                    var kotlin: Boolean = ReadmeBadgesExtension.KOTLIN,
                    var mavenCentral: Boolean = ReadmeBadgesExtension.MAVEN_CENTRAL,
                    var snapshots: Boolean = ReadmeBadgesExtension.SNAPSHOTS,
                    var build: Boolean = ReadmeBadgesExtension.BUILD,
                    var coverage: Boolean = ReadmeBadgesExtension.COVERAGE,
                    var quality: Boolean = ReadmeBadgesExtension.QUALITY,
                    var techDebt: Boolean = ReadmeBadgesExtension.TECH_DEBT,
                ) : Enableable, Configurable {

                    override fun configure(project: Project) = configureReadmeBadges(project)
                }
            }

            data class Site(
                override var isEnabled: Boolean = SiteExtension.IS_ENABLED,
                val excludes: MutableList<ProjectDependency> = mutableListOf(),
                val reports: Reports = Reports(),
                val rawConfig: RawConfig = RawConfig(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) {
                    configureSite(project)
                    rawConfig.configure(project)
                }

                data class Reports(
                    var allTests: Boolean = SiteExtension.ReportsExtension.ALL_TESTS,
                    var codeAnalysis: Boolean = SiteExtension.ReportsExtension.CODE_ANALYSIS,
                    var codeCoverage: Boolean = SiteExtension.ReportsExtension.CODE_COVERAGE,
                    var codeQuality: Boolean = SiteExtension.ReportsExtension.CODE_QUALITY,
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
            override var isEnabled: Boolean = FormatExtension.IS_ENABlED,
            val includes: MutableList<String> = FormatExtension.INCLUDES,
            val excludes: MutableList<String> = FormatExtension.EXCLUDES,
            var ktfmtVersion: String = FormatExtension.KTFMT_VERSION,
            val rawConfig: RawConfig = RawConfig(),
        ) : Enableable, LaterConfigurable {

            override val priority: Priority = Priority.VeryLow

            override fun configureLater(): Configurable = Configurable {
                configureFormat(it)
                rawConfig.configure(it)
            }

            data class RawConfig(
                var spotless: Action<SpotlessExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureKotlinFormatRawConfig(project)
            }
        }

        data class LanguageSettings(
            var experimentalCoroutinesApi: Boolean = false,
            var experimentalStdlibApi: Boolean = true,
            var experimentalTime: Boolean = true,
            var flowPreview: Boolean = false,
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
                override var isEnabled: Boolean = InstallExtension.PreCommitsExtension.IS_ENABLED,
                var allTests: Boolean = InstallExtension.PreCommitsExtension.ALL_TESTS,
                var applyFormat: Boolean = InstallExtension.PreCommitsExtension.APPLY_FORMAT,
                var assemble: Boolean = InstallExtension.PreCommitsExtension.ASSEMBLE,
                var checkAnalysis: Boolean = InstallExtension.PreCommitsExtension.CHECK_ANALYSIS,
                var checkFormat: Boolean = InstallExtension.PreCommitsExtension.CHECK_FORMAT,
                var checkApi: Boolean = InstallExtension.PreCommitsExtension.CHECK_API,
                var dumpApi: Boolean = InstallExtension.PreCommitsExtension.DUMP_API,
            ) : Enableable
        }

        data class Nexus(
            override var isEnabled: Boolean = NexusExtension.IS_ENABLED,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureNexus(project)
        }

        data class Publishing(
            override var isEnabled: Boolean = PublishingExtension.IS_ENABLED,
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
            override var isEnabled: Boolean = VersioningExtension.IS_ENABLED,
            var tagPrefix: String = VersioningExtension.TAG_PREFIX,
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
        val jvm: Jvm = Jvm(),
        val multiplatform: Multiplatform = Multiplatform(),
        var target: Int = JVM_VERSION,
    ) : Configurable {

        val isEnabled: Boolean
            get() =
                android.isEnabled || gradle.isEnabled || jvm.isEnabled || multiplatform.isEnabled

        override fun configure(project: Project) {
            android.library.configure(project)
            gradle.configure(project)
            jvm.configure(project)
            multiplatform.configure(project)
        }

        data class Android(
            var compileSdk: Int = AndroidOptions.COMPILE_SDK,
            val library: Library = Library(),
            var minSdk: Int = AndroidOptions.MIN_SDK,
        ) : Configurable {

            val isEnabled: Boolean
                get() = library.isEnabled

            override fun configure(project: Project) {
                library.configure(project)
            }

            data class Library(
                override var isEnabled: Boolean = KotlinAndroidLibraryExtension.IS_ENABLED,
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
                )
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
                override var isEnabled: Boolean = KotlinGradlePluginExtension.IS_ENABLED,
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
                override var isEnabled: Boolean = KotlinGradleVersionCatalogExtension.IS_ENABLED,
                val catalogs: MutableList<File> = mutableListOf()
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureGradleVersionCatalog(project)
            }
        }

        data class Jvm(
            override var isEnabled: Boolean = KotlinJvmExtension.IS_ENABLED,
            val features: Features = Features(),
            val rawConfig: RawConfig = RawConfig()
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
            )

            data class RawConfig(
                var kotlin: Action<KotlinJvmProjectExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureKotlinJvmRawConfig(project)
            }
        }

        data class Multiplatform(
            override var isEnabled: Boolean = KotlinMultiplatformExtension.IS_ENABLED,
            val android: Android = Android(),
            val ios: IOS = IOS(),
            val iosArm32: IOSArm32 = IOSArm32(),
            val iosArm64: IOSArm64 = IOSArm64(),
            val iosX64: IOSX64 = IOSX64(),
            val iosSimulatorArm64: IOSSimulatorArm64 = IOSSimulatorArm64(),
            val jvm: Jvm = Jvm(),
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

                native.configure(project)

                rawConfig.configure(project)
            }

            private fun configureCommonsTargets(project: Project) {
                android.configure(project)
                jvm.configure(project)
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
                override var isEnabled: Boolean = KotlinMultiplatformAndroidExtension.IS_ENABLED,
                var allLibraryVariants: Boolean =
                    KotlinMultiplatformAndroidExtension.ALL_LIBRARY_VARIANTS,
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

            data class IOS(
                override var isEnabled: Boolean = KotlinMultiplatformiOSExtension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOS(project)
            }

            data class IOSArm32(
                override var isEnabled: Boolean = KotlinMultiplatformiOSArm32Extension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOSArm32(project)
            }

            data class IOSArm64(
                override var isEnabled: Boolean = KotlinMultiplatformiOSArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOSArm64(project)
            }

            data class IOSX64(
                override var isEnabled: Boolean = KotlinMultiplatformiOSX64Extension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformIOSX64(project)
            }

            data class IOSSimulatorArm64(
                override var isEnabled: Boolean =
                    KotlinMultiplatformiOSSimulatorArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) =
                    configureMultiplatformIOSSimulatorArm64(project)
            }

            data class Jvm(
                override var isEnabled: Boolean = KotlinMultiplatformJvmExtension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformJvm(project)
            }

            data class Js(
                override var isEnabled: Boolean = KotlinMultiplatformJsExtension.IS_ENABLED,
                var browser: Boolean = KotlinMultiplatformJsExtension.BROWSER,
                var nodeJs: Boolean = KotlinMultiplatformJsExtension.NODE_JS
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformJs(project)
            }

            data class Linux(
                override var isEnabled: Boolean = KotlinMultiplatformLinuxExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformLinux(project)
            }

            data class LinuxArm32Hfp(
                override var isEnabled: Boolean =
                    KotlinMultiplatformLinuxArm32HfpExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformLinuxArm32Hfp(project)
            }

            data class LinuxArm64(
                override var isEnabled: Boolean = KotlinMultiplatformLinuxArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformLinuxArm64(project)
            }

            data class LinuxMips32(
                override var isEnabled: Boolean =
                    KotlinMultiplatformLinuxMips32Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformLinuxMips32(project)
            }

            data class LinuxMipsel32(
                override var isEnabled: Boolean =
                    KotlinMultiplatformLinuxMipsel32Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformLinuxMipsel32(project)
            }

            data class LinuxX64(
                override var isEnabled: Boolean = KotlinMultiplatformLinuxX64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformLinuxX64(project)
            }

            data class MacOS(
                override var isEnabled: Boolean = KotlinMultiplatformMacOSExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMacOS(project)
            }

            data class MacOSX64(
                override var isEnabled: Boolean = KotlinMultiplatformMacOSX64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMacOSX64(project)
            }

            data class MacOSArm64(
                override var isEnabled: Boolean = KotlinMultiplatformMacOSArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMacOSArm64(project)
            }

            data class MinGW(
                override var isEnabled: Boolean = KotlinMultiplatformMinGWExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMinGW(project)
            }

            data class MinGWX64(
                override var isEnabled: Boolean = KotlinMultiplatformMinGWX64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMinGWX64(project)
            }

            data class MinGWX86(
                override var isEnabled: Boolean = KotlinMultiplatformMinGWX86Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformMinGWX86(project)
            }

            data class Native(
                override var isEnabled: Boolean = KotlinMultiplatformNativeExtension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformNative(project)
            }

            data class TvOS(
                override var isEnabled: Boolean = KotlinMultiplatformTvOSExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformTvOS(project)
            }

            data class TvOSArm64(
                override var isEnabled: Boolean = KotlinMultiplatformTvOSArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformTvOSArm64(project)
            }

            data class TvOSSimulatorArm64(
                override var isEnabled: Boolean =
                    KotlinMultiplatformTvOSSimulatorArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformTvOSSimulatorArm64(project)
            }

            data class TvOSX64(
                override var isEnabled: Boolean = KotlinMultiplatformTvOSX64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformTvOSX64(project)
            }

            data class WAsm(
                override var isEnabled: Boolean = KotlinMultiplatformWAsmExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWAsm(project)
            }

            data class WAsm32(
                override var isEnabled: Boolean = KotlinMultiplatformWAsm32Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWAsm32(project)
            }

            data class WatchOS(
                override var isEnabled: Boolean = KotlinMultiplatformWatchOSExtension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWatchOS(project)
            }

            data class WatchOSArm32(
                override var isEnabled: Boolean =
                    KotlinMultiplatformWatchOSArm32Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformWatchOSArm32(project)
            }

            data class WatchOSArm64(
                override var isEnabled: Boolean =
                    KotlinMultiplatformWatchOSArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformWatchOSArm64(project)
            }

            data class WatchOSSimulatorArm64(
                override var isEnabled: Boolean =
                    KotlinMultiplatformWatchOSSimulatorArm64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) =
                    configureMultiplatformWatchOSSimulatorArm64(project)
            }

            data class WatchOSX64(
                override var isEnabled: Boolean = KotlinMultiplatformWatchOSX64Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWatchOSX64(project)
            }

            data class WatchOSX86(
                override var isEnabled: Boolean = KotlinMultiplatformWatchOSX86Extension.IS_ENABLED,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureMultiplatformWatchOSX86(project)
            }

            data class Features(
                var coroutines: Boolean = false,
                var extendedStdlib: Boolean = true,
                var extendedTesting: Boolean = true,
                var minimumTargetsPerOS: Boolean = false
            )

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
