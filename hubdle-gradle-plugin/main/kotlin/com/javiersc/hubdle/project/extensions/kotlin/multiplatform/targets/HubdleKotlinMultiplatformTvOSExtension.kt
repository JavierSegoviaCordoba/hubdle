package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.tvos.HubdleKotlinMultiplatformTvOSX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformTvOSExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val targetName: String = "tvos"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val tvosArm64: HubdleKotlinMultiplatformTvOSArm64Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun tvosArm64(action: Action<HubdleKotlinMultiplatformTvOSArm64Extension> = Action {}) {
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
        lazyConfigurable {
            if (allEnabled.get()) {
                tvosArm64()
                tvosSimulatorArm64()
                tvosX64()
            }
        }
    }
}
