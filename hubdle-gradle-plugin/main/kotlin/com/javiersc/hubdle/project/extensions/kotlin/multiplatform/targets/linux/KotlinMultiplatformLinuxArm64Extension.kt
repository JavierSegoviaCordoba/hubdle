package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.linux

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
public open class KotlinMultiplatformLinuxArm64Extension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    public override val targetName: String = "linuxArm64"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform.linux)

    override fun Project.defaultConfiguration() {
        configurableTargetPerOs(operativeSystem = OperatingSystem.current().isLinux) {
            linuxArm64()
        }
    }
}
