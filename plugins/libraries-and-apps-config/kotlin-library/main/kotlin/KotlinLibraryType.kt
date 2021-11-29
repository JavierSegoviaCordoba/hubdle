package com.javiersc.gradle.plugins.kotlin.library

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.gradle.plugins.core.isAndroidLibrary
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

internal sealed class KotlinLibraryType {

    open fun configure(project: Project) {
        project.tasks.withType(KotlinCompile::class.java) { kotlinCompile ->
            kotlinCompile.kotlinOptions { jvmTarget = JavaVersion.VERSION_1_8.toString() }
            kotlinCompile.sourceCompatibility = JavaVersion.VERSION_1_8.toString()
            kotlinCompile.targetCompatibility = JavaVersion.VERSION_1_8.toString()
        }
    }

    class Android(
        private val compileSdk: Int = AndroidSdk.compileSdk,
        private val minSdk: Int = AndroidSdk.minSdk,
        private val isKmp: Boolean
    ) : KotlinLibraryType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.extensions.findByType(LibraryExtension::class.java)?.apply {
                compileSdk = this@Android.compileSdk

                defaultConfig { minSdk = this@Android.minSdk }

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
    }
    object KotlinJVM : KotlinLibraryType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.extensions.findByType(JavaPluginExtension::class.java)?.apply {
                sourceSets.all {
                    it.java.setSrcDirs(listOf("${it.name}/java"))
                    it.resources.setSrcDirs(listOf("${it.name}/resources"))
                }
            }
            project.extensions.findByType(KotlinJvmProjectExtension::class.java)?.apply {
                sourceSets.all {
                    it.kotlin.setSrcDirs(listOf("${it.name}/kotlin"))
                    it.resources.setSrcDirs(listOf("${it.name}/resources"))
                }
            }
        }
    }

    object KotlinMultiplatform : KotlinLibraryType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.extensions.findByType(KotlinMultiplatformExtension::class.java)?.apply {
                sourceSets.all {
                    it.addDefaultLanguageSettings()
                    it.kotlin.setSrcDirs(listOf("${it.name}/kotlin"))
                    it.resources.setSrcDirs(listOf("${it.name}/resources"))
                }
            }
        }
    }

    object KotlinMultiplatformWithAndroid : KotlinLibraryType() {

        override fun configure(project: Project) {
            KotlinMultiplatform.configure(project)
            Android(isKmp = true).configure(project)

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
                    isKotlinMultiplatformWithAndroid -> {
                        KotlinMultiplatformWithAndroid.configure(this)
                    }
                    isKotlinMultiplatform -> {
                        KotlinMultiplatform.configure(this)
                    }
                    isKotlinJvm -> KotlinJVM.configure(this)
                    isAndroidLibrary -> Android(isKmp = false).configure(this)
                    else -> {
                        errorMessage(
                            "`javiersc-kotlin-library` doesn't support this type of project yet"
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
