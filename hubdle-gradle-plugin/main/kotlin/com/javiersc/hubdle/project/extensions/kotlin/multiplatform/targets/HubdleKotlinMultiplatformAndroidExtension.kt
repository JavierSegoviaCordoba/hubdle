package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.android._internal.configureAndroidLibraryJavaVersion
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.shared.android.HubdleAndroidDelegateSharedApis
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@HubdleDslMarker
public open class HubdleKotlinMultiplatformAndroidExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project), HubdleAndroidDelegateSharedApis {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    public override val targetName: String = "android"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val namespace: Property<String?> = property { hubdleAndroid.namespace.orNull }

    public val minSdk: Property<Int> = property { hubdleAndroid.minSdk.get() }

    public val compileSdk: Property<Int> = property { hubdleAndroid.compileSdk.get() }

    public val targetSdk: Property<Int> = property { hubdleAndroid.targetSdk.get() }

    public val publishLibraryVariants: SetProperty<String> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun publishLibraryVariants(vararg names: String) {
        publishLibraryVariants.addAll(names.toList())
        publishAllLibraryVariants.set(false)
        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                androidTarget { publishLibraryVariants(*names) }
            }
        }
    }

    public val publishAllLibraryVariants: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun publishAllLibraryVariants() {
        publishAllLibraryVariants.set(true)
        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                androidTarget { publishAllLibraryVariants() }
            }
        }
    }

    @HubdleDslMarker
    public fun android(action: Action<LibraryExtension>): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.AndroidLibrary)

        lazyConfigurable {
            val hubdleAndroid = this@HubdleKotlinMultiplatformAndroidExtension
            configure<KotlinMultiplatformExtension> {
                val publishVariants = hubdleAndroid.publishLibraryVariants.get().toTypedArray()
                val publishAllVariants = hubdleAndroid.publishAllLibraryVariants.get()
                androidTarget {
                    if (publishVariants.isNotEmpty() && !publishAllVariants) {
                        publishLibraryVariants(*publishVariants)
                    }
                    if (publishAllVariants) {
                        publishAllLibraryVariants()
                    }
                }
            }
            configure<LibraryExtension> {
                compileSdk = hubdleAndroid.compileSdk.get()
                defaultConfig.minSdk = hubdleAndroid.minSdk.get()
                namespace = hubdleAndroid.namespace.orNull
            }
            configureAndroidLibraryJavaVersion()
        }
    }
}

internal val Project.hubdleKotlinMultiplatformAndroid: HubdleKotlinMultiplatformAndroidExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdleKotlinMultiplatformAndroid:
    HubdleKotlinMultiplatformAndroidExtension
    get() = getHubdleExtension()
