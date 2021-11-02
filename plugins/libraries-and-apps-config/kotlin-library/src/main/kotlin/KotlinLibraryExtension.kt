@file:Suppress("PackageDirectoryMismatch")

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.gradle.plugins.core.isAndroidLibrary
import com.javiersc.gradle.plugins.core.isKotlinMultiplatformWithAndroid
import com.javiersc.gradle.plugins.core.withAndroidLibrary
import com.javiersc.gradle.plugins.core.withKotlinJvm
import com.javiersc.gradle.plugins.core.withKotlinMultiplatform
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

sealed class KotlinLibraryType {

    open fun configure(project: Project) {
        project.tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                sourceCompatibility = JavaVersion.VERSION_11.toString()
                targetCompatibility = JavaVersion.VERSION_11.toString()
            }
        }
    }

    class Android(
        private val compileSdk: Int = AndroidSdk.compileSdk,
        private val minSdk: Int = AndroidSdk.minSdk,
        private val isKmp: Boolean
    ) : KotlinLibraryType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configure<LibraryExtension> {
                compileSdk = this@Android.compileSdk

                defaultConfig { minSdk = this@Android.minSdk }

                compileOptions {
                    sourceCompatibility(JavaVersion.VERSION_11)
                    targetCompatibility(JavaVersion.VERSION_11)
                }

                if (isKmp.not()) {
                    sourceSets.all {
                        assets.setSrcDirs(listOf("$name/assets"))
                        java.setSrcDirs(listOf("$name/java", "$name/kotlin"))
                        manifest.srcFile("$name/AndroidManifest.xml")
                        res.setSrcDirs(listOf("$name/res"))
                        resources.setSrcDirs(listOf("$name/resources"))
                    }
                }
            }
        }
    }
    object KotlinJVM : KotlinLibraryType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configure<JavaPluginExtension> {
                sourceSets.all {
                    java.setSrcDirs(listOf("$name/java"))
                    resources.setSrcDirs(listOf("$name/resources"))
                }
            }

            project.configure<KotlinJvmProjectExtension> {
                sourceSets.all {
                    kotlin.setSrcDirs(listOf("$name/kotlin"))
                    resources.setSrcDirs(listOf("$name/resources"))
                }
            }
        }
    }

    object KotlinMultiplatform : KotlinLibraryType() {

        override fun configure(project: Project) {
            super.configure(project)

            project.configure<KotlinMultiplatformExtension> {
                sourceSets.all {
                    addDefaultLanguageSettings()
                    kotlin.setSrcDirs(listOf("$name/kotlin"))
                    resources.setSrcDirs(listOf("$name/resources"))
                }
            }
        }
    }

    object KotlinMultiplatformWithAndroid : KotlinLibraryType() {

        override fun configure(project: Project) {
            KotlinMultiplatform.configure(project)
            Android(isKmp = true).configure(project)

            project.configure<LibraryExtension> {
                sourceSets.all {
                    manifest.srcFile("android${name.capitalize()}/AndroidManifest.xml")
                }
            }
        }
    }

    companion object {
        fun build(project: Project): Unit =
            with(project) {
                withKotlinMultiplatform {
                    if (isKotlinMultiplatformWithAndroid) {
                        KotlinMultiplatformWithAndroid.configure(project)
                    } else {
                        KotlinMultiplatform.configure(project)
                    }
                }

                withKotlinJvm { KotlinJVM.configure(project) }

                withAndroidLibrary {
                    if (isAndroidLibrary) Android(isKmp = false).configure(project)
                }
            }
    }
}

object AndroidSdk {

    const val compileSdk = 31
    const val minSdk = 21
}
