hubdle {
    config {
        analysis()
        documentation {
            api()
        }
        explicitApi()
        languageSettings {
            experimentalStdlibApi()
        }
        publishing()
    }

    kotlin {
        jvm {
            features {
                jvmVersion(JavaVersion.VERSION_11)

                gradle {
                    plugin {
                        gradlePlugin {
                            plugins {
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

                        pluginUnderTestDependencies(
                            libs.android.toolsBuild.gradle,
                            libs.jetbrains.kotlin.kotlinGradlePlugin,
                        )
                    }
                }
            }

            main {
                dependencies {
                    api(projects.hubdleProjectGradlePlugin)
                    api(javierscGradleExtensions())
                    api(libs.gradle.enterprise.comGradleEnterpriseGradlePlugin)
                }
            }
        }
    }
}
