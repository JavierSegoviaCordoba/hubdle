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
                tags("hubdle settings")
                gradlePlugin {
                    plugins {
                        create("hubdle settings") {
                            id = "com.javiersc.hubdle.settings"
                            displayName = "Hubdle settings"
                            description = "Easy settings setup"
                            implementationClass =
                                "com.javiersc.hubdle.settings.HubdleSettingsPlugin"
                        }
                    }
                }
                main {
                    dependencies {
                        api(javierscGradleExtensions())
                        api(libs.gradle.enterprise.comGradleEnterpriseGradlePlugin)
                    }
                }
                pluginUnderTestDependencies(
                    libs.android.toolsBuild.gradle,
                    libs.jetbrains.kotlin.kotlinGradlePlugin,
                )
            }
        }
    }
}
