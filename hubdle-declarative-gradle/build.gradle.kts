import com.javiersc.gradle.extensions.version.catalogs.artifact
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.gradle.version.isSnapshot
import com.javiersc.kotlin.stdlib.isNotNullNorBlank

val kotlinVersion: String? =
    getStringProperty("kotlinVersion").orNull.takeIf(String?::isNotNullNorBlank)
val semverTagPrefix: String =
    getStringProperty("semver.tagPrefix").orNull.takeIf { it != "null" } ?: "d"

hubdle {
    config {
        analysis()
        coverage()
        documentation { //
            api()
        }
        explicitApi()
        languageSettings { //
            experimentalStdlibApi()
        }
        projectConfig()
        publishing {
            maven {
                repositories { //
                    mavenLocalTest()
                }
            }
        }
        versioning {
            semver { //
                tagPrefix.set("d")
                val hasSameTagPrefix: Boolean = semverTagPrefix == tagPrefix.get()
                if (kotlinVersion.isNotNullNorBlank() && hasSameTagPrefix) {
                    mapVersion { gradleVersion ->
                        gradleVersion.mapIfKotlinVersionIsProvided(kotlinVersion)
                    }
                }
            }
        }
        testing {
            test { //
                systemProperties["KOTLIN_VERSION"] = kotlinVersion.orEmpty()
            }
        }
    }

    kotlin {
        jvm {
            features {
                jvmVersion(JavaVersion.VERSION_11)

                gradle {
                    plugin {
                        gradlePlugin {
                            plugins {
                                val baseId = "com.javiersc.hubdle.declarative"

                                create("hubdle-declarative") {
                                    id = baseId
                                    displayName = "Hubdle Declarative"
                                    description = "Conventions using Gradle Declarative"
                                    implementationClass = "$baseId.HubdleDeclarativePlugin"
                                    tags.set(
                                        listOf(
                                            "hubdle",
                                            "settings",
                                            "project",
                                            "declarative",
                                            "Gradle Declarative",
                                        ),
                                    )
                                }
                                create("hubdle-declarative-project") {
                                    id = "$baseId.project"
                                    displayName = "Hubdle Declarative Project"
                                    description = "Conventions using Gradle Declarative"
                                    implementationClass = "$baseId.HubdleDeclarativeProjectPlugin"
                                    tags.set(
                                        listOf(
                                            "hubdle",
                                            "project",
                                            "declarative",
                                            "Gradle Declarative",
                                        ),
                                    )
                                }
                                create("hubdle-declarative-settings") {
                                    id = "$baseId.settings"
                                    displayName = "Hubdle Declarative Settings"
                                    description = "Conventions using Gradle Declarative"
                                    implementationClass = "$baseId.HubdleDeclarativeSettingsPlugin"
                                    tags.set(
                                        listOf(
                                            "hubdle",
                                            "settings",
                                            "declarative",
                                            "Gradle Declarative",
                                        ),
                                    )
                                }
                            }
                        }

                        // TODO: Fix when fixed
                        pluginUnderTestExternalDependencies(
                            hubdle.android.tools.build.gradle,
                            hubdle.jetbrains.kotlin.gradle.plugin.asProvider(),
                        )
                    }
                }
                kotest.enabled(true)
            }

            main {
                dependencies {
                    // TODO: REMOVE WHEN FIXED
                    implementation(hubdle.javiersc.gradle.extensions)
                    implementation(hubdle.javiersc.gradle.test.extensions)

                    api(hubdle.android.tools.build.gradle)
                    api(hubdle.jetbrains.kotlin.gradle.plugin)
                    api(hubdle.jetbrains.kotlin.serialization)

                    api(hubdle.plugins.adarshr.test.logger.artifact)
                    api(hubdle.plugins.cash.sqldelight.artifact)
                    api(hubdle.plugins.codingfeline.buildkonfig.artifact)
                    api(hubdle.plugins.diffplug.gradle.spotless.artifact)
                    api(hubdle.plugins.gitlab.arturbosch.detekt.artifact)
                    api(hubdle.plugins.gradle.develocity.artifact)
                    api(hubdle.plugins.gradle.plugin.publish.artifact)
                    api(hubdle.plugins.gradle.test.retry.artifact)
                    api(hubdle.plugins.javiersc.kotlin.kopy.artifact)
                    api(hubdle.plugins.javiersc.semver.artifact)
                    api(hubdle.plugins.jetbrains.changelog.artifact)
                    api(hubdle.plugins.jetbrains.compose.artifact)
                    api(hubdle.plugins.jetbrains.dokka.artifact)
                    api(hubdle.plugins.jetbrains.intellij.artifact)
                    api(hubdle.plugins.jetbrains.kotlin.plugin.atomicfu.artifact)
                    api(hubdle.plugins.jetbrains.kotlin.plugin.compose.artifact)
                    api(hubdle.plugins.jetbrains.kotlin.plugin.serialization.artifact)
                    api(hubdle.plugins.jetbrains.kotlinx.binary.compatibility.validator.artifact)
                    api(hubdle.plugins.jetbrains.kotlinx.kover.artifact)
                    api(hubdle.plugins.sonarqube.artifact)
                    api(hubdle.plugins.vyarus.mkdocs.artifact)

                    compileOnly(hubdle.jetbrains.kotlin.compiler.internal.test.framework)

                    implementation(hubdle.eclipse.jgit)
                }

                resources.srcDirs(file(rootDir.resolve("gradle/hubdle")))
            }

            testFixtures()
            testFunctional()
            testIntegration()
        }
    }
}

fun GradleVersion.mapIfKotlinVersionIsProvided(kotlinVersion: String): String {
    val major: Int = major
    val minor: Int = minor
    val patch: Int = patch

    val isKotlinDevVersion = kotlinVersion.isKotlinDevVersion() || kotlinVersion.contains("dev")
    val isSnapshotStage = isSnapshot || getStringProperty("semver.stage").orNull?.isSnapshot == true

    val version: String =
        if (isKotlinDevVersion || isSnapshotStage) {
            "$major.$minor.$patch+$kotlinVersion-SNAPSHOT"
        } else {
            "$major.$minor.$patch+$kotlinVersion"
        }
    return version
}

fun String.isKotlinDevVersion(): Boolean =
    matches(Regex("""(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)-dev-(0|[1-9]\d*)"""))
