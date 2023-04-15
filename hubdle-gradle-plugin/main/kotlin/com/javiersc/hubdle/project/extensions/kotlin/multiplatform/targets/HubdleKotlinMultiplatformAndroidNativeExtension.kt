package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeArm32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.android.HubdleKotlinMultiplatformAndroidNativeX86Extension
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
public open class HubdleKotlinMultiplatformAndroidNativeExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val targetName: String = "androidNative"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    public val androidNativeArm32: HubdleKotlinMultiplatformAndroidNativeArm32Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun androidNativeArm32(
        action: Action<HubdleKotlinMultiplatformAndroidNativeArm32Extension> = Action {}
    ) {
        androidNativeArm32.enableAndExecute(action)
    }

    public val androidNativeArm64: HubdleKotlinMultiplatformAndroidNativeArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun androidNativeArm64(
        action: Action<HubdleKotlinMultiplatformAndroidNativeArm64Extension> = Action {}
    ) {
        androidNativeArm64.enableAndExecute(action)
    }

    public val androidNativeX64: HubdleKotlinMultiplatformAndroidNativeX64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun androidNativeX64(
        action: Action<HubdleKotlinMultiplatformAndroidNativeX64Extension> = Action {}
    ) {
        androidNativeX64.enableAndExecute(action)
    }

    public val androidNativeX86: HubdleKotlinMultiplatformAndroidNativeX86Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun androidNativeX86(
        action: Action<HubdleKotlinMultiplatformAndroidNativeX86Extension> = Action {}
    ) {
        androidNativeX86.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                androidNativeArm32()
                androidNativeArm64()
                androidNativeX64()
                androidNativeX86()
            }
        }

        configurable(priority = Priority.P6) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val androidNativeArm32Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm32Main")
                val androidNativeArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm64Main")
                val androidNativeX64Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX64Main")
                val androidNativeX86Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX86Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val androidNativeArm32Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm32Test")
                val androidNativeArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm64Test")
                val androidNativeX64Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX64Test")
                val androidNativeX86Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX86Test")

                val androidNativeMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        androidNativeArm32Main,
                        androidNativeArm64Main,
                        androidNativeX64Main,
                        androidNativeX86Main,
                    )

                val androidNativeTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        androidNativeArm32Test,
                        androidNativeArm64Test,
                        androidNativeX64Test,
                        androidNativeX86Test,
                    )

                val androidNativeMain = sourceSets.maybeCreate("androidNativeMain")
                val androidNativeTest = sourceSets.maybeCreate("androidNativeTest")

                androidNativeMain.dependsOn(commonMain)
                for (androidNativeMainSourceSet in androidNativeMainSourceSets) {
                    androidNativeMainSourceSet.dependsOn(androidNativeMain)
                }

                androidNativeTest.dependsOn(commonTest)
                for (androidNativeTestSourceSet in androidNativeTestSourceSets) {
                    androidNativeTestSourceSet.dependsOn(androidNativeTest)
                }
            }
        }
    }
}
