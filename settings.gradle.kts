import java.util.Properties

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    `gradle-enterprise`
}

rootProject.name = providers.gradleProperty("project.name").forUseAtConfigurationTime().get()

listOf("TYPESAFE_PROJECT_ACCESSORS", "VERSION_CATALOGS").forEach(::enableFeaturePreview)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") { from(files("gradle/libs.toml")) }
        create("pluginLibs") { from(files("gradle/pluginLibs.toml")) }
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

include(":all-plugins")

include(
    ":plugins:code-enhancers-and-utils:all-projects",
    ":plugins:code-enhancers-and-utils:code-analysis",
//    ":plugins:code-enhancers-and-utils:code-coverage",
    ":plugins:code-enhancers-and-utils:code-formatter",
    ":plugins:code-enhancers-and-utils:dependency-updates",
    ":plugins:code-enhancers-and-utils:kotlin-config",
    ":plugins:code-enhancers-and-utils:versioning",
)

include(
    ":plugins:documentation:changelog",
    ":plugins:documentation:docs",
    ":plugins:documentation:readme-badges",
)

include(
    ":plugins:gradle-utils:gradle-wrapper-updater",
)

include(
    ":plugins:publish:nexus",
    ":plugins:publish:publish",
)

include(
    ":plugins:version-catalogs:massive-catalogs-updater",
    ":plugins:version-catalogs:projects-version-catalog",
)

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

include(
    ":shared:core",
    ":shared:core-test",
    ":shared:plugin-accessors",
)
