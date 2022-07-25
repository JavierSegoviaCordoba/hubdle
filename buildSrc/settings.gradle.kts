rootProject.name = "buildSrc"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") { from(files("../gradle/libs.versions.toml")) }
        create("pluginLibs") { from(files("../gradle/plugin.libs.versions.toml")) }
    }
}
