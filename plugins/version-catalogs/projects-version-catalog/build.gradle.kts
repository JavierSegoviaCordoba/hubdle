plugins {
    `kotlin-jvm`
    `java-gradle-plugin`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
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
            implementationClass = "com.javiersc.gradle.plugins.projects.version.catalog.ProjectsVersionCatalogPlugin"
            displayName = "Projects Version Catalog"
            description = "Autogenerate a Version Catalog with all projects"
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
    testImplementation(libs.jetbrains.kotlin.kotlinTestJunit)
    testImplementation(libs.kotest.kotestAssertionsCore)
    testImplementation(gradleTestKit())
}
