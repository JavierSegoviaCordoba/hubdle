
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
        val massiveCatalogs: String by settings

        create("libs") { from("com.javiersc.massive-catalogs:libs-catalog:$massiveCatalogs") }

        create("pluginLibs") {
            from("com.javiersc.massive-catalogs:plugins-catalog:$massiveCatalogs")
        }
    }
}

include("core")

include(
    "all-plugins",
    "plugin-accessors",
)

include(
    "android-library",
    "all-projects",
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
