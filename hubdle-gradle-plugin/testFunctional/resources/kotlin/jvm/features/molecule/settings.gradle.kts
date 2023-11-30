pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("com.javiersc.hubdle")
}

hubdleSettings {
    catalog {
        isEnabled.set(false)
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        register("hubdle") {
            from(files("gradle/hubdle.libs.versions.toml"))
        }
    }
}
