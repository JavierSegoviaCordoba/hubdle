plugins {
    `javiersc-versioning`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

val allPluginsProject = project

dependencies {
    api(projects.shared.core)
    api(projects.shared.pluginAccessors)
}

kotlin.sourceSets.main.configure {
    kotlin.srcDirs(buildDir.resolve("generated/main/kotlin"))
}

rootProject.gradle.projectsEvaluated {
    rootProject.allprojects {
        if ((allPluginsProject != this@allprojects) && this@allprojects.hasGradlePlugin) {
            allPluginsProject.dependencies { api(project(this@allprojects.path)) }
        }
    }
}

val Project.hasGradlePlugin: Boolean
    get() = extensions.findByType<GradlePluginDevelopmentExtension>() != null
