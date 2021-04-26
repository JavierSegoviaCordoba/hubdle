plugins {
    kotlin("jvm")
    publish
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation(gradleApi())
    api(projects.accessors)
    api(projects.changelog)
    api(projects.codeAnalysis)
    api(projects.codeFormatter)
    api(projects.core)
    api(projects.dependencyUpdates)
    api(projects.docs)
    api(projects.kotlinMultiplatform)
    api(projects.nexus)
    api(projects.publishAndroidLibrary)
    api(projects.publishGradlePlugin)
    api(projects.publishKotlinJvm)
    api(projects.publishKotlinMultiplatform)
    api(projects.publishVersionCatalog)
    api(projects.readmeBadgesGenerator)
    api(projects.versioning)
}
