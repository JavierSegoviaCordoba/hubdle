plugins {
    `javiersc-versioning`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    implementation(libs.javiersc.semver.semverCore)
}
