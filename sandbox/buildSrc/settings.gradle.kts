listOf("VERSION_CATALOGS").forEach(::enableFeaturePreview)

pluginManagement {
    repositories {
        mavenLocal {
            content { includeGroup("com.javiersc.gradle-plugins") }
        }
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            content { includeGroup("com.javiersc.gradle-plugins") }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal {
            content { includeGroup("com.javiersc.gradle-plugins") }
        }
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            content { includeGroup("com.javiersc.gradle-plugins") }
        }
    }

    versionCatalogs {
        create("pluginLibs") {
            from(files("../../gradle/pluginLibs.toml"))
            version("javierscGradlePlugins", "0.1.0-rc.2")
        }
    }
}

include(":consumer-kotlin-jvm")
