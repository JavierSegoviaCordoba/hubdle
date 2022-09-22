pluginManagement {
    val hubdleVersionProp = providers.gradleProperty("hubdleVersion").orNull
    val hubdleVersion: String =
        hubdleVersionProp
            ?: file("$rootDir/gradle/libs.versions.toml")
                .readLines()
                .first { it.contains("hubdle") }
                .split("\"")[1]

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        if (hubdleVersionProp != null) mavenLocal()
    }

    plugins {
        id("com.javiersc.hubdle.settings") version hubdleVersion
    }
}

plugins {
    id("com.javiersc.hubdle.settings")
}

val hubdleVersion: String? = providers.gradleProperty("hubdleVersion").orNull

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
        if (hubdleVersion != null) mavenLocal()
    }
    if (hubdleVersion != null) {
        versionCatalogs {
            create("libs") {
                version("hubdle", hubdleVersion)
            }
        }
    }
}

hubdleSettings {
    autoInclude {
        excludedBuilds("sandbox")
    }
}
