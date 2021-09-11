enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") { from(files("../gradle/libs.toml")) }
        create("pluginLibs") { from(files("../gradle/pluginLibs.toml")) }
    }
}
