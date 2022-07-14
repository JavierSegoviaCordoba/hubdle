package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableAllOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformNativeExtension :
    EnableableOptions, EnableableAllOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.native.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { native.isEnabled = value }

    override var Project.allEnabled: Boolean
        get() =
            hubdleState.kotlin.multiplatform.iosArm32.isEnabled &&
                hubdleState.kotlin.multiplatform.iosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.iosSimulatorArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.iosX64.isEnabled &&
                hubdleState.kotlin.multiplatform.linuxArm32Hfp.isEnabled &&
                hubdleState.kotlin.multiplatform.linuxArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.linuxMips32.isEnabled &&
                hubdleState.kotlin.multiplatform.linuxMipsel32.isEnabled &&
                hubdleState.kotlin.multiplatform.linuxX64.isEnabled &&
                hubdleState.kotlin.multiplatform.macosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.macosX64.isEnabled &&
                hubdleState.kotlin.multiplatform.mingwX64.isEnabled &&
                hubdleState.kotlin.multiplatform.mingwX86.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosSimulatorArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosX64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosSimulatorArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosX64.isEnabled &&
                hubdleState.kotlin.multiplatform.wasm32.isEnabled &&
                hubdleState.kotlin.multiplatform.watchosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.watchosSimulatorArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.watchosX64.isEnabled &&
                hubdleState.kotlin.multiplatform.watchosX86.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                ios.isEnabled = value
                iosArm32.isEnabled = value
                iosArm64.isEnabled = value
                iosSimulatorArm64.isEnabled = value
                iosX64.isEnabled = value
                linux.isEnabled = value
                linuxArm32Hfp.isEnabled = value
                linuxArm64.isEnabled = value
                linuxMips32.isEnabled = value
                linuxMipsel32.isEnabled = value
                linuxX64.isEnabled = value
                macos.isEnabled = value
                macosArm64.isEnabled = value
                macosX64.isEnabled = value
                mingw.isEnabled = value
                mingwX64.isEnabled = value
                mingwX86.isEnabled = value
                tvos.isEnabled = value
                tvosArm64.isEnabled = value
                tvosSimulatorArm64.isEnabled = value
                tvosX64.isEnabled = value
                tvosArm64.isEnabled = value
                tvosSimulatorArm64.isEnabled = value
                tvosX64.isEnabled = value
                wasm.isEnabled = value
                wasm32.isEnabled = value
                watchos.isEnabled = value
                watchosArm64.isEnabled = value
                watchosSimulatorArm64.isEnabled = value
                watchosX64.isEnabled = value
                watchosX86.isEnabled = value
            }

    override val name: String = "native"
}
