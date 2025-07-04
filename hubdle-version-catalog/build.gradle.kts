import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.gradle.version.isSnapshot
import com.javiersc.kotlin.stdlib.isNotNullNorBlank

val kotlinVersion: String? =
    getStringProperty("kotlinVersion").orNull.takeIf(String?::isNotNullNorBlank)

val isPublishToMavenLocalTest: Boolean =
    gradle.startParameter.taskNames.contains("publishToMavenLocalTest")

hubdle {
    config {
        analysis()
        coverage()
        documentation { //
            changelog()
        }
        publishing {
            maven {
                repositories { //
                    mavenLocalTest()
                }
            }
        }
        versioning {
            semver {
                tagPrefix.set(if (!isPublishToMavenLocalTest) "c" else "")
                val hasSameTagPrefix =
                    getStringProperty("semver.tagPrefix").orNull == tagPrefix.get()
                if (kotlinVersion.isNotNullNorBlank() && hasSameTagPrefix) {
                    mapVersion { gradleVersion ->
                        gradleVersion.mapIfKotlinVersionIsProvided(kotlinVersion)
                    }
                }
            }
        }
    }

    gradle {
        versionCatalogs {
            catalog {
                toml(rootDir.resolve("gradle/hubdle.libs.versions.toml"))
                if (kotlinVersion.isNotNullNorBlank()) {
                    version("jetbrains-kotlin", kotlinVersion)
                }
            }
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
