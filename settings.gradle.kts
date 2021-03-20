
rootProject.name = providers.gradleProperty("libName").forUseAtConfigurationTime().get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        jcenter()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") { from("com.javiersc.massive-catalogs:libs-catalog:0.1.0-alpha.3") }
        create("pluginLibs") { from("com.javiersc.massive-catalogs:plugins-catalog:0.1.0-alpha.3") }
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
include("publish-gradle-plugin")
include("readme-badges-generator")
include("versioning")
