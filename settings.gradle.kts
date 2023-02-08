import com.javiersc.gradle.properties.extensions.getPropertyOrNull

pluginManagement {
    val itselfVersionFromProp: String? = providers.gradleProperty("itselfVersion").orNull
    val itselfVersion: String =
        itselfVersionFromProp
            ?: file("$rootDir/gradle/libs.versions.toml")
                .readLines()
                .first { it.contains("hubdle") }
                .split("\"")[1]

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        if (itselfVersionFromProp != null) mavenLocal()
    }

    plugins { id("com.javiersc.hubdle.settings") version itselfVersion }
}

plugins { id("com.javiersc.hubdle.settings") }

val itselfVersion: String? = providers.gradleProperty("itselfVersion").orNull

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
        if (itselfVersion != null) mavenLocal()
    }
    if (itselfVersion != null) {
        versionCatalogs { create("libs") { version("hubdle", itselfVersion) } }
    }
}

hubdleSettings {
    autoInclude {
        val sandboxEnabled = getPropertyOrNull("sandbox.enabled")?.toBoolean() ?: false
        if (!sandboxEnabled) excludedBuilds("sandbox")
    }
}
