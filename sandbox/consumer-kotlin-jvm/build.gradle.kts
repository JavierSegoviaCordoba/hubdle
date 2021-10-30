plugins {
    kotlin("jvm")
    `javiersc-kotlin-library`
    application
}

application {
    mainClass.set("com.javiersc.gradle.plugins.sandbox.MainKt")
}
