package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm32HfpExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMips32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMipsel32Extension
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
public open class HubdleKotlinMultiplatformLinuxExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val targetName: String = "linux"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val linuxArm32Hfp: KotlinMultiplatformLinuxArm32HfpExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxArm32Hfp(
        action: Action<KotlinMultiplatformLinuxArm32HfpExtension> = Action {}
    ) {
        linuxArm32Hfp.enableAndExecute(action)
    }

    public val linuxArm64: KotlinMultiplatformLinuxArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxArm64(action: Action<KotlinMultiplatformLinuxArm64Extension> = Action {}) {
        linuxArm64.enableAndExecute(action)
    }

    public val linuxMips32: KotlinMultiplatformLinuxMips32Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxMips32(action: Action<KotlinMultiplatformLinuxMips32Extension> = Action {}) {
        linuxMips32.enableAndExecute(action)
    }

    public val linuxMipsel32: KotlinMultiplatformLinuxMipsel32Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxMipsel32(
        action: Action<KotlinMultiplatformLinuxMipsel32Extension> = Action {}
    ) {
        linuxMipsel32.enableAndExecute(action)
    }

    public val linuxX64: KotlinMultiplatformLinuxX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun linuxX64(action: Action<KotlinMultiplatformLinuxX64Extension> = Action {}) {
        linuxX64.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                linuxArm32Hfp()
                linuxArm64()
                linuxMips32()
                linuxMipsel32()
                linuxX64()
            }
        }
        configurable(priority = Priority.P6) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val linuxArm64Main: KotlinSourceSet? = sourceSets.findByName("linuxArm64Main")
                val linuxArm32HfpMain: KotlinSourceSet? = sourceSets.findByName("linuxArm32HfpMain")
                val linuxMips32Main: KotlinSourceSet? = sourceSets.findByName("linuxMips32Main")
                val linuxMipsel32Main: KotlinSourceSet? = sourceSets.findByName("linuxMipsel32Main")
                val linuxX64Main: KotlinSourceSet? = sourceSets.findByName("linuxX64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val linuxArm64Test: KotlinSourceSet? = sourceSets.findByName("linuxArm64Test")
                val linuxArm32HfpTest: KotlinSourceSet? = sourceSets.findByName("linuxArm32HfpTest")
                val linuxMips32Test: KotlinSourceSet? = sourceSets.findByName("linuxMips32Test")
                val linuxMipsel32Test: KotlinSourceSet? = sourceSets.findByName("linuxMipsel32Test")
                val linuxX64Test: KotlinSourceSet? = sourceSets.findByName("linuxX64Test")

                val linuxMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        linuxArm64Main,
                        linuxArm32HfpMain,
                        linuxMips32Main,
                        linuxMipsel32Main,
                        linuxX64Main,
                    )

                val linuxTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        linuxArm64Test,
                        linuxArm32HfpTest,
                        linuxMips32Test,
                        linuxMipsel32Test,
                        linuxX64Test,
                    )

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
