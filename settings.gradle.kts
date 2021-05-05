
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

        create("pluginLibs") {
            from("com.javiersc.massive-catalogs:plugins-catalog:$massiveCatalogs")
        }
    }
}

include("core")

include("accessors")
include("all-plugins")

include("all-projects")
include("changelog")
include("code-analysis")
include("code-formatter")
include("dependency-updates")
include("docs")
include("kotlin-multiplatform")
include("nexus")
include("publish-android-library")
include("publish-gradle-plugin")
include("publish-kotlin-jvm")
include("publish-kotlin-multiplatform")
include("publish-version-catalog")
include("readme-badges")
include("versioning")
