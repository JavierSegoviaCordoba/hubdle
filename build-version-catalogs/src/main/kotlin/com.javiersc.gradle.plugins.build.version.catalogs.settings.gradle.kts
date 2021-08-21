import com.javiersc.plugins.build.version.catalogs.Catalog

enableFeaturePreview("VERSION_CATALOGS")

val buildDir = file("build/catalogs")

val catalogNameKey = "// Catalog name: "

val projects =
    rootDir.walkTopDown().filter { file ->
        file.name == "build.gradle.kts" &&
            file.readLines().any { line -> line.contains(catalogNameKey) }
    }

projects.forEach { file ->
    val catalogName =
        file.readLines().first { line -> line.contains(catalogNameKey) }.replace(catalogNameKey, "")
    val catalogAsText = Catalog(file.readText()).data

    file("$buildDir/$catalogName.toml").apply {
        parentFile.mkdirs()
        createNewFile()
        writeText(catalogAsText)
    }
}

dependencyResolutionManagement {
    val catalogs =
        buildDir.walkTopDown().filter { it.extension == "toml" }.map { file ->
            file.nameWithoutExtension to file.path
        }

    versionCatalogs {
        catalogs.forEach { (name, filePath) -> create(name) { from(files(filePath)) } }
    }
}
