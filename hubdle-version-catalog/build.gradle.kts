import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.gradle.version.isSnapshot
import com.javiersc.kotlin.stdlib.isNotNullNorBlank

val kotlinVersion: String? =
    getStringProperty("kotlinVersion").orNull.takeIf(String?::isNotNullNorBlank)

hubdle {
    config {
        analysis()
        coverage()
        documentation { //
            changelog()
        }
        publishing()
        versioning {
            semver {
                tagPrefix.set("c")
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

    kotlin {
        jvm {
            features {
                gradle {
                    versionCatalogs {
                        catalog {
                            toml(rootDir.resolve("gradle/hubdle.libs.versions.toml"))
                            if (kotlinVersion.isNotNullNorBlank()) {
                                version("kotlin") {
                                    strictly(kotlinVersion)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun GradleVersion.mapIfKotlinVersionIsProvided(kotlinVersion: String): String {
    val major: Int = major
    val minor: Int = minor
    val patch: Int = patch
    check(kotlinVersion.isKotlinDevVersion()) {
        """ |Kotlin version: $kotlinVersion
            |Requirements to use a specific Kotlin version:  
            | - It must be a dev version, for example: `1.9.20-dev-5788`
            |Check the Kotlin dev versions on the bootstrap repository:
            |  - https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap/org/jetbrains/kotlin/ 
        """
            .trimMargin()
    }

    check(isSnapshot) {
        """ |Current version: ${this@mapIfKotlinVersionIsProvided}
            |Kotlin version: $kotlinVersion
            |Requirements to use a specific Kotlin version:  
            | - Use a SNAPSHOT version with `-P semver.stage=snapshot`
            | - Clean repo or use `-P semver.checkClean=false`
        """
            .trimMargin()
    }
    return "$major.$minor.$patch+$kotlinVersion-SNAPSHOT"
}

fun String.isKotlinDevVersion(): Boolean =
    matches(Regex("""(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)-dev-(0|[1-9]\d*)"""))
