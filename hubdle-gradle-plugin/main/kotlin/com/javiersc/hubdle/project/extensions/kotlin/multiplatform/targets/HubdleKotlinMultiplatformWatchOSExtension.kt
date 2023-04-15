package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSX64Extension
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
public open class HubdleKotlinMultiplatformWatchOSExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val targetName: String = "watchos"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val watchosArm32: HubdleKotlinMultiplatformWatchOSArm32Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun watchosArm32(
        action: Action<HubdleKotlinMultiplatformWatchOSArm32Extension> = Action {}
    ) {
        watchosArm32.enableAndExecute(action)
    }

    public val watchosArm64: HubdleKotlinMultiplatformWatchOSArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun watchosArm64(
        action: Action<HubdleKotlinMultiplatformWatchOSArm64Extension> = Action {}
    ) {
        watchosArm64.enableAndExecute(action)
    }

    public val watchosSimulatorArm64: HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun watchosSimulatorArm64(
        action: Action<HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension> = Action {}
    ) {
        watchosSimulatorArm64.enableAndExecute(action)
    }

    public val watchosX64: HubdleKotlinMultiplatformWatchOSX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun watchosX64(
        action: Action<HubdleKotlinMultiplatformWatchOSX64Extension> = Action {}
    ) {
        watchosX64.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                watchosArm32()
                watchosArm64()
                watchosSimulatorArm64()
                watchosX64()
            }
        }
        configurable(priority = Priority.P6) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val watchosArm32Main: KotlinSourceSet? = sourceSets.findByName("watchosArm32Main")
                val watchosArm64Main: KotlinSourceSet? = sourceSets.findByName("watchosArm64Main")
                val watchosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Main")
                val watchosX64Main: KotlinSourceSet? = sourceSets.findByName("watchosX64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val watchosArm32Test: KotlinSourceSet? = sourceSets.findByName("watchosArm32Test")
                val watchosArm64Test: KotlinSourceSet? = sourceSets.findByName("watchosArm64Test")
                val watchosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Test")
                val watchosX64Test: KotlinSourceSet? = sourceSets.findByName("watchosX64Test")

                val watchosMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        watchosArm32Main,
                        watchosArm64Main,
                        watchosSimulatorArm64Main,
                        watchosX64Main,
                    )
                val watchosTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        watchosArm32Test,
                        watchosArm64Test,
                        watchosSimulatorArm64Test,
                        watchosX64Test,
                    )

                val watchosMain = sourceSets.maybeCreate("watchosMain")
                val watchosTest = sourceSets.maybeCreate("watchosTest")

                watchosMain.dependsOn(commonMain)
                for (watchosMainSourceSet in watchosMainSourceSets) {
                    watchosMainSourceSet.dependsOn(watchosMain)
                }

                watchosTest.dependsOn(commonTest)
                for (watchosTestSourceSet in watchosTestSourceSets) {
                    watchosTestSourceSet.dependsOn(watchosTest)
                }
            }
        }
    }
}
