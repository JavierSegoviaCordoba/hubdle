plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        explicitApi()
        languageSettings {
            experimentalStdlibApi()
        }
        publishing()
    }

    kotlin {
        gradle {
            plugin {
                jvmVersion = 11

                tags("hubdle")

                gradlePlugin {
                    plugins {
                        create("hubdle") {
                            id = "com.javiersc.hubdle"
                            displayName = "Hubdle"
                            description = "Easy setup for projects or settings"
                            implementationClass = "com.javiersc.hubdle.HubdlePlugin"
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
}
