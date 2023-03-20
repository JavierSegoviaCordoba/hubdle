package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSArm32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSX64Extension
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
public open class HubdleKotlinMultiplatformIOSExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    public override val targetName: String = "ios"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    public val iosArm32: HubdleKotlinMultiplatformIOSArm32Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun iosArm32(action: Action<HubdleKotlinMultiplatformIOSArm32Extension> = Action {}) {
        iosArm32.enableAndExecute(action)
    }

    public val iosArm64: HubdleKotlinMultiplatformIOSArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun iosArm64(action: Action<HubdleKotlinMultiplatformIOSArm64Extension> = Action {}) {
        iosArm64.enableAndExecute(action)
    }

    public val iosSimulatorArm64: HubdleKotlinMultiplatformIOSSimulatorArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun iosSimulatorArm64(
        action: Action<HubdleKotlinMultiplatformIOSSimulatorArm64Extension> = Action {}
    ) {
        iosSimulatorArm64.enableAndExecute(action)
    }

    public val iosX64: HubdleKotlinMultiplatformIOSX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun iosX64(action: Action<HubdleKotlinMultiplatformIOSX64Extension> = Action {}) {
        iosX64.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                iosArm32()
                iosArm64()
                iosSimulatorArm64()
                iosX64()
            }
        }
        configurable(priority = Priority.P6) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val iosArm32Main: KotlinSourceSet? = sourceSets.findByName("iosArm32Main")
                val iosArm64Main: KotlinSourceSet? = sourceSets.findByName("iosArm64Main")
                val iosX64Main: KotlinSourceSet? = sourceSets.findByName("iosX64Main")
                val iosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val iosArm32Test: KotlinSourceSet? = sourceSets.findByName("iosArm32Test")
                val iosArm64Test: KotlinSourceSet? = sourceSets.findByName("iosArm64Test")
                val iosX64Test: KotlinSourceSet? = sourceSets.findByName("iosX64Test")
                val iosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Test")

                val iosMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        iosArm32Main,
                        iosArm64Main,
                        iosX64Main,
                        iosSimulatorArm64Main,
                    )

                val iosTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        iosArm32Test,
                        iosArm64Test,
                        iosX64Test,
                        iosSimulatorArm64Test,
                    )

                val iosMain = sourceSets.maybeCreate("iosMain")
                val iosTest = sourceSets.maybeCreate("iosTest")

                iosMain.dependsOn(commonMain)
                for (iosMainSourceSet in iosMainSourceSets) {
                    iosMainSourceSet.dependsOn(iosMain)
                }
                iosTest.dependsOn(commonTest)
                for (iosTestSourceSet in iosTestSourceSets) {
                    iosTestSourceSet.dependsOn(iosTest)
                }
            }
        }
    }
}
