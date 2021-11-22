plugins {
    kotlin("jvm")
    `javiersc-publish`
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    implementation(libs.javiersc.semver.semverCore)
}
