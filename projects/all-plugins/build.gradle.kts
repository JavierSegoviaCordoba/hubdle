plugins {
    kotlin("jvm")
    `javiersc-publish-kotlin-jvm`
}

dependencies {
    implementation(gradleApi())
    api(projects.projects.pluginAccessors)
    api(projects.projects.core)

    api(projects.projects.allProjects)
    api(projects.projects.androidLibrary)
    api(projects.projects.buildVersionCatalogs)
    api(projects.projects.buildVersionCatalogsUpdater)
    api(projects.projects.changelog)
    api(projects.projects.codeAnalysis)
    api(projects.projects.codeFormatter)
    api(projects.projects.dependencyUpdates)
    api(projects.projects.docs)
    api(projects.projects.gradleWrapperUpdater)
    api(projects.projects.kotlinMultiplatform)
    api(projects.projects.massiveCatalogsUpdater)
    api(projects.projects.nexus)
    api(projects.projects.publishAndroidLibrary)
    api(projects.projects.publishGradlePlugin)
    api(projects.projects.publishKotlinJvm)
    api(projects.projects.publishKotlinMultiplatform)
    api(projects.projects.publishVersionCatalog)
    api(projects.projects.readmeBadges)
    api(projects.projects.versioning)
}
