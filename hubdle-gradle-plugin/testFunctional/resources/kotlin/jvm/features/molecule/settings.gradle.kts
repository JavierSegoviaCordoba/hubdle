pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
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
