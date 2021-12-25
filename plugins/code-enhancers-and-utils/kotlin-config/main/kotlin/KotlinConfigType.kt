package com.javiersc.gradle.plugins.kotlin.config

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.gradle.plugins.core.isAndroidApplication
import com.javiersc.gradle.plugins.core.isAndroidLibrary
import com.javiersc.gradle.plugins.core.isGradlePlugin
import com.javiersc.gradle.plugins.core.isKotlinJvm
import com.javiersc.gradle.plugins.core.isKotlinMultiplatform
import com.javiersc.gradle.plugins.core.isKotlinMultiplatformWithAndroid
import com.javiersc.kotlin.stdlib.AnsiColor
import com.javiersc.kotlin.stdlib.ansiColor
import java.util.Locale
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal sealed class KotlinConfigType {

    open fun configure(project: Project) {
        project.tasks.withType(KotlinCompile::class.java) { kotlinCompile ->
            kotlinCompile.kotlinOptions { jvmTarget = JavaVersion.VERSION_1_8.toString() }
            kotlinCompile.sourceCompatibility = JavaVersion.VERSION_1_8.toString()
            kotlinCompile.targetCompatibility = JavaVersion.VERSION_1_8.toString()
        }
    }

    object AndroidApplication : KotlinConfigType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configureAndroid(isKmp = false)
        }
    }

    class AndroidLibrary(private val isKmp: Boolean) : KotlinConfigType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configureAndroid(isKmp)
        }
    }

    object GradlePlugin : KotlinConfigType() {
        override fun configure(project: Project) {
            super.configure(project)

            check(JavaVersion.current() >= JavaVersion.VERSION_11) {
                """   
                    |Using `javiersc-kotlin-config` to configure Gradle plugins needs Java 11"
                    |  - Use Java 11 to build via adding to your path or whatever other solution.
                    |  - Projects can be still compatible with Java 8 (`KotlinCompile.jvmTarget`)
                    |
                """.trimMargin()
            }

            project.configureJavaAndKotlinSourceSets()

            project.tasks.withType(KotlinCompile::class.java) { kotlinCompile ->
                kotlinCompile.kotlinOptions { jvmTarget = JavaVersion.VERSION_11.toString() }
                kotlinCompile.sourceCompatibility = JavaVersion.VERSION_11.toString()
                kotlinCompile.targetCompatibility = JavaVersion.VERSION_11.toString()
            }
        }
    }

    object KotlinJVM : KotlinConfigType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configureJavaAndKotlinSourceSets()
        }
    }

    object KotlinMultiplatform : KotlinConfigType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configureJavaAndKotlinSourceSets()
        }
    }

    object KotlinMultiplatformWithAndroid : KotlinConfigType() {

        override fun configure(project: Project) {
            KotlinMultiplatform.configure(project)
            AndroidLibrary(isKmp = true).configure(project)

            project.extensions.findByType(LibraryExtension::class.java)?.apply {
                sourceSets.all {
                    it.manifest.srcFile("android${it.name.capitalize()}/AndroidManifest.xml")
                }
            }
        }
    }

    companion object {

        fun build(project: Project): Unit =
            with(project) {
                when {
                    isAndroidApplication -> {
                        AndroidApplication.configure(this)
                    }
                    isGradlePlugin -> {
                        GradlePlugin.configure(this)
                    }
                    isKotlinMultiplatformWithAndroid -> {
                        KotlinMultiplatformWithAndroid.configure(this)
                    }
                    isKotlinMultiplatform -> {
                        KotlinMultiplatform.configure(this)
                    }
                    isKotlinJvm -> {
                        KotlinJVM.configure(this)
                    }
                    isAndroidLibrary -> {
                        AndroidLibrary(isKmp = false).configure(this)
                    }
                    else -> {
                        errorMessage(
                            "`javiersc-kotlin-config` doesn't support this type of project yet"
                        )
                    }
                }
            }
    }
}

object AndroidSdk {

    const val compileSdk = 31
    const val minSdk = 21
}

internal fun Project.errorMessage(message: String) =
    logger.lifecycle(message.ansiColor(AnsiColor.Foreground.Yellow))

private fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

private fun Project.configureJavaAndKotlinSourceSets() {
    extensions.findByType(JavaPluginExtension::class.java)?.apply {
        sourceSets.all {
            it.java.setSrcDirs(listOf("${it.name}/java"))
            it.resources.setSrcDirs(listOf("${it.name}/resources"))
        }
    }
    extensions.findByType(KotlinJvmProjectExtension::class.java)?.apply {
        sourceSets.all {
            it.kotlin.setSrcDirs(listOf("${it.name}/kotlin"))
            it.resources.setSrcDirs(listOf("${it.name}/resources"))
        }
    }

    extensions.findByType(KotlinMultiplatformExtension::class.java)?.apply {
        sourceSets.all {
            it.addDefaultLanguageSettings()
            it.kotlin.setSrcDirs(listOf("${it.name}/kotlin"))
            it.resources.setSrcDirs(listOf("${it.name}/resources"))
        }
    }
}

private fun Project.configureAndroid(isKmp: Boolean) {
    project.extensions.findByType(CommonExtension::class.java)?.apply {
        compileSdk = AndroidSdk.compileSdk

        defaultConfig { minSdk = AndroidSdk.minSdk }

        compileOptions {
            sourceCompatibility(JavaVersion.VERSION_1_8)
            targetCompatibility(JavaVersion.VERSION_1_8)
        }

        if (isKmp.not()) {
            sourceSets.all {
                it.assets.setSrcDirs(listOf("${it.name}/assets"))
                it.java.setSrcDirs(listOf("${it.name}/java", "${it.name}/kotlin"))
                it.manifest.srcFile("${it.name}/AndroidManifest.xml")
                it.res.setSrcDirs(listOf("${it.name}/res"))
                it.resources.setSrcDirs(listOf("${it.name}/resources"))
            }
        }
    }
}
