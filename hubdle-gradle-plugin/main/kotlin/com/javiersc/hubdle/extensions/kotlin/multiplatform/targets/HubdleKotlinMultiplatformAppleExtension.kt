package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
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
public open class HubdleKotlinMultiplatformAppleExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleKotlinMultiplatformTargetOptions {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { true }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val targetName: String = "apple"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    public val ios: HubdleKotlinMultiplatformIOSExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun ios(action: Action<HubdleKotlinMultiplatformIOSExtension> = Action {}) {
        ios.enableAndExecute(action)
    }

    public val macos: HubdleKotlinMultiplatformMacOSExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun macos(action: Action<HubdleKotlinMultiplatformMacOSExtension> = Action {}) {
        macos.enableAndExecute(action)
    }

    public val tvos: HubdleKotlinMultiplatformTvOSExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun tvos(action: Action<HubdleKotlinMultiplatformTvOSExtension> = Action {}) {
        tvos.enableAndExecute(action)
    }

    public val watchos: HubdleKotlinMultiplatformWatchOSExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun watchos(action: Action<HubdleKotlinMultiplatformWatchOSExtension> = Action {}) {
        watchos.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                ios.allEnabled()
                macos.allEnabled()
                tvos.allEnabled()
                watchos.allEnabled()
            }
        }

        configurable(priority = Priority.P4) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val iosArm32Main: KotlinSourceSet? = sourceSets.findByName("iosArm32Main")
                val iosArm64Main: KotlinSourceSet? = sourceSets.findByName("iosArm64Main")
                val iosX64Main: KotlinSourceSet? = sourceSets.findByName("iosX64Main")
                val iosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Main")
                val macosX64Main: KotlinSourceSet? = sourceSets.findByName("macosX64Main")
                val macosArm64Main: KotlinSourceSet? = sourceSets.findByName("macosArm64Main")
                val tvosArm64Main: KotlinSourceSet? = sourceSets.findByName("tvosArm64Main")
                val tvosX64Main: KotlinSourceSet? = sourceSets.findByName("tvosX64Main")
                val tvosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Main")
                val watchosArm32Main: KotlinSourceSet? = sourceSets.findByName("watchosArm32Main")
                val watchosArm64Main: KotlinSourceSet? = sourceSets.findByName("watchosArm64Main")
                val watchosX64Main: KotlinSourceSet? = sourceSets.findByName("watchosX64Main")
                val watchosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Main")
                val watchosX86Main: KotlinSourceSet? = sourceSets.findByName("watchosX86Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val iosArm32Test: KotlinSourceSet? = sourceSets.findByName("iosArm32Test")
                val iosArm64Test: KotlinSourceSet? = sourceSets.findByName("iosArm64Test")
                val iosX64Test: KotlinSourceSet? = sourceSets.findByName("iosX64Test")
                val iosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Test")
                val macosX64Test: KotlinSourceSet? = sourceSets.findByName("macosX64Test")
                val macosArm64Test: KotlinSourceSet? = sourceSets.findByName("macosArm64Test")
                val tvosArm64Test: KotlinSourceSet? = sourceSets.findByName("tvosArm64Test")
                val tvosX64Test: KotlinSourceSet? = sourceSets.findByName("tvosX64Test")
                val tvosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Test")
                val watchosArm32Test: KotlinSourceSet? = sourceSets.findByName("watchosArm32Test")
                val watchosArm64Test: KotlinSourceSet? = sourceSets.findByName("watchosArm64Test")
                val watchosX64Test: KotlinSourceSet? = sourceSets.findByName("watchosX64Test")
                val watchosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Test")
                val watchosX86Test: KotlinSourceSet? = sourceSets.findByName("watchosX86Test")

                val appleMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        iosArm32Main,
                        iosArm64Main,
                        iosX64Main,
                        iosSimulatorArm64Main,
                        macosX64Main,
                        macosArm64Main,
                        tvosArm64Main,
                        tvosX64Main,
                        tvosSimulatorArm64Main,
                        watchosArm32Main,
                        watchosArm64Main,
                        watchosX64Main,
                        watchosSimulatorArm64Main,
                        watchosX86Main,
                    )

                val appleTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        iosArm32Test,
                        iosArm64Test,
                        iosX64Test,
                        iosSimulatorArm64Test,
                        macosX64Test,
                        macosArm64Test,
                        tvosArm64Test,
                        tvosX64Test,
                        tvosSimulatorArm64Test,
                        watchosArm32Test,
                        watchosArm64Test,
                        watchosX64Test,
                        watchosSimulatorArm64Test,
                        watchosX86Test,
                    )

                val appleMain = sourceSets.maybeCreate("appleMain")
                val appleTest = sourceSets.maybeCreate("appleTest")

                appleMain.dependsOn(commonMain)
                for (appleMainSourceSet in appleMainSourceSets) {
                    appleMainSourceSet.dependsOn(appleMain)
                }

                appleTest.dependsOn(commonTest)
                for (appleTestSourceSet in appleTestSourceSets) {
                    appleTestSourceSet.dependsOn(appleTest)
                }
            }
        }
    }
}
