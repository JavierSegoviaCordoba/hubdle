rootProject.name = providers.gradleProperty("allProjects.name").forUseAtConfigurationTime().get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    plugins {
        val buildVersionCatalogs: String by settings

        id("com.javiersc.gradle.plugins.build.version.catalogs") version buildVersionCatalogs
    }
}

plugins {
    id("com.javiersc.gradle.plugins.build.version.catalogs")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

include(
    ":a--catalogs:libs",
    ":a--catalogs:plugins",
)

include(
    ":projects:core",
    ":projects:publishing-core",
)

include(
    ":projects:all-plugins",
    ":projects:plugin-accessors",
)

include(
    ":projects:android-library",
    ":projects:all-projects",
    ":projects:build-version-catalogs",
    ":projects:build-version-catalogs-updater",
    ":projects:changelog",
    ":projects:code-analysis",
    ":projects:code-formatter",
    ":projects:dependency-updates",
    ":projects:docs",
    ":projects:gradle-wrapper-updater",
    ":projects:kotlin-multiplatform",
    ":projects:massive-catalogs-updater",
    ":projects:nexus",
    ":projects:publish-android-library",
    ":projects:publish-gradle-plugin",
    ":projects:publish-kotlin-jvm",
    ":projects:publish-kotlin-multiplatform",
    ":projects:publish-version-catalog",
    ":projects:readme-badges",
    ":projects:versioning",
)
