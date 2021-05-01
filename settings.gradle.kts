
rootProject.name = providers.gradleProperty("libName").forUseAtConfigurationTime().get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven {
            setUrl("https://oss.sonatype.org/content/repositories/snapshots")
            content {
                includeGroup("com.javiersc.massive-catalogs")
            }
        }
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

include("accessors")
include("all-plugins")

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
include("readme-badges-generator")
include("versioning")
