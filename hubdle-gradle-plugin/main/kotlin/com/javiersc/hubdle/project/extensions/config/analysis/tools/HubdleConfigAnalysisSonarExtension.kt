package com.javiersc.hubdle.project.extensions.config.analysis.tools

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryBuildType
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.LibraryProductFlavor
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.kotlinSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions._internal.kotlinTestsSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.analysis.hubdleAnalysis
import com.javiersc.kotlin.stdlib.capitalize
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.sonarqube.gradle.SonarExtension
import org.sonarqube.gradle.SonarProperties

public open class HubdleConfigAnalysisSonarExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAnalysis)

    public val hostUrl: Property<String> = property {
        getStringProperty(Sonar.hostUrl).orElse("https://sonarcloud.io").get()
    }
    public val login: Property<String> = property {
        getStringProperty(Sonar.login).orElse("").get()
    }
    public val organization: Property<String> = property {
        getStringProperty(Sonar.organization).orElse("").get()
    }
    public val projectKey: Property<String> = property {
        getStringProperty(Sonar.projectKey).orElse("").get()
    }
    public val projectName: Property<String> = property {
        getStringProperty(Sonar.projectName).orElse("").get()
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = ApplicablePlugin.Scope.CurrentProject,
            pluginId = PluginId.Sonarqube
        )

        configurable(priority = Priority.P4) { configureSonarqube(project) }
        // TODO: Remove both when project isolation is fixed in Sonar Gradle plugin as hubdle
        //       analysis.sonar.isFullEnabled.get() will be false, and the Sonar plugin shouldn't
        //       pick this project
        afterEvaluate { proj ->
            proj.extensions.findByType<SonarExtension>()?.isSkipProject = !isFullEnabled.get()
        }
    }

    private fun configureSonarqube(project: Project) {
        project.configure<SonarExtension> {
            isSkipProject = !isFullEnabled.get()
            properties { properties ->
                properties.property("sonar.projectName", projectName.get())
                properties.property("sonar.projectKey", projectKey.get())
                properties.property("sonar.login", login.get())
                properties.property("sonar.host.url", hostUrl.get())
                properties.property("sonar.organization", organization.get())
                val detektReportPath = "${project.buildDir}/reports/detekt/detekt.xml"
                properties.property("sonar.kotlin.detekt.reportPaths", detektReportPath)
                val jacocoXmlReportPaths = "${project.buildDir}/reports/kover/xml/report.xml"
                properties.property("sonar.coverage.jacoco.xmlReportPaths", jacocoXmlReportPaths)

                project.configureAndroidLintReportPaths(properties)

                // TODO: https://github.com/detekt/detekt/issues/5412
                //  https://github.com/detekt/detekt/issues/5896
                properties.property("sonar.sources", project.kotlinDirs)
                properties.property("sonar.tests", project.kotlinTestDirs)
            }
        }
    }

    private val Project.kotlinDirs: List<String>
        get() =
            kotlinSrcDirsWithoutBuild.orNull
                .orEmpty()
                .asSequence()
                .filter(File::exists)
                .map(File::getPath)
                .toList()

    private val Project.kotlinTestDirs: List<String>
        get() =
            kotlinTestsSrcDirsWithoutBuild.orNull
                .orEmpty()
                .asSequence()
                .filter(File::exists)
                .map(File::getPath)
                .toList()

    private fun Project.configureAndroidLintReportPaths(properties: SonarProperties) {
        val reportsDir: File = buildDir.resolve("reports")
        val sonarAndroidLintReportPaths = "sonar.androidLint.reportPaths"
        fun variants(buildTypes: List<String>, flavors: List<String>): Set<String> = buildSet {
            for (flavor in flavors) {
                addAll(buildTypes.map { buildType -> "${flavor}${buildType.capitalize()}" })
            }
        }
        val defaultLintFile = "lint-results.xml"
        fun lintFile(name: String): String = "lint-results-$name.xml"
        fun reportPaths(buildTypes: List<String>, variants: Set<String>): List<File> =
            buildSet {
                    add(defaultLintFile)
                    addAll(buildTypes.map(::lintFile))
                    addAll(variants.map(::lintFile))
                }
                .map { reportFile -> reportsDir.resolve(reportFile) }

        pluginManager.withPlugin(PluginId.AndroidApplication.id) {
            val android: ApplicationExtension = the()
            val buildTypes: List<String> = android.buildTypes.map(ApplicationBuildType::getName)
            val flavors: List<String> =
                android.productFlavors.map(ApplicationProductFlavor::getName)
            properties.property(
                sonarAndroidLintReportPaths,
                reportPaths(buildTypes, variants(buildTypes, flavors)),
            )
        }

        pluginManager.withPlugin(PluginId.AndroidLibrary.id) {
            val android: LibraryExtension = the()
            val buildTypes: List<String> = android.buildTypes.map(LibraryBuildType::getName)
            val flavors: List<String> = android.productFlavors.map(LibraryProductFlavor::getName)
            properties.property(
                sonarAndroidLintReportPaths,
                reportPaths(buildTypes, variants(buildTypes, flavors)),
            )
        }
    }

    public object Sonar {
        public const val hostUrl: String = "analysis.sonar.host.url"
        public const val login: String = "analysis.sonar.login"
        public const val organization: String = "analysis.sonar.organization"
        public const val projectKey: String = "analysis.sonar.projectKey"
        public const val projectName: String = "analysis.sonar.projectName"
    }
}

internal val HubdleEnableableExtension.hubdleSonar: HubdleConfigAnalysisSonarExtension
    get() = getHubdleExtension()
