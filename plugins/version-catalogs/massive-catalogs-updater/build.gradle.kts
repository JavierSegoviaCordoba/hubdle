plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-library`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "massive catalogs",
            "versions catalogs",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.massive.catalogs.updater") {
            id = "com.javiersc.gradle.plugins.massive.catalogs.updater"
            displayName = "Massive Catalogs Updater"
            description = "A plugin for updating Massive Catalogs"
            implementationClass =
                "com.javiersc.gradle.plugins.massive.catalogs.updater.MassiveCatalogsUpdaterPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(libs.jsoup.jsoup)
    implementation(libs.javiersc.semver.semverCore)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
