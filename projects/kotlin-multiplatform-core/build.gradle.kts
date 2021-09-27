plugins {
    kotlin("jvm")
    `javiersc-publish-kotlin-jvm`
}

dependencies {
    implementation(gradleApi())
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginApi)
}
