enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        mavenLocal()
    }

    versionCatalogs {
        create("pluginLibs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

//include(":consumer-kotlin-android")
include(":consumer-kotlin-jvm")
//include(":consumer-kotlin-multiplatform")
//includeBuild("consumer-settings")
