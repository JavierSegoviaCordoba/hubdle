import java.util.Properties

plugins {
    `gradle-enterprise`
}

rootProject.name = providers.gradleProperty("project.name").get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("hubdleLibs") { from(files("gradle/hubdle.libs.versions.toml")) }
        create("pluginLibs") { from(files("gradle/pluginLibs.versions.toml")) }
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

include(":hubdle-gradle-plugin")

file("local.properties").apply {
    if (exists().not()) {
        createNewFile()
        writeText("sandbox.enabled=false")
    }
    inputStream().use { fileInputStream ->
        Properties().apply {
            load(fileInputStream)
            if (getProperty("sandbox.enabled")?.toString()?.toBoolean() == true) {
                includeBuild("sandbox")
            }
        }
    }
}
