package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinMultiplatformNativeExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleKotlinMultiplatformTargetOptions {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val priority: Priority = Priority.P3

    override val targetName: String = "native"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            if (allEnabled.get()) {
                hubdleKotlinMultiplatform.apple.allEnabled()
                hubdleKotlinMultiplatform.linux.allEnabled()
                hubdleKotlinMultiplatform.mingw.allEnabled()
            }
        }

        configurable(priority = Priority.P4) {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val iosArm32Main: KotlinSourceSet? = sourceSets.findByName("iosArm32Main")
                val iosArm64Main: KotlinSourceSet? = sourceSets.findByName("iosArm64Main")
                val iosX64Main: KotlinSourceSet? = sourceSets.findByName("iosX64Main")
                val iosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Main")
                val linuxArm64Main: KotlinSourceSet? = sourceSets.findByName("linuxArm64Main")
                val linuxArm32HfpMain: KotlinSourceSet? = sourceSets.findByName("linuxArm32HfpMain")
                val linuxMips32Main: KotlinSourceSet? = sourceSets.findByName("linuxMips32Main")
                val linuxMipsel32Main: KotlinSourceSet? = sourceSets.findByName("linuxMipsel32Main")
                val linuxX64Main: KotlinSourceSet? = sourceSets.findByName("linuxX64Main")
                val macosX64Main: KotlinSourceSet? = sourceSets.findByName("macosX64Main")
                val macosArm64Main: KotlinSourceSet? = sourceSets.findByName("macosArm64Main")
                val mingwX64Main: KotlinSourceSet? = sourceSets.findByName("mingwX64Main")
                val mingwX86Main: KotlinSourceSet? = sourceSets.findByName("mingwX86Main")
                val tvosArm64Main: KotlinSourceSet? = sourceSets.findByName("tvosArm64Main")
                val tvosX64Main: KotlinSourceSet? = sourceSets.findByName("tvosX64Main")
                val tvosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Main")
                val wasm32Main: KotlinSourceSet? = sourceSets.findByName("wasm32Main")
                val watchosArm32Main: KotlinSourceSet? = sourceSets.findByName("watchosArm32Main")
                val watchosArm64Main: KotlinSourceSet? = sourceSets.findByName("watchosArm64Main")
                val watchosX64Main: KotlinSourceSet? = sourceSets.findByName("watchosX64Main")
                val watchosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Main")
                val watchosX86Main: KotlinSourceSet? = sourceSets.findByName("watchosX86Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val iosArm32Test: KotlinSourceSet? = sourceSets.findByName("iosArm32Test")
                val iosArm64Test: KotlinSourceSet? = sourceSets.findByName("iosArm64Test")
                val iosX64Test: KotlinSourceSet? = sourceSets.findByName("iosX64Test")
                val iosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Test")
                val linuxArm64Test: KotlinSourceSet? = sourceSets.findByName("linuxArm64Test")
                val linuxArm32HfpTest: KotlinSourceSet? = sourceSets.findByName("linuxArm32HfpTest")
                val linuxMips32Test: KotlinSourceSet? = sourceSets.findByName("linuxMips32Test")
                val linuxMipsel32Test: KotlinSourceSet? = sourceSets.findByName("linuxMipsel32Test")
                val linuxX64Test: KotlinSourceSet? = sourceSets.findByName("linuxX64Test")
                val macosX64Test: KotlinSourceSet? = sourceSets.findByName("macosX64Test")
                val macosArm64Test: KotlinSourceSet? = sourceSets.findByName("macosArm64Test")
                val mingwX64Test: KotlinSourceSet? = sourceSets.findByName("mingwX64Test")
                val mingwX86Test: KotlinSourceSet? = sourceSets.findByName("mingwX86Test")
                val tvosArm64Test: KotlinSourceSet? = sourceSets.findByName("tvosArm64Test")
                val tvosX64Test: KotlinSourceSet? = sourceSets.findByName("tvosX64Test")
                val tvosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Test")
                val wasm32Test: KotlinSourceSet? = sourceSets.findByName("wasm32Test")
                val watchosArm32Test: KotlinSourceSet? = sourceSets.findByName("watchosArm32Test")
                val watchosArm64Test: KotlinSourceSet? = sourceSets.findByName("watchosArm64Test")
                val watchosX64Test: KotlinSourceSet? = sourceSets.findByName("watchosX64Test")
                val watchosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Test")
                val watchosX86Test: KotlinSourceSet? = sourceSets.findByName("watchosX86Test")

                val nativeMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        iosArm32Main,
                        iosArm64Main,
                        iosX64Main,
                        iosSimulatorArm64Main,
                        linuxArm64Main,
                        linuxArm32HfpMain,
                        linuxMips32Main,
                        linuxMipsel32Main,
                        linuxX64Main,
                        macosX64Main,
                        macosArm64Main,
                        mingwX64Main,
                        mingwX86Main,
                        tvosArm64Main,
                        tvosX64Main,
                        tvosSimulatorArm64Main,
                        wasm32Main,
                        watchosArm32Main,
                        watchosArm64Main,
                        watchosX64Main,
                        watchosSimulatorArm64Main,
                        watchosX86Main,
                    )

                val nativeTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        iosArm32Test,
                        iosArm64Test,
                        iosX64Test,
                        iosSimulatorArm64Test,
                        linuxArm64Test,
                        linuxArm32HfpTest,
                        linuxMips32Test,
                        linuxMipsel32Test,
                        linuxX64Test,
                        macosX64Test,
                        macosArm64Test,
                        mingwX64Test,
                        mingwX86Test,
                        tvosArm64Test,
                        tvosX64Test,
                        tvosSimulatorArm64Test,
                        wasm32Test,
                        watchosArm32Test,
                        watchosArm64Test,
                        watchosX64Test,
                        watchosSimulatorArm64Test,
                        watchosX86Test,
                    )

                val nativeMain = sourceSets.maybeCreate("nativeMain")
                val nativeTest = sourceSets.maybeCreate("nativeTest")

                nativeMain.dependsOn(commonMain)
                for (nativeMainSourceSet in nativeMainSourceSets) {
                    nativeMainSourceSet.dependsOn(nativeMain)
                }

                nativeTest.dependsOn(commonTest)
                for (nativeTestSourceSet in nativeTestSourceSets) {
                    nativeTestSourceSet.dependsOn(nativeTest)
                }
            }
        }
    }
}
