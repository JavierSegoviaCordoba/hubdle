package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.wasm.HubdleKotlinMultiplatformWAsmJsExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.wasm.HubdleKotlinMultiplatformWAsmWAsiExtension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformWAsmExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val targetName: String = "wasm"

    public val js: HubdleKotlinMultiplatformWAsmJsExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun js(action: Action<HubdleKotlinMultiplatformWAsmJsExtension> = Action {}) {
        js.enableAndExecute(action)
    }

    public val wasi: HubdleKotlinMultiplatformWAsmWAsiExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun wasi(action: Action<HubdleKotlinMultiplatformWAsmWAsiExtension> = Action {}) {
        wasi.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {}
}
