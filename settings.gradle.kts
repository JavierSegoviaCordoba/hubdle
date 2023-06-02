import com.javiersc.gradle.properties.extensions.getBooleanProperty

pluginManagement {
    val itselfVersionFromProp: String? = providers.gradleProperty("itselfVersion").orNull
    val itselfVersion: String =
        itselfVersionFromProp
            ?: file("$rootDir/gradle/hubdle.libs.versions.toml")
                .readLines()
                .first { it.contains("hubdle") }
                .split("\"")[1]

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        if (itselfVersionFromProp != null) mavenLocal()
        mavenLocal()
    }

    plugins { //
        id("com.javiersc.hubdle") version itselfVersion
    }
}

plugins { //
    id("com.javiersc.hubdle")
}

hubdleSettings {
    autoInclude {
        val sandboxEnabled = getBooleanProperty("sandbox.enabled").orNull ?: false
        if (!sandboxEnabled) excludedBuilds("sandbox")
    }
}

val itselfVersion: String? = providers.gradleProperty("itselfVersion").orNull

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev/")
        if (itselfVersion != null) mavenLocal()
    }

    gradle.settingsEvaluated {
        versionCatalogs {
            removeIf { it.name == "hubdle" }
            create("hubdle") {
                if (itselfVersion != null) {
                    version("hubdle", itselfVersion)
                }
                from(files(rootDir.resolve("gradle/hubdle.libs.versions.toml")))
            }
        }
    }
}
