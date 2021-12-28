plugins {
    id("com.javiersc.gradle.plugins.readme.badges.generator")
}

allprojects {
    version = "0.1.1-SNAPSHOT"
    group = "com.javiersc.example"
}

readmeBadges {
    mavenCentral.set(false)
    quality.set(false)
}
