enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    versionCatalogs {
        create("pluginLibs") {
            from(files("../gradle/pluginLibs.versions.toml"))
        }
    }
}

include(":consumer-kotlin-jvm")
include(":consumer-kotlin-multiplatform")
