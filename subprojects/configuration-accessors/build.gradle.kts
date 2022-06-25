plugins {
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    api(projects.subprojects.properties)
    api(libs.javiersc.semver.semverCore)

    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(pluginLibs.android.toolsBuild.gradle)
    implementation(pluginLibs.diffplug.spotless.spotlessPluginGradle)
    implementation(pluginLibs.gradle.publish.pluginPublishPlugin)
    implementation(pluginLibs.javiersc.semver.semverGradlePlugin)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
