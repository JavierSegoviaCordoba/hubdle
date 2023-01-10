package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.wasm.HubdleKotlinMultiplatformWAsm32Extension
import com.javiersc.hubdle.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinMultiplatformWAsmExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val priority: Priority = Priority.P3

    override val targetName: String = "wasm"

    public val wasm32: HubdleKotlinMultiplatformWAsm32Extension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun wasm32(action: Action<HubdleKotlinMultiplatformWAsm32Extension> = Action {}) {
        wasm32.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        configurable(priority = Priority.P6) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val wasm32Main: KotlinSourceSet? = sourceSets.findByName("wasm32Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val wasm32Test: KotlinSourceSet? = sourceSets.findByName("wasm32Test")

                val wasmMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        wasm32Main,
                    )
                val wasmTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        wasm32Test,
                    )

                val wasmMain = sourceSets.maybeCreate("wasmMain")
                val wasmTest = sourceSets.maybeCreate("wasmTest")

                wasmMain.dependsOn(commonMain)
                for (wasmMainSourceSet in wasmMainSourceSets) {
                    wasmMainSourceSet.dependsOn(wasmMain)
                }

                wasmTest.dependsOn(commonTest)
                for (wasmTestSourceSet in wasmTestSourceSets) {
                    wasmTestSourceSet.dependsOn(wasmTest)
                }
            }
        }
    }
}
