package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinMultiplatformLinuxExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val targetName: String = "linux"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val linuxArm64: KotlinMultiplatformLinuxArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxArm64(action: Action<KotlinMultiplatformLinuxArm64Extension> = Action {}) {
        linuxArm64.enableAndExecute(action)
    }

    public val linuxX64: KotlinMultiplatformLinuxX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxX64(action: Action<KotlinMultiplatformLinuxX64Extension> = Action {}) {
        linuxX64.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            if (allEnabled.get()) {
                linuxArm64()
                linuxX64()
            }
        }
        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val linuxArm64Main: KotlinSourceSet? = sourceSets.findByName("linuxArm64Main")
                val linuxX64Main: KotlinSourceSet? = sourceSets.findByName("linuxX64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val linuxArm64Test: KotlinSourceSet? = sourceSets.findByName("linuxArm64Test")
                val linuxX64Test: KotlinSourceSet? = sourceSets.findByName("linuxX64Test")

                val linuxMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(linuxArm64Main, linuxX64Main)

                val linuxTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(linuxArm64Test, linuxX64Test)

                val linuxMain = sourceSets.maybeCreate("linuxMain")
                val linuxTest = sourceSets.maybeCreate("linuxTest")

                linuxMain.dependsOn(commonMain)
                for (linuxMainSourceSet in linuxMainSourceSets) {
                    linuxMainSourceSet.dependsOn(linuxMain)
                }

                linuxTest.dependsOn(commonTest)
                for (linuxTestSourceSet in linuxTestSourceSets) {
                    linuxTestSourceSet.dependsOn(linuxTest)
                }
            }
        }
    }
}
