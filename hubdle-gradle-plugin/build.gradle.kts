plugins {
    `kotlin-dsl`
    `javiersc-kotlin-config`
    `javiersc-publish`
    `dependencies-codegen`
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
        create("com.javiersc.hubdle") {
            id = "com.javiersc.hubdle"
            displayName = "Hubdle"
            description = "Easy setup for each kind of project"
            implementationClass = "com.javiersc.hubdle.HubdlePlugin"
        }
    }
}

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    api(pluginLibs.adarshr.gradleTestLoggerPlugin)
    api(pluginLibs.diffplug.spotless.spotlessPluginGradle)
    api(pluginLibs.github.gradleNexus.publishPlugin)
    api(pluginLibs.gitlab.arturboschDetekt.detektGradlePlugin)
    api(pluginLibs.gradle.kotlin.gradleKotlinDslPlugins)
    api(pluginLibs.gradle.publish.pluginPublishPlugin)
    api(pluginLibs.javiersc.semver.semverGradlePlugin)
    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
    api(pluginLibs.jetbrains.intellijPlugins.gradleChangelogPlugin)
    api(pluginLibs.jetbrains.intellijPlugins.gradleIntellijPlugin)
    api(pluginLibs.jetbrains.kotlinx.binaryCompatibilityValidator)
    api(pluginLibs.jetbrains.kotlinx.kover)
    api(pluginLibs.jetbrains.kotlinx.serialization)
    api(pluginLibs.sonarqube.scannerGradle.sonarqubeGradlePlugin)
    api(pluginLibs.vyarus.gradleMkdocsPlugin)

    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.compose.composeGradlePlugin)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(libs.eclipse.jgit)
    implementation(libs.javiersc.gradle.gradleExtensions)
    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(libs.javiersc.semver.semverCore)

    testImplementation(gradleTestKit())
    testImplementation(libs.javiersc.gradle.gradleTestExtensions)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)

    testPluginClasspath("${pluginLibs.android.toolsBuild.gradle.get().module}:7.2.1")
    testPluginClasspath("${pluginLibs.jetbrains.kotlin.kotlinGradlePlugin.get().module}:1.6.21")
}

tasks { pluginUnderTestMetadata { pluginClasspath.from(testPluginClasspath) } }
