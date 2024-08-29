package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm32Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSDeviceArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos.HubdleKotlinMultiplatformWatchOSX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformWatchOSExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

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

    public val watchosDeviceArm64: HubdleKotlinMultiplatformWatchOSDeviceArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun watchosDeviceArm64(
        action: Action<HubdleKotlinMultiplatformWatchOSDeviceArm64Extension> = Action {}
    ) {
        watchosDeviceArm64.enableAndExecute(action)
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
        lazyConfigurable {
            if (allEnabled.get()) {
                watchosArm32()
                watchosArm64()
                watchosDeviceArm64()
                watchosSimulatorArm64()
                watchosX64()
            }
        }
    }
}
