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
        publishing()
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

    kotlin {
        jvm {
            features {
                jvmVersion(JavaVersion.VERSION_11)
                kotest.enabled(true)
            }

            main {
                dependencies {
                    implementation(gradleApi())
                    implementation(gradleKotlinDsl())
                }
            }

            testFixtures()
            testFunctional()
            testIntegration()
        }
    }
}
