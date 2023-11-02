import com.javiersc.gradle.properties.extensions.getBooleanProperty

pluginManagement {
    val itselfVersionFromProp: String? = providers.gradleProperty("itselfVersion").orNull
    val itselfVersion: String =
        itselfVersionFromProp
            ?: file("$rootDir/gradle/hubdle.libs.versions.toml")
                .readLines()
                .first { it.startsWith("hubdle") }
                .split("\"")[1]

    val kotlinVersionFromProp: String? = providers.gradleProperty("kotlinVersion").orNull
    val kotlinVersion: String? = kotlinVersionFromProp

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        if (kotlinVersion != null) {
            maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
        }
        if (itselfVersionFromProp != null) {
            mavenLocal()
        }
    }

    plugins { //
        id("com.javiersc.hubdle") version itselfVersion
        if (kotlinVersion != null) id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
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
    catalog.isEnabled.set(false)
}

val itselfVersion: String? = providers.gradleProperty("itselfVersion").orNull
val kotlinVersion: String? = providers.gradleProperty("kotlinVersion").orNull

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        if (kotlinVersion != null) {
            maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
        }
        if (itselfVersion != null) {
            mavenLocal()
        }
    }

    gradle.settingsEvaluated {
        versionCatalogs {
            removeIf { it.name == "hubdle" }
            create("hubdle") {
                if (itselfVersion != null) {
                    version("hubdle", itselfVersion)
                }
                if (kotlinVersion != null) {
                    version("kotlin") { //
                        strictly(kotlinVersion)
                    }
                }
                from(files(rootDir.resolve("gradle/hubdle.libs.versions.toml")))
            }
        }
    }
}
