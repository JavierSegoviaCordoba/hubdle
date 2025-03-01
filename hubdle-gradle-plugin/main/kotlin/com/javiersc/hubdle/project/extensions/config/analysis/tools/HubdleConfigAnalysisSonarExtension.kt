package com.javiersc.hubdle.project.extensions.config.analysis.tools

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryBuildType
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.LibraryProductFlavor
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.kotlinSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions._internal.kotlinTestsSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.analysis.hubdleAnalysis
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.kotlin.stdlib.capitalize
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.sonarqube.gradle.SonarExtension
import org.sonarqube.gradle.SonarProperties

public open class HubdleConfigAnalysisSonarExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAnalysis)

    public val hostUrl: Property<String> = property {
        getStringProperty(Sonar.hostUrl).orElse("https://sonarcloud.io").get()
    }
    public val token: Property<String> = property {
        getStringProperty(Sonar.token).orElse("").get()
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
            scope = ApplicablePlugin.Scope.CurrentProject,
            pluginId = PluginId.Sonarqube,
        )

        afterConfigurable { configureSonarqube(project) }
        // TODO: Remove both when project isolation is fixed in Sonar Gradle plugin as hubdle
        //       analysis.sonar.isFullEnabled.get() will be false, and the Sonar plugin shouldn't
        //       pick this project
        afterEvaluate { proj ->
            proj.extensions.findByType<SonarExtension>()?.isSkipProject = !isFullEnabled.get()
        }
    }

    // TODO: https://github.com/detekt/detekt/issues/5412
    //  https://github.com/detekt/detekt/issues/5896
    private fun configureSonarqube(project: Project) {
        project.configure<SonarExtension> {
            isSkipProject = !isFullEnabled.get()
            val buildDir = project.layout.buildDirectory.asFile.get()
            properties { properties ->
                fun prop(key: String, value: Any) = properties.property("sonar.$key", value)

                project.configureAndroidLintReportPaths(properties)

                prop("coverage.exclusions", KotlinGlob.allTests)
                prop("coverage.jacoco.xmlReportPaths", "$buildDir/reports/kover/xml/report.xml")
                prop("exclusions", "$buildDir/**/*")
                prop("host.url", hostUrl.get())
                prop("kotlin.detekt.reportPaths", "$buildDir/reports/detekt/detekt.xml")
                prop("organization", organization.get())
                prop("projectName", projectName.get())
                prop("projectKey", projectKey.get())
                prop("sources", project.kotlinDirs)
                prop("tests", project.kotlinTestDirs)
                prop("token", token.get())
            }
        }
    }

    private val Project.kotlinDirs: Set<String>
        get() =
            kotlinSrcDirsWithoutBuild.orNull
                .orEmpty()
                .asSequence()
                .filter(File::exists)
                .map(File::getPath)
                .minus(kotlinTestDirs)
                .toSet()

    private val Project.kotlinTestDirs: Set<String>
        get() =
            kotlinTestsSrcDirsWithoutBuild.orNull
                .orEmpty()
                .asSequence()
                .filter(File::exists)
                .map(File::getPath)
                .toSet()

    private fun Project.configureAndroidLintReportPaths(properties: SonarProperties) {
        val reportsDir: File = layout.buildDirectory.asFile.get().resolve("reports")
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

        withPlugin(PluginId.AndroidApplication) {
            val android: ApplicationExtension = the()
            val buildTypes: List<String> = android.buildTypes.map(ApplicationBuildType::getName)
            val flavors: List<String> =
                android.productFlavors.map(ApplicationProductFlavor::getName)
            properties.property(
                sonarAndroidLintReportPaths,
                reportPaths(buildTypes, variants(buildTypes, flavors)),
            )
        }

        withPlugin(PluginId.AndroidLibrary) {
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
        public const val token: String = "analysis.sonar.token"
        public const val organization: String = "analysis.sonar.organization"
        public const val projectKey: String = "analysis.sonar.projectKey"
        public const val projectName: String = "analysis.sonar.projectName"
    }

    internal enum class KotlinGlob(val pattern: List<String>) {
        Main(
            listOf("main/kotlin/**", "**/main/kotlin/**", "*Main/kotlin/**", "**/*Main/kotlin/**")
        ),
        Test(
            listOf("test/kotlin/**", "**/test/kotlin/**", "*Test/kotlin/**", "**/*Test/kotlin/**")
        ),
        TestFixtures(
            listOf(
                "testFixtures/kotlin/**",
                "**/testFixtures/kotlin/**",
                "**/*TestFixtures/kotlin/**",
            )
        ),
        TestFunctional(
            listOf(
                "testFunctional/kotlin/**",
                "**/testFunctional/kotlin/**",
                "functionalTest/kotlin/**",
                "**/functionalTest/kotlin/**",
            )
        ),
        TestIntegration(
            listOf(
                "testIntegration/kotlin/**",
                "**/testIntegration/kotlin/**",
                "integrationTest/kotlin/**",
                "**/integrationTest/kotlin/**",
            )
        );

        companion object {
            val allSource: List<String> = Main.pattern + TestFixtures.pattern
            val allTests: List<String> =
                Test.pattern + TestFunctional.pattern + TestIntegration.pattern
        }
    }
}

internal val HubdleEnableableExtension.hubdleSonar: HubdleConfigAnalysisSonarExtension
    get() = getHubdleExtension()
