plugins {
    `javiersc-versioning`
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

kotlin { explicitApi() }

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
    api(projects.subprojects.properties)



    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(libs.javiersc.kotlin.kotlinStdlib)

    implementation(projects.subprojects.extensions)

    implementation(pluginLibs.android.toolsBuild.gradle)
    implementation(pluginLibs.github.tripletGradle.playPublisher)
    implementation(pluginLibs.gradle.publish.pluginPublishPlugin)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    implementation(pluginLibs.javiersc.semver.semverGradlePlugin)

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
