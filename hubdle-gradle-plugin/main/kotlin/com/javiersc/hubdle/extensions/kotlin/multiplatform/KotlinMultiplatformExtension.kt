@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.kotlin.multiplatform

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.dependencies._internal.MultiplatformDependencies
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.multiplatformFeatures
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformCommonExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJsExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJvmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformLinuxExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformMacOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformMinGWExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformNativeExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTvOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformWAsmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformWatchOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformiOSExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSArm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios.KotlinMultiplatformiOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm32HfpExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMips32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxMipsel32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux.KotlinMultiplatformLinuxX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.KotlinMultiplatformMacOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos.KotlinMultiplatformMacOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw.KotlinMultiplatformMinGWX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw.KotlinMultiplatformMinGWX86Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.KotlinMultiplatformTvOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.KotlinMultiplatformTvOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.tvos.KotlinMultiplatformTvOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.wasm.KotlinMultiplatformWAsm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSArm32Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSSimulatorArm64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSX64Extension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos.KotlinMultiplatformWatchOSX86Extension
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KotlinProjectMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class KotlinMultiplatformExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<KotlinMultiplatformExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<KotlinMultiplatformExtension.RawConfigExtension>,
    MultiplatformDependencies {

    override var isEnabled: Boolean = IS_ENABLED

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    override var jvmVersion: Int = KotlinJvmOptions.JVM_VERSION

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinProjectMultiplatformExtension>().sourceSets

    private val common: KotlinMultiplatformCommonExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.common(action: Action<KotlinMultiplatformCommonExtension> = Action {}) {
        action.execute(common)
    }

    private val android: KotlinMultiplatformAndroidExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.android(action: Action<KotlinMultiplatformAndroidExtension> = Action {}) {
        android.isEnabled = true
        action.execute(android)
        hubdleState.kotlin.multiplatform.android.isEnabled = android.isEnabled
    }

    private val ios: KotlinMultiplatformiOSExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.ios(action: Action<KotlinMultiplatformiOSExtension> = Action {}) {
        ios.isEnabled = true
        action.execute(ios)
        hubdleState.kotlin.multiplatform.ios.isEnabled = ios.isEnabled
    }

    private val iosArm32: KotlinMultiplatformiOSArm32Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.iosArm32(action: Action<KotlinMultiplatformiOSArm32Extension> = Action {}) {
        iosArm32.isEnabled = true
        action.execute(iosArm32)
        hubdleState.kotlin.multiplatform.iosArm32.isEnabled = iosArm32.isEnabled
    }

    private val iosArm64: KotlinMultiplatformiOSArm64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.iosArm64(action: Action<KotlinMultiplatformiOSArm64Extension> = Action {}) {
        iosArm64.isEnabled = true
        action.execute(iosArm64)
        hubdleState.kotlin.multiplatform.iosArm64.isEnabled = iosArm64.isEnabled
    }

    private val iosX64: KotlinMultiplatformiOSX64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.iosX64(action: Action<KotlinMultiplatformiOSX64Extension> = Action {}) {
        iosX64.isEnabled = true
        action.execute(iosX64)
        hubdleState.kotlin.multiplatform.iosX64.isEnabled = iosX64.isEnabled
    }

    private val iosSimulatorArm64: KotlinMultiplatformiOSSimulatorArm64Extension =
        objects.newInstance()

    @HubdleDslMarker
    public fun Project.iosSimulatorArm64(
        action: Action<KotlinMultiplatformiOSSimulatorArm64Extension> = Action {}
    ) {
        iosSimulatorArm64.isEnabled = true
        action.execute(iosSimulatorArm64)
        hubdleState.kotlin.multiplatform.iosSimulatorArm64.isEnabled = iosSimulatorArm64.isEnabled
    }

    private val jvm: KotlinMultiplatformJvmExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.jvm(action: Action<KotlinMultiplatformJvmExtension> = Action {}) {
        jvm.isEnabled = true
        action.execute(jvm)
        hubdleState.kotlin.multiplatform.jvm.isEnabled = jvm.isEnabled
    }

    private val js: KotlinMultiplatformJsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.js(action: Action<KotlinMultiplatformJsExtension> = Action {}) {
        js.isEnabled = true
        action.execute(js)
        hubdleState.kotlin.multiplatform.js.isEnabled = js.isEnabled
    }

    private val linux: KotlinMultiplatformLinuxExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.linux(action: Action<KotlinMultiplatformLinuxExtension> = Action {}) {
        linux.isEnabled = true
        action.execute(linux)
        hubdleState.kotlin.multiplatform.linux.isEnabled = linux.isEnabled
    }

    private val linuxArm32Hfp: KotlinMultiplatformLinuxArm32HfpExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.linuxArm32Hfp(
        action: Action<KotlinMultiplatformLinuxArm32HfpExtension> = Action {}
    ) {
        linuxArm32Hfp.isEnabled = true
        action.execute(linuxArm32Hfp)
        hubdleState.kotlin.multiplatform.linuxArm32Hfp.isEnabled = linuxArm32Hfp.isEnabled
    }

    private val linuxArm64: KotlinMultiplatformLinuxArm64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.linuxArm64(
        action: Action<KotlinMultiplatformLinuxArm64Extension> = Action {}
    ) {
        linuxArm64.isEnabled = true
        action.execute(linuxArm64)
        hubdleState.kotlin.multiplatform.linuxArm64.isEnabled = linuxArm64.isEnabled
    }

    private val linuxMips32: KotlinMultiplatformLinuxMips32Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.linuxMips32(
        action: Action<KotlinMultiplatformLinuxMips32Extension> = Action {}
    ) {
        linuxMips32.isEnabled = true
        action.execute(linuxMips32)
        hubdleState.kotlin.multiplatform.linuxMips32.isEnabled = linuxMips32.isEnabled
    }

    private val linuxMipsel32: KotlinMultiplatformLinuxMipsel32Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.linuxMipsel32(
        action: Action<KotlinMultiplatformLinuxMipsel32Extension> = Action {}
    ) {
        linuxMipsel32.isEnabled = true
        action.execute(linuxMipsel32)
        hubdleState.kotlin.multiplatform.linuxMipsel32.isEnabled = linuxMipsel32.isEnabled
    }

    private val linuxX64: KotlinMultiplatformLinuxX64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.linuxX64(action: Action<KotlinMultiplatformLinuxX64Extension> = Action {}) {
        linuxX64.isEnabled = true
        action.execute(linuxX64)
        hubdleState.kotlin.multiplatform.linuxX64.isEnabled = linuxX64.isEnabled
    }

    private val macos: KotlinMultiplatformMacOSExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.macos(action: Action<KotlinMultiplatformMacOSExtension> = Action {}) {
        macos.isEnabled = true
        action.execute(macos)
        hubdleState.kotlin.multiplatform.macos.isEnabled = macos.isEnabled
    }

    private val macosArm64: KotlinMultiplatformMacOSArm64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.macosArm64(
        action: Action<KotlinMultiplatformMacOSArm64Extension> = Action {}
    ) {
        macosArm64.isEnabled = true
        action.execute(macosArm64)
        hubdleState.kotlin.multiplatform.macosArm64.isEnabled = macosArm64.isEnabled
    }

    private val macosX64: KotlinMultiplatformMacOSX64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.macosX64(action: Action<KotlinMultiplatformMacOSX64Extension> = Action {}) {
        macosX64.isEnabled = true
        action.execute(macosX64)
        hubdleState.kotlin.multiplatform.macosX64.isEnabled = macosX64.isEnabled
    }

    private val mingw: KotlinMultiplatformMinGWExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.mingw(action: Action<KotlinMultiplatformMinGWExtension> = Action {}) {
        mingw.isEnabled = true
        action.execute(mingw)
        hubdleState.kotlin.multiplatform.mingw.isEnabled = mingw.isEnabled
    }

    private val mingwX64: KotlinMultiplatformMinGWX64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.mingwX64(action: Action<KotlinMultiplatformMinGWX64Extension> = Action {}) {
        mingwX64.isEnabled = true
        action.execute(mingwX64)
        hubdleState.kotlin.multiplatform.mingwX64.isEnabled = mingwX64.isEnabled
    }

    private val mingwX86: KotlinMultiplatformMinGWX86Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.mingwX86(action: Action<KotlinMultiplatformMinGWX86Extension> = Action {}) {
        mingwX86.isEnabled = true
        action.execute(mingwX86)
        hubdleState.kotlin.multiplatform.mingwX86.isEnabled = mingwX86.isEnabled
    }

    private val native: KotlinMultiplatformNativeExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.native(action: Action<KotlinMultiplatformNativeExtension> = Action {}) {
        native.isEnabled = true
        action.execute(native)
        hubdleState.kotlin.multiplatform.native.isEnabled = native.isEnabled
    }

    private val tvos: KotlinMultiplatformTvOSExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.tvos(action: Action<KotlinMultiplatformTvOSExtension> = Action {}) {
        tvos.isEnabled = true
        action.execute(tvos)
        hubdleState.kotlin.multiplatform.tvos.isEnabled = tvos.isEnabled
    }

    private val tvosArm64: KotlinMultiplatformTvOSArm64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.tvosArm64(
        action: Action<KotlinMultiplatformTvOSArm64Extension> = Action {}
    ) {
        tvosArm64.isEnabled = true
        action.execute(tvosArm64)
        hubdleState.kotlin.multiplatform.tvosArm64.isEnabled = tvosArm64.isEnabled
    }

    private val tvosSimulatorArm64: KotlinMultiplatformTvOSSimulatorArm64Extension =
        objects.newInstance()

    @HubdleDslMarker
    public fun Project.tvosSimulatorArm64(
        action: Action<KotlinMultiplatformTvOSSimulatorArm64Extension> = Action {}
    ) {
        tvosSimulatorArm64.isEnabled = true
        action.execute(tvosSimulatorArm64)
        hubdleState.kotlin.multiplatform.tvosSimulatorArm64.isEnabled = tvosSimulatorArm64.isEnabled
    }

    private val tvosX64: KotlinMultiplatformTvOSX64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.tvosX64(action: Action<KotlinMultiplatformTvOSX64Extension> = Action {}) {
        tvosX64.isEnabled = true
        action.execute(tvosX64)
        hubdleState.kotlin.multiplatform.tvosX64.isEnabled = tvosX64.isEnabled
    }

    private val wasm: KotlinMultiplatformWAsmExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.wasm(action: Action<KotlinMultiplatformWAsmExtension> = Action {}) {
        wasm.isEnabled = true
        action.execute(wasm)
        hubdleState.kotlin.multiplatform.wasm.isEnabled = wasm.isEnabled
    }

    private val wasm32: KotlinMultiplatformWAsm32Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.wasm32(action: Action<KotlinMultiplatformWAsm32Extension> = Action {}) {
        wasm32.isEnabled = true
        action.execute(wasm32)
        hubdleState.kotlin.multiplatform.wasm32.isEnabled = wasm32.isEnabled
    }

    private val watchos: KotlinMultiplatformWatchOSExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.watchos(action: Action<KotlinMultiplatformWatchOSExtension> = Action {}) {
        watchos.isEnabled = true
        action.execute(watchos)
        hubdleState.kotlin.multiplatform.watchos.isEnabled = watchos.isEnabled
    }

    private val watchosArm32: KotlinMultiplatformWatchOSArm32Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.watchosArm32(
        action: Action<KotlinMultiplatformWatchOSArm32Extension> = Action {}
    ) {
        watchosArm32.isEnabled = true
        action.execute(watchosArm32)
        hubdleState.kotlin.multiplatform.watchosArm32.isEnabled = watchosArm32.isEnabled
    }

    private val watchosArm64: KotlinMultiplatformWatchOSArm64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.watchosArm64(
        action: Action<KotlinMultiplatformWatchOSArm64Extension> = Action {}
    ) {
        watchosArm64.isEnabled = true
        action.execute(watchosArm64)
        hubdleState.kotlin.multiplatform.watchosArm64.isEnabled = watchosArm64.isEnabled
    }

    private val watchosX64: KotlinMultiplatformWatchOSX64Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.watchosX64(
        action: Action<KotlinMultiplatformWatchOSX64Extension> = Action {}
    ) {
        watchosX64.isEnabled = true
        action.execute(watchosX64)
        hubdleState.kotlin.multiplatform.watchosX64.isEnabled = watchosX64.isEnabled
    }

    private val watchosSimulatorArm64: KotlinMultiplatformWatchOSSimulatorArm64Extension =
        objects.newInstance()

    @HubdleDslMarker
    public fun Project.watchosSimulatorArm64(
        action: Action<KotlinMultiplatformWatchOSSimulatorArm64Extension> = Action {}
    ) {
        watchosSimulatorArm64.isEnabled = true
        action.execute(watchosSimulatorArm64)
        hubdleState.kotlin.multiplatform.watchosSimulatorArm64.isEnabled =
            watchosSimulatorArm64.isEnabled
    }

    private val watchosX86: KotlinMultiplatformWatchOSX86Extension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.watchosX86(
        action: Action<KotlinMultiplatformWatchOSX86Extension> = Action {}
    ) {
        watchosX86.isEnabled = true
        action.execute(watchosX86)
        hubdleState.kotlin.multiplatform.watchosX86.isEnabled = watchosX86.isEnabled
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.kotlin(action: Action<KotlinProjectMultiplatformExtension>) {
            hubdleState.kotlin.multiplatform.rawConfig.kotlin = action
        }
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        @HubdleDslMarker
        public fun Project.coroutines(enabled: Boolean = true) {
            multiplatformFeatures.coroutines = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedStdlib(enabled: Boolean = true) {
            multiplatformFeatures.extendedStdlib = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedTesting(enabled: Boolean = true) {
            multiplatformFeatures.extendedTesting = enabled
        }

        @HubdleDslMarker
        public fun Project.minimumTargetsPerOS(enabled: Boolean = true) {
            multiplatformFeatures.minimumTargetsPerOS = enabled
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
