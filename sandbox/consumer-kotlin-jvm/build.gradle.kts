plugins {
    kotlin("jvm")
    `javiersc-kotlin-config`
    application
}

application {
    mainClass.set("com.javiersc.gradle.plugins.sandbox.MainKt")
}
