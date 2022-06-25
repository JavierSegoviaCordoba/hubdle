plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

kotlin {
    explicitApi()
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
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)
    api(projects.subprojects.configurationAccessors)
    api(projects.subprojects.properties)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(libs.eclipse.jgit)
    implementation(libs.javiersc.gradleExtensions.gradleExt)
    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(pluginLibs.android.toolsBuild.gradle)
    implementation(pluginLibs.diffplug.spotless.spotlessPluginGradle)
    implementation(pluginLibs.github.gradleNexus.publishPlugin)
    implementation(pluginLibs.gitlab.arturboschDetekt.detektGradlePlugin)
    implementation(pluginLibs.gradle.publish.pluginPublishPlugin)
    implementation(pluginLibs.javiersc.semver.semverGradlePlugin)
    implementation(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
    implementation(pluginLibs.jetbrains.intellijPlugins.gradleChangelogPlugin)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    implementation(pluginLibs.sonarqube.scannerGradle.sonarqubeGradlePlugin)
    implementation(pluginLibs.vyarus.gradleMkdocsPlugin)

    testImplementation(gradleTestKit())
    testImplementation(projects.shared.coreTest)
    testImplementation(libs.javiersc.gradleExtensions.gradleTestkitExt)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)

    testPluginClasspath(pluginLibs.android.toolsBuild.gradle)
    testPluginClasspath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}

tasks {
    pluginUnderTestMetadata {
        pluginClasspath.from(testPluginClasspath)
    }
}
