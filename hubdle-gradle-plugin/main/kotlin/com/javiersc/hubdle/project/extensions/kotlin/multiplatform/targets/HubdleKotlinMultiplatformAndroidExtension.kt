package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@HubdleDslMarker
public open class HubdleKotlinMultiplatformAndroidExtension @Inject constructor(project: Project) :
    HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    public override val targetName: String = "android"

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    public val namespace: Property<String> = convention { hubdleAndroid.namespace }

    public val minSdk: Property<Int> = property { hubdleAndroid.minSdk.get() }

    public val compileSdk: Property<Int> = property { hubdleAndroid.compileSdk.get() }

    public val targetSdk: Property<Int> = property { hubdleAndroid.targetSdk.get() }

    @HubdleDslMarker
    public fun android(action: Action<KotlinMultiplatformAndroidLibraryTarget>): Unit =
        afterConfigurable {
            configure<KotlinMultiplatformExtension> {
                val androidLibrary: KotlinMultiplatformAndroidLibraryTarget = extensions.getByType()
                action.execute(androidLibrary)
            }
        }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.AndroidKotlinMultiplatformLibrary,
        )

        lazyConfigurable {
            configure<KotlinMultiplatformExtension> {
                extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                    val hubdleAndroid = this@HubdleKotlinMultiplatformAndroidExtension
                    this.compileSdk = hubdleAndroid.compileSdk.get()
                    this.minSdk = hubdleAndroid.minSdk.get()
                    this.namespace = hubdleAndroid.namespace.orNull
                }
            }
        }
    }
}

internal val Project.hubdleKotlinMultiplatformAndroid: HubdleKotlinMultiplatformAndroidExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdleKotlinMultiplatformAndroid:
    HubdleKotlinMultiplatformAndroidExtension
    get() = getHubdleExtension()
