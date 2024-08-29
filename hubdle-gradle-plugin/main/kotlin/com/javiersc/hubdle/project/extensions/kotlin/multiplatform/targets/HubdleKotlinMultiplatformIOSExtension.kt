package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSSimulatorArm64Extension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.ios.HubdleKotlinMultiplatformIOSX64Extension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformIOSExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    public override val targetName: String = "ios"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
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
        lazyConfigurable {
            if (allEnabled.get()) {
                iosArm64()
                iosSimulatorArm64()
                iosX64()
            }
        }
    }
}
