package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
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
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val targetName: String = "native"

    public val allEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun allEnabled(value: Boolean = true) {
        allEnabled.set(value)
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            if (allEnabled.get()) {
                hubdleKotlinMultiplatform.androidNative.allEnabled()
                hubdleKotlinMultiplatform.apple.allEnabled()
                hubdleKotlinMultiplatform.linux.allEnabled()
                hubdleKotlinMultiplatform.mingw.allEnabled()
            }
        }

        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                val commonMain: KotlinSourceSet by sourceSets.getting
                val androidNativeArm32Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm32Main")
                val androidNativeArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm64Main")
                val androidNativeX64Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX64Main")
                val androidNativeX86Main: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX86Main")
                val iosArm64Main: KotlinSourceSet? = sourceSets.findByName("iosArm64Main")
                val iosX64Main: KotlinSourceSet? = sourceSets.findByName("iosX64Main")
                val iosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Main")
                val linuxArm64Main: KotlinSourceSet? = sourceSets.findByName("linuxArm64Main")
                val linuxX64Main: KotlinSourceSet? = sourceSets.findByName("linuxX64Main")
                val macosX64Main: KotlinSourceSet? = sourceSets.findByName("macosX64Main")
                val macosArm64Main: KotlinSourceSet? = sourceSets.findByName("macosArm64Main")
                val mingwX64Main: KotlinSourceSet? = sourceSets.findByName("mingwX64Main")
                val tvosArm64Main: KotlinSourceSet? = sourceSets.findByName("tvosArm64Main")
                val tvosX64Main: KotlinSourceSet? = sourceSets.findByName("tvosX64Main")
                val tvosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Main")
                val wasmJsMain: KotlinSourceSet? = sourceSets.findByName("wasmJsMain")
                val watchosArm32Main: KotlinSourceSet? = sourceSets.findByName("watchosArm32Main")
                val watchosArm64Main: KotlinSourceSet? = sourceSets.findByName("watchosArm64Main")
                val watchosX64Main: KotlinSourceSet? = sourceSets.findByName("watchosX64Main")
                val watchosSimulatorArm64Main: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Main")

                val commonTest: KotlinSourceSet by sourceSets.getting
                val androidNativeArm32Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm32Test")
                val androidNativeArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeArm64Test")
                val androidNativeX64Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX64Test")
                val androidNativeX86Test: KotlinSourceSet? =
                    sourceSets.findByName("androidNativeX86Test")
                val iosArm64Test: KotlinSourceSet? = sourceSets.findByName("iosArm64Test")
                val iosX64Test: KotlinSourceSet? = sourceSets.findByName("iosX64Test")
                val iosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("iosSimulatorArm64Test")
                val linuxArm64Test: KotlinSourceSet? = sourceSets.findByName("linuxArm64Test")
                val linuxX64Test: KotlinSourceSet? = sourceSets.findByName("linuxX64Test")
                val macosX64Test: KotlinSourceSet? = sourceSets.findByName("macosX64Test")
                val macosArm64Test: KotlinSourceSet? = sourceSets.findByName("macosArm64Test")
                val mingwX64Test: KotlinSourceSet? = sourceSets.findByName("mingwX64Test")
                val tvosArm64Test: KotlinSourceSet? = sourceSets.findByName("tvosArm64Test")
                val tvosX64Test: KotlinSourceSet? = sourceSets.findByName("tvosX64Test")
                val tvosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("tvosSimulatorArm64Test")
                val wasmJsTest: KotlinSourceSet? = sourceSets.findByName("wasmJsTest")
                val watchosArm32Test: KotlinSourceSet? = sourceSets.findByName("watchosArm32Test")
                val watchosArm64Test: KotlinSourceSet? = sourceSets.findByName("watchosArm64Test")
                val watchosX64Test: KotlinSourceSet? = sourceSets.findByName("watchosX64Test")
                val watchosSimulatorArm64Test: KotlinSourceSet? =
                    sourceSets.findByName("watchosSimulatorArm64Test")

                val nativeMainSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        androidNativeArm32Main,
                        androidNativeArm64Main,
                        androidNativeX64Main,
                        androidNativeX86Main,
                        iosArm64Main,
                        iosX64Main,
                        iosSimulatorArm64Main,
                        linuxArm64Main,
                        linuxX64Main,
                        macosX64Main,
                        macosArm64Main,
                        mingwX64Main,
                        tvosArm64Main,
                        tvosX64Main,
                        tvosSimulatorArm64Main,
                        wasmJsMain,
                        watchosArm32Main,
                        watchosArm64Main,
                        watchosX64Main,
                        watchosSimulatorArm64Main,
                    )

                val nativeTestSourceSets: List<KotlinSourceSet> =
                    listOfNotNull(
                        androidNativeArm32Test,
                        androidNativeArm64Test,
                        androidNativeX64Test,
                        androidNativeX86Test,
                        iosArm64Test,
                        iosX64Test,
                        iosSimulatorArm64Test,
                        linuxArm64Test,
                        linuxX64Test,
                        macosX64Test,
                        macosArm64Test,
                        mingwX64Test,
                        tvosArm64Test,
                        tvosX64Test,
                        tvosSimulatorArm64Test,
                        wasmJsTest,
                        watchosArm32Test,
                        watchosArm64Test,
                        watchosX64Test,
                        watchosSimulatorArm64Test,
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
