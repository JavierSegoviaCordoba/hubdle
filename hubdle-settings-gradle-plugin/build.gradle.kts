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
            "hubdle",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.hubdle.settings") {
            id = "com.javiersc.hubdle.settings"
            displayName = "Hubdle"
            description = "Easy settings setup"
            implementationClass = "com.javiersc.hubdle.HubdleSettingsPlugin"
        }
    }
}

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    api(libs.javiersc.gradle.gradleExtensions)
    implementation(pluginLibs.gradle.enterprise.comGradleEnterpriseGradlePlugin)

    testImplementation(gradleTestKit())
    testImplementation(libs.javiersc.gradle.gradleTestExtensions)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks { pluginUnderTestMetadata { pluginClasspath.from(testPluginClasspath) } }
