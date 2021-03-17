
rootProject.name = providers.gradleProperty("libName").forUseAtConfigurationTime().get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
            content {
                includeGroup("com.javiersc.massive-catalogs")
            }
        }
    }

    versionCatalogs {
        create("libs") { from("com.javiersc.massive-catalogs:libs-catalog:0.2.0-SNAPSHOT") }
        create("pluginLibs") { from("com.javiersc.massive-catalogs:plugins-catalog:0.2.0-SNAPSHOT") }
    }
}

include("core")

include("accessors")

include("changelog")
include("code-analysis")
include("code-formatter")
include("dependency-updates")
include("docs")
include("kotlin-multiplatform")
include("nexus")
include("publish")
include("readme-badges-generator")
include("versioning")
