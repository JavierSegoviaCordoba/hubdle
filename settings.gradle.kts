import com.javiersc.gradle.properties.extensions.getBooleanProperty

pluginManagement {
    fun prop(name: String): String? =
        providers.gradleProperty(name).orNull?.takeIf(String::isNotBlank)
    val itselfVersionFromProp: String? = prop("itselfVersion")?.dropWhile { it.isLetter() }
    val itselfVersion: String =
        itselfVersionFromProp
            ?: file("$rootDir/gradle/hubdle.libs.versions.toml")
                .readLines()
                .first { it.startsWith("javiersc-hubdle") }
                .split("\"")[1]

    val kotlinVersionFromProp: String? = prop("kotlinVersion")
    val kotlinVersion: String? = kotlinVersionFromProp

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
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")

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
        val sandboxEnabled = getBooleanProperty("sandbox.enabled").orNull == true
        if (!sandboxEnabled) excludedBuilds("sandbox")
    }
    catalog.isEnabled.set(false)
}

fun prop(name: String): String? = providers.gradleProperty(name).orNull?.takeIf(String::isNotBlank)

val itselfVersion: String? = prop("itselfVersion")?.dropWhile { it.isLetter() }
val kotlinVersion: String? = prop("kotlinVersion")

dependencyResolutionManagement {
    repositories {
        if (itselfVersion != null) {
            mavenLocal()
        }
    }

    gradle.settingsEvaluated {
        versionCatalogs {
            removeIf { it.name == "hubdle" }
            create("hubdle") {
                if (itselfVersion != null) {
                    version("javiersc-hubdle", itselfVersion)
                }
                if (kotlinVersion != null) {
                    version("jetbrains-kotlin", kotlinVersion)
                }
                from(files(rootDir.resolve("gradle/hubdle.libs.versions.toml")))
            }
        }
    }
}
