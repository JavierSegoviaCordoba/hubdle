import com.javiersc.gradle.extensions.version.catalogs.artifact
import com.javiersc.hubdle.logic.createGradlePlugin
import com.javiersc.hubdle.logic.getSemverTagPrefixOrElse
import com.javiersc.hubdle.logic.kotlinVersion
import com.javiersc.hubdle.logic.mapIfKotlinVersionIsProvided
import com.javiersc.kotlin.stdlib.isNotNullNorBlank

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
                val hasSameTagPrefix: Boolean = getSemverTagPrefixOrElse("d") == tagPrefix.get()
                if (kotlinVersion.isNotNullNorBlank() && hasSameTagPrefix) {
                    mapVersion { gradleVersion ->
                        mapIfKotlinVersionIsProvided(gradleVersion, kotlinVersion!!)
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
            gradlePlugin {
                plugins {
                    createGradlePlugin(
                        pluginDeclarationName = "config-documentation-readme-badges",
                        pluginName = "ReadmeBadges",
                    )
                }
            }
        }
    }

    kotlin {
        jvm {
            features {
                jvmVersion(JavaVersion.VERSION_17)
                kotest.enabled(true)
            }

            main {
                dependencies { //
                    implementation(projects.platform)
                    compileOnly(hubdle.plugins.jetbrains.kotlin.android.artifact)
                    compileOnly(hubdle.plugins.jetbrains.kotlin.jvm.artifact)
                    compileOnly(hubdle.plugins.jetbrains.kotlin.multiplatform.artifact)
                }
            }

            testFixtures()
            testFunctional()
            testIntegration()
        }
    }
}
