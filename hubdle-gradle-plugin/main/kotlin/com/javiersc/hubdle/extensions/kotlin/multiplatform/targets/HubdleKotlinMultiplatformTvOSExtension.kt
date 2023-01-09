package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSX64Extension
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
public open class HubdleKotlinMultiplatformTvOSExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleKotlinMultiplatformTargetOptions {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val targetName: String = "tvos"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    @HubdleDslMarker
    public val tvosArm64: HubdleKotlinMultiplatformTvOSSimulatorArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun tvosArm64(
        action: Action<HubdleKotlinMultiplatformTvOSSimulatorArm64Extension> = Action {}
    ) {
        tvosArm64.enableAndExecute(action)
    }

    public val tvosSimulatorArm64: HubdleKotlinMultiplatformTvOSSimulatorArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun tvosSimulatorArm64(
        action: Action<HubdleKotlinMultiplatformTvOSSimulatorArm64Extension> = Action {}
    ) {
        tvosSimulatorArm64.enableAndExecute(action)
    }

    public val tvosX64: HubdleKotlinMultiplatformTvOSX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun tvosX64(action: Action<HubdleKotlinMultiplatformTvOSX64Extension> = Action {}) {
        tvosX64.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                tvosArm64()
                tvosSimulatorArm64()
                tvosX64()
            }
        }
        configurable(priority = Priority.P6) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val tvosArm64Main: KotlinSourceSet? = sourceSets.findByName("tvosArm64Main")
                val tvosX64Main: KotlinSourceSet? = sourceSets.findByName("tvosX64Main")
                val tvosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val tvosArm64Test: KotlinSourceSet? = sourceSets.findByName("tvosArm64Test")
                val tvosX64Test: KotlinSourceSet? = sourceSets.findByName("tvosX64Test")
                val tvosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Test")

                val tvosMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        tvosArm64Main,
                        tvosX64Main,
                        tvosSimulatorArm64Main,
                    )
                val tvosTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        tvosArm64Test,
                        tvosX64Test,
                        tvosSimulatorArm64Test,
                    )

                val tvosMain = sourceSets.maybeCreate("tvosMain")
                val tvosTest = sourceSets.maybeCreate("tvosTest")

                tvosMain.dependsOn(commonMain)
                for (tvosMainSourceSet in tvosMainSourceSets) {
                    tvosMainSourceSet.dependsOn(tvosMain)
                }

                tvosTest.dependsOn(commonTest)
                for (tvosTestSourceSet in tvosTestSourceSets) {
                    tvosTestSourceSet.dependsOn(tvosTest)
                }
            }
        }
    }
}
