rootProject.name = providers.gradleProperty("allProjects.name").forUseAtConfigurationTime().get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") { from(files("gradle/libs.toml")) }
        create("pluginLibs") { from(files("gradle/pluginLibs.toml")) }
    }
}

include(
    ":projects:android-core",
    ":projects:core",
    ":projects:kotlin-multiplatform-core",
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
    ":projects:kotlin-jvm",
    ":projects:kotlin-multiplatform",
    ":projects:kotlin-multiplatform-no-android",
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
