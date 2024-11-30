pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("com.javiersc.hubdle.declarative.config.documentation.readme.badges.settings")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

include(":library-a")
