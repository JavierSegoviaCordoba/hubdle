plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

kotlin {
    explicitApi()

    sourceSets.all {
        languageSettings {
            optIn("kotlin.ExperimentalStdlibApi")
        }
    }
}

pluginBundle {
    tags =
        listOf(
            "hubdle settings",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.hubdle.settings") {
            id = "com.javiersc.hubdle.settings"
            displayName = "Hubdle settings"
            description = "Easy settings setup"
            implementationClass = "com.javiersc.hubdle.settings.HubdleSettingsPlugin"
        }
    }
}

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    api(libs.javiersc.gradle.gradleExtensions)
    api(pluginLibs.gradle.enterprise.comGradleEnterpriseGradlePlugin)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    testImplementation(gradleTestKit())
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks { pluginUnderTestMetadata { pluginClasspath.from(testPluginClasspath) } }
