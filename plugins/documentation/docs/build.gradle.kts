plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "docs",
            "dokka",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.docs") {
            id = "com.javiersc.gradle.plugins.docs"
            displayName = "Docs"
            description = "A custom plugin for Dokka Plugin with basic setup"
            implementationClass = "com.javiersc.gradle.plugins.docs.DocsPlugin"
        }
    }
}

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
    implementation(pluginLibs.vyarus.gradleMkdocsPlugin)
    implementation(projects.shared.core)


    testPluginClasspath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    testImplementation(gradleTestKit())
    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks {
    pluginUnderTestMetadata {
        pluginClasspath.from(testPluginClasspath)
    }
}
