plugins {
    id("com.javiersc.gradle.plugins.readme.badges.generator")
}

allprojects {
    version = "0.1.1-SNAPSHOT"
    group = "com.javiersc.example"
}

readmeBadges {
    kotlin.set(false)
    mavenCentral.set(false)
    snapshots.set(false)
    build.set(false)
    coverage.set(false)
    quality.set(false)
    techDebt.set(false)
}
