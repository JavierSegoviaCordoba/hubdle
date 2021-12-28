plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "version catalogs",
            "version",
            "catalogs"
        )
}

gradlePlugin {
    plugins {
        create("ProjectsVersionCatalog") {
            id = "com.javiersc.gradle.plugins.projects.version.catalog"
            displayName = "Projects Version Catalog"
            description = "Autogenerate a Version Catalog with all projects"
            implementationClass = "com.javiersc.gradle.plugins.projects.version.catalog.ProjectsVersionCatalogPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    implementation(libs.tomlj)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
    testImplementation(gradleTestKit())
}
