plugins {
    kotlin("jvm")
    `javiersc-publish-kotlin-jvm`
}

dependencies {
    implementation(gradleApi())
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
