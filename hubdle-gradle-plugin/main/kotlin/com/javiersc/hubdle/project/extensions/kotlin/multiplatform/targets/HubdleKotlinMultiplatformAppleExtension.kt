package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformAppleExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

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
        lazyConfigurable {
            if (allEnabled.get()) {
                ios.allEnabled()
                macos.allEnabled()
                tvos.allEnabled()
                watchos.allEnabled()
            }
        }
    }
}
