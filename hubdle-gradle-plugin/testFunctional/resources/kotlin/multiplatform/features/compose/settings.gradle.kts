@file:Suppress("PackageDirectoryMismatch")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
    }
}

plugins { //
    id("com.javiersc.hubdle")
}

hubdleSettings {
    catalog {
        isEnabled.set(false)
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
        mavenLocalTest()
    }

    versionCatalogs {
        register("hubdle") {
            from(files("gradle/hubdle.libs.versions.toml"))
        }
    }
}
