package com.javiersc.hubdle.extensions._internal.state

import com.javiersc.hubdle.extensions._internal.state.config.configureInstall
import com.javiersc.hubdle.extensions._internal.state.config.configureNexus
import com.javiersc.hubdle.extensions._internal.state.config.configureVersioning
import com.javiersc.hubdle.extensions._internal.state.config.documentation.configureChangelog
import com.javiersc.hubdle.extensions._internal.state.config.documentation.configureReadmeBadges
import com.javiersc.hubdle.extensions._internal.state.config.documentation.configureSite
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureAndroidLibrary
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureJvm
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureMultiplatform
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureMultiplatformAndroid
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureMultiplatformJvm
import com.javiersc.hubdle.extensions._internal.state.kotlin.gradle.configureGradlePlugin
import com.javiersc.hubdle.extensions._internal.state.kotlin.gradle.configureGradleVersionCatalog
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureAnalysis
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureCoverage
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureFormat
import java.io.File
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

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
            val readmeBadges: ReadmeBadges = ReadmeBadges(),
            val site: Site = Site()
        ) : Configurable {

            override fun configure(project: Project) {
                changelog.configure(project)
                readmeBadges.configure(project)
                site.configure(project)
            }

            data class Changelog(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureChangelog(project)
            }

            data class ReadmeBadges(
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

            data class Site(
                override var isEnabled: Boolean = false,
                val reports: Reports = Reports(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureSite(project)

                data class Reports(
                    var allTests: Boolean = true,
                    var codeAnalysis: Boolean = true,
                    var codeCoverage: Boolean = true,
                    var codeQuality: Boolean = true,
                )
            }
        }

        data class Install(
            override var isEnabled: Boolean = false,
            val preCommits: PreCommits = PreCommits(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) = configureInstall(project)

            data class PreCommits(
                var allTests: Boolean = false,
                var applyFormat: Boolean = false,
                var assemble: Boolean = false,
                var checkAnalysis: Boolean = false,
                var checkFormat: Boolean = false,
                var checkApi: Boolean = false,
            )
        }

        data class Nexus(
            override var isEnabled: Boolean = false,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureNexus(project)
        }

        data class Versioning(
            override var isEnabled: Boolean = false,
            var tagPrefix: String = "",
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureVersioning(project)
        }
    }

    data class Kotlin(
        val android: Android = Android(),
        val gradle: Gradle = Gradle(),
        var isPublishingEnabled: Boolean = false,
        val jvm: Jvm = Jvm(),
        val multiplatform: Multiplatform = Multiplatform(),
        var target: Int = 8,
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
            var compileSdk: Int = 31,
            val library: Library = Library(),
            var minSdk: Int = 21,
        ) : Configurable {

            override fun configure(project: Project) {
                library.configure(project)
            }

            data class Library(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureAndroidLibrary(project)
            }
        }

        data class Gradle(
            val plugin: Plugin = Plugin(),
            val versionCatalog: VersionCatalog = VersionCatalog(),
        ) : Configurable {

            override fun configure(project: Project) {
                plugin.configure(project)
                versionCatalog.configure(project)
            }

            data class Plugin(override var isEnabled: Boolean = false) : Enableable, Configurable {

                override fun configure(project: Project) = configureGradlePlugin(project)
            }

            data class VersionCatalog(
                override var isEnabled: Boolean = false,
                val files: MutableList<File> = mutableListOf()
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureGradleVersionCatalog(project)
            }
        }

        data class Jvm(
            override var isEnabled: Boolean = false,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureJvm(project)
        }

        data class Multiplatform(
            override var isEnabled: Boolean = false,
            var android: Android = Android(),
            var jvm: Jvm = Jvm(),
        ) : Enableable, Configurable {

            override fun configure(project: Project) {
                configureMultiplatform(project)
                android.configure(project)
                jvm.configure(project)
            }

            data class Android(
                override var isEnabled: Boolean = false,
                var allLibraryVariants: Boolean = false,
                val publishLibraryVariants: MutableList<String> = mutableListOf(),
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformAndroid(project)
            }

            data class Jvm(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureMultiplatformJvm(project)
            }
        }

        data class Tools(
            val analysis: Analysis = Analysis(),
            var binaryCompatibilityValidator: Boolean = false,
            var coverage: Coverage = Coverage(),
            var explicitApiMode: ExplicitApiMode = ExplicitApiMode.Disabled,
            val format: Format = Format(),
        ) : Configurable {

            override fun configure(project: Project) {
                analysis.configure(project)
                coverage.configure(project)
                format.configure(project)
            }

            data class Analysis(
                override var isEnabled: Boolean = false,
                var isIgnoreFailures: Boolean = true,
                val includes: MutableList<String> = mutableListOf(),
                val excludes: MutableList<String> = mutableListOf(),
                val reports: Reports = Reports(),
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureAnalysis(project)

                data class Reports(
                    var html: Boolean = true,
                    var sarif: Boolean = true,
                    var txt: Boolean = false,
                    var xml: Boolean = true,
                )
            }

            data class Coverage(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureCoverage(project)
            }

            data class Format(
                override var isEnabled: Boolean = false,
                val includes: MutableList<String> = mutableListOf(),
                val excludes: MutableList<String> = mutableListOf(),
                var ktfmtVersion: String = "0.37"
            ) : Enableable, Configurable {

                override fun configure(project: Project) = configureFormat(project)
            }
        }
    }
}

private interface Configurable {
    fun configure(project: Project)
}

private interface Enableable {
    var isEnabled: Boolean
}
