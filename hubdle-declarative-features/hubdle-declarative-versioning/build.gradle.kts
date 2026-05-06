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

    gradle {
        plugin {
            pluginUnderTestProjects(projects.hubdleDeclarative)
        }
    }

    kotlin {
        jvm {
            features {
                jvmVersion(JavaVersion.VERSION_17)
                kotest.enabled(true)
            }

            main {
                dependencies {
                    implementation(hubdle.javiersc.gradle.extensions)
                    implementation(hubdle.javiersc.gradle.test.extensions)

                    api(projects.platform)
                }

                resources.srcDirs(file(rootDir.resolve("gradle/hubdle")))
            }

            testFixtures()
            testFunctional {
                dependencies { //
                    implementation(projects.hubdleDeclarative)
                    implementation(testFixtures(projects.platform))
                }
            }
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
    matches(Regex("""(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)-dev-(0|[1-9]\\d*)"""))
