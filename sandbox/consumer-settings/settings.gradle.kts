pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("com.javiersc.hubdle.settings") version "0.2.1-SNAPSHOT"
}

hubdle {
    autoInclude {
//        excludes(":library")
//        excludedBuilds("included-library")
    }
}
