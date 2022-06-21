package com.javiersc.hubdle.extensions._internal.state

import com.javiersc.hubdle.extensions._internal.state.config.configureNexus
import com.javiersc.hubdle.extensions._internal.state.config.configureVersioning
import com.javiersc.hubdle.extensions._internal.state.config.documentation.configureChangelog
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureAndroidLibrary
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureJvm
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureMultiplatform
import com.javiersc.hubdle.extensions._internal.state.kotlin.gradle.configureGradlePlugin
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureAnalysis
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureFormat
import java.io.File
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

internal val hubdleState = HubdleState()

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
        val nexus: Nexus = Nexus(),
        val versioning: Versioning = Versioning(),
    ) : Configurable {

        override fun configure(project: Project) {
            documentation.configure(project)
            nexus.configure(project)
            versioning.configure(project)
        }

        data class Documentation(val changelog: Changelog = Changelog()) : Configurable {

            override fun configure(project: Project) {
                changelog.configure(project)
            }

            data class Changelog(
                override var isEnabled: Boolean = false,
            ) : Enableable, Configurable {
                override fun configure(project: Project) = configureChangelog(project)
            }
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
        var explicitApiMode: ExplicitApiMode = ExplicitApiMode.Disabled,
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
                override fun configure(project: Project) = configureVersioning(project)
            }
        }

        data class Jvm(
            override var isEnabled: Boolean = false,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureJvm(project)
        }

        data class Multiplatform(
            override var isEnabled: Boolean = false,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureMultiplatform(project)
        }

        data class Tools(
            val analysis: Analysis = Analysis(),
            val format: Format = Format(),
        ) : Configurable {

            override fun configure(project: Project) {
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
