pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("com.javiersc.hubdle.settings") version "0.6.0"
}

hubdle {
    autoInclude {
//        excludes(":library")
//        excludedBuilds("included-library")
    }
}
