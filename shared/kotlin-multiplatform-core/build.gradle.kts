plugins {
    kotlin("jvm")
    `javiersc-publish`
}

dependencies {
    implementation(gradleApi())
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
