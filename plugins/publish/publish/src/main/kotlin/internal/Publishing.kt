package internal

import com.github.triplet.gradle.play.PlayPublisherExtension
import com.gradle.publish.PluginBundleExtension
import com.javiersc.gradle.plugins.core.isAndroidApplication
import com.javiersc.gradle.plugins.core.isAndroidLibrary
import com.javiersc.gradle.plugins.core.isGradlePlugin
import com.javiersc.gradle.plugins.core.isJavaPlatform
import com.javiersc.gradle.plugins.core.isKotlinJvm
import com.javiersc.gradle.plugins.core.isKotlinMultiplatform
import com.javiersc.gradle.plugins.core.isVersionCatalog
import com.javiersc.gradle.plugins.publish.internal.configureMavenPublication
import com.javiersc.gradle.plugins.publish.internal.docsJar
import com.javiersc.gradle.plugins.publish.internal.sourcesJar
import com.javiersc.gradle.plugins.publish.internal.warningMessage
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configurePublishing() {
    val projectSupportedPublications = allSupportedPublications.filter(Publishing::shouldConfigure)

    if (projectSupportedPublications.count() > 1) {
        warningMessage(
            """
                |There are multiple publishing options:
                |${projectSupportedPublications.map(Publishing::name)}
            """.trimMargin()
        )
    }
    projectSupportedPublications.forEach(Publishing::configure)
}

internal val Project.allSupportedPublications: List<Publishing>
    get() =
        listOf(
            JavaPlatformPublishing(this),
            GradlePluginPublishing(this),
            VersionCatalogPublishing(this),
            KotlinMultiplatformPublishing(this),
            AndroidApplicationPublishing(this),
            AndroidLibraryPublishing(this),
            KotlinJvmPublishing(this),
        )

internal interface Publishing {
    val name: String

    val shouldConfigure: Boolean

    val project: Project

    fun configure()
}

internal class AndroidApplicationPublishing(override val project: Project) : Publishing {
    override val name = "Android application"

    override val shouldConfigure = project.isAndroidApplication

    override fun configure() =
        with(project) {
            pluginManager.apply("com.github.triplet.play")

            afterEvaluate {
                configure<PlayPublisherExtension> { releaseName.set("Publish $version") }
            }
        }
}

internal class AndroidLibraryPublishing(override val project: Project) : Publishing {
    override val name = "Android library"

    override val shouldConfigure = project.isAndroidLibrary

    override fun configure() =
        with(project) {
            afterEvaluate {
                configureMavenPublication(
                    artifacts = listOf(docsJar, sourcesJar),
                    components = mapOf("release" to "release"),
                )
            }
        }
}

internal class GradlePluginPublishing(override val project: Project) : Publishing {
    override val name = "Gradle plugin"

    override val shouldConfigure = project.isGradlePlugin

    override fun configure() =
        with(project) {
            pluginManager.apply("com.gradle.plugin-publish")

            configure<PluginBundleExtension> {
                website = property("pom.url").toString()
                vcsUrl = property("pom.smc.url").toString()
            }

            afterEvaluate {
                configureMavenPublication(
                    artifacts = listOf(docsJar, sourcesJar),
                )
            }
        }
}

internal class JavaPlatformPublishing(override val project: Project) : Publishing {
    override val name = "Java Platform"

    override val shouldConfigure = project.isJavaPlatform

    override fun configure() =
        with(project) {
            afterEvaluate {
                configureMavenPublication(
                    artifacts = emptyList(),
                    components = mapOf("myPlatform" to "javaPlatform"),
                )
            }
        }
}

internal class KotlinJvmPublishing(override val project: Project) : Publishing {
    override val name = "Kotlin JVM"

    override val shouldConfigure = project.isKotlinJvm

    override fun configure() =
        with(project) {
            afterEvaluate {
                configureMavenPublication(
                    artifacts = listOf(docsJar, sourcesJar),
                    components = mapOf("maven" to "java"),
                )
            }
        }
}

internal class KotlinMultiplatformPublishing(override val project: Project) : Publishing {
    override val name = "Kotlin Multiplatform"

    override val shouldConfigure = project.isKotlinMultiplatform

    override fun configure() =
        with(project) {
            afterEvaluate {
                configureMavenPublication(
                    artifacts = listOf(docsJar),
                )
            }
        }
}

internal class VersionCatalogPublishing(override val project: Project) : Publishing {
    override val name = "Version Catalog"

    override val shouldConfigure = project.isVersionCatalog

    override fun configure() =
        with(project) {
            afterEvaluate {
                configureMavenPublication(
                    artifacts = listOf(docsJar),
                    components = mapOf("maven" to "versionCatalog"),
                )
            }
        }
}
