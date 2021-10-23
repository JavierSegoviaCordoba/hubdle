plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    // TODO: remove when the next issue is fixed:
    //  https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(files(pluginLibs.javaClass.superclass.protectionDomain.codeSource.location))

// TODO: REMOVE IF NOT NEEDED
//    implementation(libs.google.codeGson.gson)
//    implementation(libs.jsoup.jsoup)
//    implementation(libs.javiersc.semanticVersioning.semanticVersioningCore)

    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    implementation(pluginLibs.jetbrains.kotlinx.binaryCompatibilityValidator)
    implementation(pluginLibs.javiersc.gradlePlugins.allPlugins)
}
