package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.HubdleKotlinMultiplatformMacOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.HubdleKotlinMultiplatformMacOSX64Extension
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
public open class HubdleKotlinMultiplatformMacOSExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleKotlinMultiplatformTargetOptions {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { true }

    override val priority: Priority = Priority.P3

    override val targetName: String = "macos"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val macosArm64: HubdleKotlinMultiplatformMacOSArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun macosArm64(
        action: Action<HubdleKotlinMultiplatformMacOSArm64Extension> = Action {}
    ) {
        macosArm64.enableAndExecute(action)
    }

    public val macosX64: HubdleKotlinMultiplatformMacOSX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun macosX64(action: Action<HubdleKotlinMultiplatformMacOSX64Extension> = Action {}) {
        macosX64.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                macosArm64()
                macosX64()
            }
        }
        configurable(priority = Priority.P4) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val macosX64Main: KotlinSourceSet? = sourceSets.findByName("macosX64Main")
                val macosArm64Main: KotlinSourceSet? = sourceSets.findByName("macosArm64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val macosX64Test: KotlinSourceSet? = sourceSets.findByName("macosX64Test")
                val macosArm64Test: KotlinSourceSet? = sourceSets.findByName("macosArm64Test")

                val macosMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        macosX64Main,
                        macosArm64Main,
                    )
                val macosTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        macosX64Test,
                        macosArm64Test,
                    )

                val macosMain = sourceSets.maybeCreate("macosMain")
                val macosTest = sourceSets.maybeCreate("macosTest")

                macosMain.dependsOn(commonMain)
                for (macosMainSourceSet in macosMainSourceSets) {
                    macosMainSourceSet.dependsOn(macosMain)
                }

                macosTest.dependsOn(commonTest)
                for (macosTestSourceSet in macosTestSourceSets) {
                    macosTestSourceSet.dependsOn(macosTest)
                }
            }
        }
    }
}
