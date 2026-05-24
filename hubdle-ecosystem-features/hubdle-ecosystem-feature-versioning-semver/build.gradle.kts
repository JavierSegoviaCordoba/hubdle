import com.javiersc.gradle.extensions.version.catalogs.artifact
import com.javiersc.gradle.properties.extensions.getStringProperty

val semverTagPrefix: String =
    getStringProperty("semver.tagPrefix").orNull.takeIf { it != "null" } ?: "d"

hubdle {
    config {
        analysis()
        coverage()
        documentation { api() }
        explicitApi()
        languageSettings { experimentalStdlibApi() }
        projectConfig()
        publishing {
            maven {
                repositories { mavenLocalTest() }
            }
        }
        versioning {
            semver { tagPrefix.set("d") }
        }
    }

    gradle {
        plugin {
            pluginUnderTestProjects(projects.hubdleEcosystem)
            pluginUnderTestDependencies(hubdle.plugins.javiersc.semverFeatures.artifact)
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
                    implementation(hubdle.javiersc.gradle.test.extensions)
                    api(projects.hubdleEcosystemApi)
                }
            }
            testFunctional {
                dependencies {
                    implementation(projects.hubdleEcosystem)
                    implementation(testFixtures(projects.hubdleEcosystemApi))
                }
            }
        }
    }
}
