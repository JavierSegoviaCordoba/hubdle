package com.javiersc.hubdle.extensions._internal.state

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.config.documentation.changelog.ChangelogExtension
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.configureChangelog
import com.javiersc.hubdle.extensions.config.documentation.readme.ReadmeBadgesExtension
import com.javiersc.hubdle.extensions.config.documentation.readme._internal.configureReadmeBadges
import com.javiersc.hubdle.extensions.config.documentation.site.SiteExtension
import com.javiersc.hubdle.extensions.config.documentation.site._internal.configureSite
import com.javiersc.hubdle.extensions.config.install.InstallExtension
import com.javiersc.hubdle.extensions.config.install._internal.configureInstallPreCommits
import com.javiersc.hubdle.extensions.config.nexus.NexusExtension
import com.javiersc.hubdle.extensions.config.nexus._internal.configureNexus
import com.javiersc.hubdle.extensions.config.versioning.VersioningExtension
import com.javiersc.hubdle.extensions.config.versioning._internal.configureVersioning
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.android.library.KotlinAndroidLibraryExtension
import com.javiersc.hubdle.extensions.kotlin.android.library._internal.configureAndroidLibrary
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.KotlinGradlePluginExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureGradlePlugin
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.KotlinGradleVersionCatalogExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog._internal.configureGradleVersionCatalog
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions.Companion.JVM_VERSION
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.configureJvm
import com.javiersc.hubdle.extensions.kotlin.multiplatform.KotlinMultiplatformExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatform
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.configureMultiplatformAndroid
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
import com.javiersc.hubdle.extensions.kotlin.tools.analysis.AnalysisExtension
import com.javiersc.hubdle.extensions.kotlin.tools.analysis._internal.configureAnalysis
import com.javiersc.hubdle.extensions.kotlin.tools.binary.compatibility.validator.BinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.kotlin.tools.binary.compatibility.validator._internal.configureBinaryCompatibilityValidator
import com.javiersc.hubdle.extensions.kotlin.tools.coverage.CoverageExtension
import com.javiersc.hubdle.extensions.kotlin.tools.coverage._internal.configureCoverage
import com.javiersc.hubdle.extensions.kotlin.tools.format.FormatExtension
import com.javiersc.hubdle.extensions.kotlin.tools.format._internal.configureFormat
import com.javiersc.hubdle.extensions.kotlin.tools.publishing.PublishingExtension
import java.io.File
import org.gradle.api.Action
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KotlinMultiplatformProjectExtension

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

    override fun configure(project: Project) {
        config.configure(project)
        kotlin.configure(project)
    }

    data class Config(
        val documentation: Documentation = Documentation(),
        val install: Install = Install(),
        val nexus: Nexus = Nexus(),
        val versioning: Versioning = Versioning(),
    ) : Configurable {

        override fun configure(project: Project) {
            documentation.configure(project)
            install.configure(project)
            nexus.configure(project)
            versioning.configure(project)
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
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureChangelog(project)
            }

            data class Readme(val badges: Badges = Badges()) : Configurable {

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
                val reports: Reports = Reports(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureSite(project)

                data class Reports(
                    var allTests: Boolean = SiteExtension.ReportsExtension.ALL_TESTS,
                    var codeAnalysis: Boolean = SiteExtension.ReportsExtension.CODE_ANALYSIS,
                    var codeCoverage: Boolean = SiteExtension.ReportsExtension.CODE_COVERAGE,
                    var codeQuality: Boolean = SiteExtension.ReportsExtension.CODE_QUALITY,
                )
            }
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

        data class Versioning(
            override var isEnabled: Boolean = VersioningExtension.IS_ENABLED,
            var tagPrefix: String = VersioningExtension.TAG_PREFIX,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureVersioning(project)
        }
    }

    data class Kotlin(
        val android: Android = Android(),
        val gradle: Gradle = Gradle(),
        var isPublishingEnabled: Boolean = PublishingExtension.IS_ENABLED,
        val jvm: Jvm = Jvm(),
        val multiplatform: Multiplatform = Multiplatform(),
        var target: Int = JVM_VERSION,
        val tools: Tools = Tools(),
    ) : Configurable {

        override fun configure(project: Project) {
            android.library.configure(project)
            gradle.configure(project)
            jvm.configure(project)
            multiplatform.configure(project)
            tools.configure(project)
        }

        data class Android(
            var compileSdk: Int = AndroidOptions.COMPILE_SDK,
            val library: Library = Library(),
            var minSdk: Int = AndroidOptions.MIN_SDK,
            var features: Features = Features(),
        ) : Configurable {

            override fun configure(project: Project) {
                library.configure(project)
            }

            data class Library(
                override var isEnabled: Boolean = KotlinAndroidLibraryExtension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureAndroidLibrary(project)
            }

            data class Features(
                var coroutines: Boolean =
                    KotlinAndroidLibraryExtension.FeaturesExtension.COROUTINES,
                var javierScStdlib: Boolean =
                    KotlinAndroidLibraryExtension.FeaturesExtension.JAVIER_SC_STDLIB,
            )
        }

        data class Gradle(
            val plugin: Plugin = Plugin(),
            val versionCatalog: VersionCatalog = VersionCatalog(),
        ) : Configurable {

            override fun configure(project: Project) {
                plugin.configure(project)
                versionCatalog.configure(project)
            }

            data class Plugin(
                override var isEnabled: Boolean = KotlinGradlePluginExtension.IS_ENABLED,
                var features: Features = Features()
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureGradlePlugin(project)

                data class Features(
                    var coroutines: Boolean =
                        KotlinGradlePluginExtension.FeaturesExtension.COROUTINES,
                    var javierScGradleExtensions: Boolean =
                        KotlinGradlePluginExtension.FeaturesExtension.JAVIERSC_GRADLE_EXTENSIONS,
                    var javierScStdlib: Boolean =
                        KotlinGradlePluginExtension.FeaturesExtension.JAVIER_SC_STDLIB,
                )
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
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureJvm(project)

            data class Features(
                var coroutines: Boolean = KotlinJvmExtension.FeaturesExtension.COROUTINES,
                var javierScGradleExtensions: Boolean =
                    KotlinJvmExtension.FeaturesExtension.JAVIERSC_GRADLE_EXTENSIONS,
                var javierScStdlib: Boolean = KotlinJvmExtension.FeaturesExtension.JAVIER_SC_STDLIB,
            )
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
                android.configure(project)
                iosArm32.configure(project)
                iosArm64.configure(project)
                iosX64.configure(project)
                iosSimulatorArm64.configure(project)
                jvm.configure(project)
                js.configure(project)
                linuxArm32Hfp.configure(project)
                linuxArm64.configure(project)
                linuxMips32.configure(project)
                linuxMipsel32.configure(project)
                linuxX64.configure(project)
                macosArm64.configure(project)
                macosX64.configure(project)
                mingwX64.configure(project)
                mingwX86.configure(project)
                tvosArm64.configure(project)
                tvosSimulatorArm64.configure(project)
                tvosX64.configure(project)
                wasm32.configure(project)
                watchosArm32.configure(project)
                watchosArm64.configure(project)
                watchosX64.configure(project)
                watchosSimulatorArm64.configure(project)
                watchosX86.configure(project)

                ios.configure(project)
                linux.configure(project)
                macos.configure(project)
                mingw.configure(project)
                tvos.configure(project)
                wasm.configure(project)
                watchos.configure(project)
                native.configure(project)

                rawConfig.configure(project)
            }

            data class Android(
                override var isEnabled: Boolean = KotlinMultiplatformAndroidExtension.IS_ENABLED,
                var allLibraryVariants: Boolean =
                    KotlinMultiplatformAndroidExtension.ALL_LIBRARY_VARIANTS,
                val publishLibraryVariants: MutableList<String> = mutableListOf(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformAndroid(project)
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
                var coroutines: Boolean = KotlinMultiplatformExtension.FeaturesExtension.COROUTINES,
                var javierScStdlib: Boolean =
                    KotlinMultiplatformExtension.FeaturesExtension.JAVIERSC_STDLIB,
            )

            data class RawConfig(
                var android: Action<LibraryExtension>? = null,
                var kotlin: Action<KotlinMultiplatformProjectExtension>? = null,
            ) : Configurable {
                override fun configure(project: Project) = configureMultiplatformRawConfig(project)
            }
        }

        data class Tools(
            val analysis: Analysis = Analysis(),
            val binaryCompatibilityValidator: BinaryCompatibilityValidator =
                BinaryCompatibilityValidator(),
            val coverage: Coverage = Coverage(),
            var explicitApiMode: ExplicitApiMode = ExplicitApiMode.Disabled,
            val format: Format = Format(),
        ) : Configurable {

            override fun configure(project: Project) {
                analysis.configure(project)
                binaryCompatibilityValidator.configure(project)
                coverage.configure(project)
                format.configure(project)
            }

            data class Analysis(
                override var isEnabled: Boolean = AnalysisExtension.IS_ENABLED,
                var isIgnoreFailures: Boolean = AnalysisExtension.IGNORE_FAILURES,
                val includes: MutableList<String> = AnalysisExtension.INCLUDES,
                val excludes: MutableList<String> = AnalysisExtension.EXCLUDES,
                val reports: Reports = Reports(),
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureAnalysis(project)

                data class Reports(
                    var html: Boolean = AnalysisExtension.ReportsExtension.HTML,
                    var sarif: Boolean = AnalysisExtension.ReportsExtension.SARIF,
                    var txt: Boolean = AnalysisExtension.ReportsExtension.TXT,
                    var xml: Boolean = AnalysisExtension.ReportsExtension.XML,
                )
            }

            data class BinaryCompatibilityValidator(
                override var isEnabled: Boolean = BinaryCompatibilityValidatorExtension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) =
                    configureBinaryCompatibilityValidator(project)
            }

            data class Coverage(
                override var isEnabled: Boolean = CoverageExtension.IS_ENABLED,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureCoverage(project)
            }

            data class Format(
                override var isEnabled: Boolean = FormatExtension.IS_ENABlED,
                val includes: MutableList<String> = mutableListOf(),
                val excludes: MutableList<String> = mutableListOf(),
                var ktfmtVersion: String = FormatExtension.KTFMT_VERSION
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureFormat(project)
            }
        }
    }
}

internal interface Configurable {
    fun configure(project: Project)
}

internal interface Enableable {
    var isEnabled: Boolean
}
