plugins {
    kotlin("jvm")
    `javiersc-publish`
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    implementation(gradleTestKit())
    implementation(libs.eclipse.jgit)
    implementation(libs.jetbrains.kotlin.kotlinTest)
    implementation(libs.kotest.kotestAssertionsCore)
}
