plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "wrapper",
            "updater",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.gradle.wrapper.updater") {
            id = "com.javiersc.gradle.plugins.gradle.wrapper.updater"
            displayName = "Gradle Wrapper Updater"
            description = "A plugin for updating the Gradle Wrapper"
            implementationClass =
                "com.javiersc.gradle.plugins.gradle.wrapper.updater.GradleWrapperUpdaterPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(libs.google.codeGson.gson)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
