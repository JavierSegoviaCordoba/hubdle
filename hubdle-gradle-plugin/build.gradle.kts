import com.javiersc.gradle.extensions.version.catalogs.artifact
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.gradle.version.isSnapshot
import com.javiersc.hubdle.logic.GenerateHubdleTask
import com.javiersc.hubdle.logic.GenerateHubdleTask.HubdleDependency
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import kotlin.jvm.optionals.getOrNull
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

val kotlinVersion: String? =
    getStringProperty("kotlinVersion").orNull.takeIf(String?::isNotNullNorBlank)
val semverTagPrefix: String =
    getStringProperty("semver.tagPrefix").orNull.takeIf { it != "null" } ?: "p"

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
                tagPrefix.set("p")
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
                                create("hubdle") {
                                    id = "com.javiersc.hubdle"
                                    displayName = "Hubdle"
                                    description = "Easy setup for projects or settings"
                                    implementationClass = "com.javiersc.hubdle.HubdlePlugin"
                                    tags.set(listOf("hubdle"))
                                }
                                create("hubdle project") {
                                    id = "com.javiersc.hubdle.project"
                                    displayName = "Hubdle project"
                                    description = "Easy setup for each kind of project"
                                    implementationClass =
                                        "com.javiersc.hubdle.project.HubdleProjectPlugin"
                                    tags.set(listOf("hubdle project"))
                                }
                                create("hubdle settings") {
                                    id = "com.javiersc.hubdle.settings"
                                    displayName = "Hubdle settings"
                                    description = "Easy settings setup"
                                    implementationClass =
                                        "com.javiersc.hubdle.settings.HubdleSettingsPlugin"
                                    tags.set(listOf("hubdle settings"))
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
                    api(hubdle.plugins.github.gradle.nexus.publish.plugin.artifact)
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

tasks {
    val hubdleCodegen: TaskProvider<GenerateHubdleTask> =
        register<GenerateHubdleTask>("generateHubdle")

    val catalog: VersionCatalog = versionCatalogs.named("hubdle")

    val catalogDependencies: Provider<List<HubdleDependency>> = provider {
        catalog.libraryAliases
            .asSequence()
            .mapNotNull { catalog.findLibrary(it).getOrNull()?.orNull }
            .map { HubdleDependency("${it.module}", it.versionConstraint.displayName) }
            .toList()
    }

    hubdleCodegen.configure {
        group = "build"

        libraries.set(catalogDependencies)
        libraryAliases.set(provider { catalog.libraryAliases })
        pluginAliases.set(provider { catalog.pluginAliases })
    }

    the<KotlinProjectExtension>().sourceSets.named("main").configure {
        kotlin.srcDirs(hubdleCodegen)
    }
}
