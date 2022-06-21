package com.javiersc.hubdle.extensions._internal.state

import com.javiersc.hubdle.extensions._internal.state.config.configureVersioning
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureAndroidLibrary
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureGradle
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureJvm
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureMultiplatform
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureFormat
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
        val versioning: Versioning = Versioning(),
    ) : Configurable {

        override fun configure(project: Project) {
            versioning.configure(project)
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
            override var isEnabled: Boolean = false,
        ) : Enableable, Configurable {
            override fun configure(project: Project) = configureGradle(project)
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
            val format: Format = Format(),
        ) : Configurable {

            override fun configure(project: Project) {
                format.configure(project)
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
