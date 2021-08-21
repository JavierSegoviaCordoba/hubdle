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
    ":a--dependencies:libs",
    ":a--dependencies:plugins",
)

include(
    "core",
    "publishing-core",
)

include(
    "all-plugins",
    "plugin-accessors",
)

include(
    "android-library",
    "all-projects",
    "build-version-catalogs",
    "build-version-catalogs-updater",
    "changelog",
    "code-analysis",
    "code-formatter",
    "dependency-updates",
    "docs",
    "gradle-wrapper-updater",
    "kotlin-multiplatform",
    ":massive-catalogs-updater",
    "nexus",
    "publish-android-library",
    "publish-gradle-plugin",
    "publish-kotlin-jvm",
    "publish-kotlin-multiplatform",
    "publish-version-catalog",
    "readme-badges",
    "versioning",
)
