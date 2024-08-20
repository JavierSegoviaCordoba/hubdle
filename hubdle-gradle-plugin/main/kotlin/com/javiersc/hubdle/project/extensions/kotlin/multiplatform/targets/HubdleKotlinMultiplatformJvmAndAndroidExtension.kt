package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@HubdleDslMarker
public open class HubdleKotlinMultiplatformJvmAndAndroidExtension
@Inject
constructor(project: Project) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val targetName: String = "jvmAndAndroid"

    override fun Project.defaultConfiguration() = lazyConfigurable {
        configure<KotlinMultiplatformExtension> {
            /* TODO: enable when granular metadata for jvm+android is fixed
            val jvmAndAndroidMain = sourceSets.create("jvmAndAndroidMain")
            val jvmAndAndroidTest = sourceSets.create("jvmAndAndroidTest")

            jvmAndAndroidMain.dependsOn(sourceSets.getByName("commonMain"))
            jvmAndAndroidTest.dependsOn(sourceSets.getByName("commonTest"))

            sourceSets.findByName("jvmMain")?.dependsOn(jvmAndAndroidMain)
            sourceSets.findByName("jvmTest")?.dependsOn(jvmAndAndroidTest)

            sourceSets.findByName("androidMain")?.dependsOn(jvmAndAndroidMain)
            sourceSets.findByName("androidTest")?.dependsOn(jvmAndAndroidTest)
            */

            // TODO: remove when granular metadata for jvm+android is fixed
            val mainKotlin = "jvmAndAndroid/main/kotlin"
            val mainResources = "jvmAndAndroid/main/resources"
            val testKotlin = "jvmAndAndroid/test/kotlin"
            val testResources = "jvmAndAndroid/test/resources"

            sourceSets.findByName("androidMain")?.apply {
                kotlin.srcDirs(mainKotlin)
                resources.srcDirs(mainResources)
            }
            sourceSets.findByName("androidTest")?.apply {
                kotlin.srcDirs(testKotlin)
                resources.srcDirs(testResources)
            }
            sourceSets.findByName("jvmMain")?.apply {
                kotlin.srcDirs(mainKotlin)
                resources.srcDirs(mainResources)
            }
            sourceSets.findByName("jvmTest")?.apply {
                kotlin.srcDirs(testKotlin)
                resources.srcDirs(testResources)
            }
        }
    }
}
