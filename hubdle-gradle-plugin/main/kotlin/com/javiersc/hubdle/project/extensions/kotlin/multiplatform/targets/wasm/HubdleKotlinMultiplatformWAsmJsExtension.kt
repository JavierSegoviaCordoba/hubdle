package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.wasm

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinWasmD8Dsl

@HubdleDslMarker
public open class HubdleKotlinMultiplatformWAsmJsExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform.wasm)

    override val targetName: String = "wasmJs"

    @HubdleDslMarker
    public fun browser(action: Action<KotlinJsBrowserDsl> = Action {}) {
        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                wasmJs().apply {
                    browser { //
                        action.execute(this)
                    }
                }
            }
        }
    }

    @HubdleDslMarker
    public fun d8(action: Action<KotlinWasmD8Dsl> = Action {}) {
        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                wasmJs().apply {
                    d8 { //
                        action.execute(this)
                    }
                }
            }
        }
    }

    @HubdleDslMarker
    public fun nodejs(action: Action<KotlinJsNodeDsl> = Action {}) {
        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                wasmJs().apply {
                    nodejs { //
                        action.execute(this)
                    }
                }
            }
        }
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable { configure<KotlinMultiplatformExtension> { wasmJs() } }
    }
}
