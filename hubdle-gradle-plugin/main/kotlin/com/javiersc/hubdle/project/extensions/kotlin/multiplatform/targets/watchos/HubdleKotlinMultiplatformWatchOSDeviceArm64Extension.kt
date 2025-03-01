package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.watchos

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.features.configurableTargetPerOs
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.internal.os.OperatingSystem

@HubdleDslMarker
public open class HubdleKotlinMultiplatformWatchOSDeviceArm64Extension
@Inject
constructor(project: Project) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    public override val targetName: String = "watchosDeviceArm64"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform.apple.watchos)

    override fun Project.defaultConfiguration() {
        configurableTargetPerOs(operativeSystem = OperatingSystem.current().isMacOsX) {
            watchosDeviceArm64()
        }
    }
}
