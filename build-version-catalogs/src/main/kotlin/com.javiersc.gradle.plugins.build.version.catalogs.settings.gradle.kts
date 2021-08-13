enableFeaturePreview("VERSION_CATALOGS")

val buildDir = file("/build/catalogs/")

val projects =
    rootDir.walkTopDown().filter { file ->
        file.name == "build.gradle.kts" &&
            file.readLines().any { line -> line.contains("// catalog start") }
    }

projects.forEach { file -> file.generateVersionCatalog(buildDir) }

dependencyResolutionManagement {
    val catalogs =
        buildDir.walkTopDown().filter { it.extension == "toml" }.map { file ->
            file.nameWithoutExtension to file.path
        }

    versionCatalogs {
        catalogs.forEach { (name, filePath) -> create(name) { from(files(filePath)) } }
    }
}
