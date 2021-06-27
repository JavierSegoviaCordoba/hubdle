@file:Suppress("MagicNumber", "SwallowedException", "TooGenericExceptionCaught")

import java.net.HttpURLConnection
import java.net.URL
import kotlin.streams.asSequence
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

/**
 * ### Usage
 *
 * ```shell
 * ./gradlew updateGradlew
 *
 * ./gradlew updateGradlew -P"gradlew.stage"="rc"
 * ```
 *
 * - If the input is not set, or it is `current`, the last stable version will be used.
 * - If the input is `rc`, the last release candidate version will be used, can be possible there is
 * no release candidate version.
 * - If the input is `nightly`, the last nightly version will be used.
 */
rootProject.tasks {
    val configureWrapper by tasks.registering {
        doLast {
            tasks.withType<Wrapper>().configureEach { gradleVersion = getSpecificGradleVersion() }
        }
    }
    tasks.register("updateGradlew") {
        group = "updater"
        description = "Check the latest Gradlew Wrapper version"

        dependsOn(configureWrapper)

        if (tasks.withType<Wrapper>().toList().all { it.gradleVersion.isNotBlank() }) {
            finalizedBy(tasks.withType<Wrapper>())
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

private val baseUrl = "https://services.gradle.org/versions"
private val currentUrl = "$baseUrl/current"
private val releaseCandidateUrl = "$baseUrl/release-candidate"
private val nightlyUrl = "https://services.gradle.org/versions/nightly"

private val reset = "\u001B[0m"
private val cyan = "\u001B[36m"
private val yellow = "\u001B[33m"
private val green = "\u001B[32m"
private val red = "\u001b[31m"

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

    (URL(url).openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        if (responseCode !in 200..299) {
            error("There is an error getting the last Gradle version (code: $responseCode")
        }

        inputStream.bufferedReader().use {
            val version =
                it.lines()
                    .asSequence()
                    .firstOrNull { line -> line.contains("version") }
                    ?.replace(" ", "")
                    ?.replace("version", "")
                    ?.replace(":", "")
                    ?.replace("\"", "")
                    ?.replace(",", "")

            return if (version.isNullOrBlank() && stage == Stage.RC) {
                error("${red}There is no release candidate Gradle version available$reset")
            } else {
                version?.also {
                    File("${project.rootProject.buildDir}/versions/gradle-wrapper.txt").apply {
                        ensureParentDirsCreated()
                        createNewFile()
                        writeText(version)
                    }

                    logger.lifecycle(
                        "The last ${cyan}Gradle version$reset for the stage $yellow$stage$reset is $green$version$reset"
                    )
                }
                    ?: error("${red}Gradle version not found or the service is not available$reset")
            }
        }
    }
}

val isWindows: Boolean
    get() = System.getProperty("os.name").contains("windows", true)
