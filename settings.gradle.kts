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

include(":all-plugins")

include(
    ":plugins:code-enhancers-and-utils:all-projects",
    ":plugins:code-enhancers-and-utils:code-analysis",
    ":plugins:code-enhancers-and-utils:code-formatter",
    ":plugins:code-enhancers-and-utils:dependency-updates",
    ":plugins:code-enhancers-and-utils:versioning",
)

include(
    ":plugins:documentation:changelog",
    ":plugins:documentation:docs",
    ":plugins:documentation:readme-badges",
)

include(
    ":plugins:gradle-utils:gradle-wrapper-updater",
)

include(
    ":plugins:libraries-and-apps-config:android-library",
    ":plugins:libraries-and-apps-config:kotlin-jvm",
    ":plugins:libraries-and-apps-config:kotlin-multiplatform",
    ":plugins:libraries-and-apps-config:kotlin-multiplatform-no-android",
)

include(
    ":plugins:publish:nexus",
    ":plugins:publish:publish",
)

include(
    ":plugins:version-catalogs:build-version-catalogs",
    ":plugins:version-catalogs:build-version-catalogs-updater",
    ":plugins:version-catalogs:massive-catalogs-updater",
    ":plugins:version-catalogs:projects-version-catalog",
)

include(
    ":shared:android-core",
    ":shared:core",
    ":shared:core-test",
    ":shared:kotlin-multiplatform-core",
    ":shared:plugin-accessors",
)
