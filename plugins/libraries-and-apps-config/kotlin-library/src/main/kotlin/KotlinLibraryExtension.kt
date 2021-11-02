@file:Suppress("PackageDirectoryMismatch")

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.gradle.plugins.core.isAndroidLibrary
import com.javiersc.gradle.plugins.core.isKotlinJvm
import com.javiersc.gradle.plugins.core.isKotlinMultiplatform
import com.javiersc.gradle.plugins.core.isKotlinMultiplatformWithAndroid
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
        private val isKmp: Boolean = true
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
                when {
                    isKotlinMultiplatformWithAndroid ->
                        KotlinMultiplatformWithAndroid.configure(this)
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

internal fun Project.errorMessage(message: String) = logger.lifecycle("$YELLOW$message$RESET")

private const val RESET = "\u001B[0m"
private const val YELLOW = "\u001B[0;33m"
