@file:Suppress("MagicNumber", "SwallowedException", "TooGenericExceptionCaught")

import com.google.gson.Gson
import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Cyan
import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Green
import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Red
import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Yellow
import com.javiersc.kotlin.stdlib.AnsiColor.Reset
import java.net.HttpURLConnection
import java.net.URL
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

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
rootProject.tasks {
    val configureWrapper by
        tasks.registering {
            doLast {
                tasks.withType<Wrapper>().configureEach {
                    gradleVersion = getSpecificGradleVersion()
                }
            }
        }
    tasks.register("updateGradleWrapper") {
        group = "updater"
        description = "Check the latest Gradlew Wrapper version"

        dependsOn(configureWrapper)
        finalizedBy(tasks.withType<Wrapper>())

        doLast {
            if (tasks.withType<Wrapper>().toList().all { it.gradleVersion.isNotBlank() }) {
                rootProject.exec {
                    workingDir = rootProject.rootDir
                    commandLine =
                        (if (isWindows) "cmd git update-index --chmod=+x gradlew"
                            else "git update-index --chmod=+x gradlew")
                            .split(" ")
                }
            }
        }
    }
}

private val baseUrl = "https://services.gradle.org/versions"
private val currentUrl = "$baseUrl/current"
private val releaseCandidateUrl = "$baseUrl/release-candidate"
private val nightlyUrl = "https://services.gradle.org/versions/nightly"

enum class Stage {
    Current,
    RC,
    Nightly
}

fun getSpecificGradleVersion(): String {
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
            error("${Red}There is no Gradle version available$Reset")
        } else {
            File("${project.rootProject.buildDir}/versions/gradle-wrapper.txt").apply {
                ensureParentDirsCreated()
                createNewFile()
                writeText(gradleVersion.version)
            }

            logger.lifecycle(
                "The latest ${Cyan}Gradle version$Reset for " +
                    "the stage $Yellow${stage ?: "Current"}$Reset " +
                    "is $Green${gradleVersion.version}${Reset}"
            )
            gradleVersion.version
        }
    }
}

data class GradleVersion(val version: String?)

val isWindows: Boolean
    get() = System.getProperty("os.name").contains("windows", true)
