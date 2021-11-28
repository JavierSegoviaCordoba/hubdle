plugins {
    `kotlin-jvm`
    `javiersc-kotlin-library`
    `javiersc-publish`
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    implementation(libs.javiersc.semver.semverCore)
}
