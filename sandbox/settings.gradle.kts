enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("pluginLibs") {
            from(files("../gradle/plugin.libs.versions.toml"))
        }
    }
}

include(":consumer-kotlin-jvm")
include(":consumer-kotlin-multiplatform")
includeBuild("consumer-settings")
