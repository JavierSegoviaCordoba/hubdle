pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
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
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
    }

    versionCatalogs {
        register("hubdle") {
            from(files("gradle/hubdle.libs.versions.toml"))
        }
    }
}
