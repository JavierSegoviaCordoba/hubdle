plugins {
    `kotlin-jvm`
    `javiersc-kotlin-library`
    `javiersc-publish`
}

val allPluginsProject = project

dependencies {
    api(projects.shared.core)
    api(projects.shared.pluginAccessors)
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
