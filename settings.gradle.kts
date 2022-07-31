pluginManagement {
    val hubdleVersion: String =
        file("$rootDir/gradle/libs.versions.toml")
            .readLines()
            .first { it.contains("hubdle") }
            .split("\"")[1]

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    plugins {
        id("com.javiersc.hubdle.settings") version hubdleVersion
    }
}

plugins {
    id("com.javiersc.hubdle.settings")
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
        // mavenLocal()
    }
}

hubdleSettings {
    autoInclude {
        excludedBuilds("sandbox")
    }
}
