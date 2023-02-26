package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.kotlin.android._internal.calculateAndroidNamespace
import com.javiersc.hubdle.extensions.kotlin.android._internal.configureAndroidLibraryJavaVersion
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import com.javiersc.hubdle.extensions.shared.android.HubdleAndroidDelegateSharedApis
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

    override val priority: Priority = Priority.P3

    public override val targetName: String = "android"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val namespace: Property<String?> = property { null }

    public val minSdk: Property<Int> = property { 23 }

    public val compileSdk: Property<Int> = property { 33 }

    public val targetSdk: Property<Int> = property { 33 }

    public val publishLibraryVariants: SetProperty<String> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun publishLibraryVariants(vararg names: String) {
        publishLibraryVariants.addAll(names.toList())
        publishAllLibraryVariants.set(false)
        configurable {
            configure<KotlinMultiplatformExtension> { android { publishLibraryVariants(*names) } }
        }
    }

    public val publishAllLibraryVariants: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun publishAllLibraryVariants() {
        publishAllLibraryVariants.set(true)
        configurable {
            configure<KotlinMultiplatformExtension> { android { publishAllLibraryVariants() } }
        }
    }

    @HubdleDslMarker
    public fun android(action: Action<LibraryExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.AndroidLibrary
        )

        configurable {
            val hubdleAndroid = this@HubdleKotlinMultiplatformAndroidExtension
            configure<KotlinMultiplatformExtension> {
                val publishVariants = hubdleAndroid.publishLibraryVariants.get().toTypedArray()
                val publishAllVariants = hubdleAndroid.publishAllLibraryVariants.get()
                android {
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
                namespace = calculateAndroidNamespace(hubdleAndroid.namespace.orNull)
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
