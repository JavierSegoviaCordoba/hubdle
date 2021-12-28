package com.javiersc.gradle.plugins.gradle.wrapper.updater

import com.google.gson.Gson
import com.javiersc.kotlin.stdlib.AnsiColor
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.wrapper.Wrapper

/**
 * ### Usage
 *
 * ```shell
 * ./gradlew updateGradleWrapper
 *
 * ./gradlew updateGradleWrapper -P"gradlew.stage"="rc"
 * ```
 *
 * - If the input is not set, or it is `current`, the last stable version will be used.
 * - If the input is `rc`, the last release candidate version will be used, can be possible there is
 * no release candidate version.
 * - If the input is `nightly`, the last nightly version will be used.
 */
abstract class GradleWrapperUpdaterPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.rootProject.tasks.apply {
            register("configureWrapper") { task ->
                task.doLast {
                    it.project.tasks.withType(Wrapper::class.java).configureEach { wrapper ->
                        wrapper.gradleVersion = it.project.getSpecificGradleVersion()
                    }
                }
            }
            register("updateGradleWrapper") { task ->
                task.group = "updater"
                task.description = "Check the latest Gradlew Wrapper version"

                task.dependsOn("configureWrapper")
                task.finalizedBy(task.project.tasks.withType(Wrapper::class.java))

                task.doLast {
                    if (it.project.tasks.withType(Wrapper::class.java).toList().all { wrapper ->
                            wrapper.gradleVersion.isNotBlank()
                        }
                    ) {
                        it.project.rootProject.exec { execSpec ->
                            execSpec.workingDir = it.project.rootProject.rootDir
                            execSpec.commandLine =
                                (if (isWindows) "cmd git update-index --chmod=+x gradlew"
                                    else "git update-index --chmod=+x gradlew")
                                    .split(" ")
                        }
                    }
                }
            }
        }
    }
}

private fun Project.getSpecificGradleVersion(): String {
    val baseUrl = "https://services.gradle.org/versions"
    val currentUrl = "$baseUrl/current"
    val releaseCandidateUrl = "$baseUrl/release-candidate"
    val nightlyUrl = "https://services.gradle.org/versions/nightly"

    val stage: Stage? =
        Stage.values().firstOrNull {
            it.name.equals(properties["gradlew.stage"]?.toString(), ignoreCase = true)
        }

    val url =
        when (stage) {
            Stage.Current -> currentUrl
            Stage.RC -> releaseCandidateUrl
            Stage.Nightly -> nightlyUrl
            else -> {
                if (properties["gradlew.stage"] == null) {
                    logger.lifecycle(
                        "`stage` should be one of [`current`, `rc` or `nightly`], using `current`"
                    )
                }
                currentUrl
            }
        }

    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    if (connection.responseCode !in 200..299) {
        error("There is an error getting the last Gradle version (code: ${connection.responseCode}")
    }

    connection.inputStream.bufferedReader().use {
        val gradleVersion: GradleVersion = Gson().fromJson(it.readText(), GradleVersion::class.java)

        return if (gradleVersion.version == null) {
            error(
                "${AnsiColor.Foreground.Red}There is no Gradle version available${AnsiColor.Reset}"
            )
        } else {
            File("${project.rootProject.buildDir}/versions/gradle-wrapper.txt").apply {
                parentFile.mkdirs()
                createNewFile()
                writeText(gradleVersion.version)
            }

            logger.lifecycle(
                "The latest ${AnsiColor.Foreground.Cyan}Gradle version${AnsiColor.Reset} for " +
                    "the stage ${AnsiColor.Foreground.Yellow}${stage ?: "Current"}${AnsiColor.Reset} " +
                    "is ${AnsiColor.Foreground.Green}${gradleVersion.version}${AnsiColor.Reset}"
            )
            gradleVersion.version
        }
    }
}

private val isWindows: Boolean
    get() = System.getProperty("os.name").contains("windows", true)

private data class GradleVersion(val version: String?)

private enum class Stage {
    Current,
    RC,
    Nightly
}
