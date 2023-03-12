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
                                create("hubdle") {
                                    id = "com.javiersc.hubdle"
                                    displayName = "Hubdle"
                                    description = "Easy setup for projects or settings"
                                    implementationClass = "com.javiersc.hubdle.HubdlePlugin"
                                    tags.set(listOf("hubdle"))
                                }
                            }
                        }
                    }
                }
            }

            main {
                dependencies {
                    api(projects.hubdleProjectGradlePlugin)
                    api(projects.hubdleSettingsGradlePlugin)
                }
            }
        }
    }
}
