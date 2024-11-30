rootProject.name = "buildSrc"

dependencyResolutionManagement {
    versionCatalogs {
        create("hubdle") { //
            from(files("../gradle/hubdle.libs.versions.toml"))
        }
    }
}
