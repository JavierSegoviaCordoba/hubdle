plugins {
    `javiersc-versioning`
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

    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
}
