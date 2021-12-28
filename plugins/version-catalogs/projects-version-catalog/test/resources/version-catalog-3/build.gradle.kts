plugins {
    `version-catalog`
    id("com.javiersc.gradle.plugins.projects.version.catalog")
}

allprojects {
    group = "com.example.foo-bar"
    version = "1.2.3"
}

catalog {
    versionCatalog {
        from(files("${project.projectDir}/projects.versions.toml"))
    }
}

projectsVersionCatalog {
    librariesPrefix.set("jsc")
    removeVersionAliasPrefix.set("com")
}
