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
    api(projects.pluginAccessors)
    api(projects.core)

    api(projects.allProjects)
    api(projects.androidLibrary)
    api(projects.buildVersionCatalogs)
    api(projects.buildVersionCatalogsUpdater)
    api(projects.changelog)
    api(projects.codeAnalysis)
    api(projects.codeFormatter)
    api(projects.dependencyUpdates)
    api(projects.docs)
    api(projects.gradleWrapperUpdater)
    api(projects.kotlinMultiplatform)
    api(projects.massiveCatalogsUpdater)
    api(projects.nexus)
    api(projects.publishAndroidLibrary)
    api(projects.publishGradlePlugin)
    api(projects.publishKotlinJvm)
    api(projects.publishKotlinMultiplatform)
    api(projects.publishVersionCatalog)
    api(projects.readmeBadges)
    api(projects.versioning)
}
