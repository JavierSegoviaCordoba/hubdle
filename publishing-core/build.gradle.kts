plugins {
    kotlin("jvm")
    `javiersc-publish-kotlin-jvm`
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    api(projects.core)
}
