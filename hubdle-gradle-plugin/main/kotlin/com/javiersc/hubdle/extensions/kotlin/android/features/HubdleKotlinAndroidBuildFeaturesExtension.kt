package com.javiersc.hubdle.extensions.kotlin.android.features

import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.HubdleProperty.Android.BuildFeatures
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.android._internal.configureAndroidCommonExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.extensions.kotlin.android.library.hubdleAndroidLibrary
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinAndroidBuildFeaturesExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAndroidApplication, hubdleAndroidLibrary)

    override val priority: Priority = Priority.P4

    public val aidl: Property<Boolean?> = property { null }
    public val buildConfig: Property<Boolean?> = property { null }
    public val compose: Property<Boolean?> = property { null }
    public val renderScript: Property<Boolean?> = property { null }
    public val resValues: Property<Boolean?> = property { null }
    public val shaders: Property<Boolean?> = property { null }
    public val viewBinding: Property<Boolean?> = property { null }

    override fun Project.defaultConfiguration() {
        configurable {
            val feats = this@HubdleKotlinAndroidBuildFeaturesExtension
            configureAndroidCommonExtension {
                buildFeatures.aidl =
                    feats.aidl.orNull ?: propOrNull(BuildFeatures.aidl) ?: trueIfApp()
                buildFeatures.buildConfig =
                    feats.buildConfig.orNull ?: propOrNull(BuildFeatures.buildConfig) ?: trueIfApp()
                buildFeatures.compose =
                    feats.compose.orNull ?: propOrNull(BuildFeatures.compose) ?: false
                buildFeatures.renderScript =
                    feats.renderScript.orNull
                        ?: propOrNull(BuildFeatures.renderScript) ?: trueIfApp()
                buildFeatures.resValues =
                    feats.resValues.orNull ?: propOrNull(BuildFeatures.resValues) ?: trueIfApp()
                buildFeatures.shaders =
                    feats.shaders.orNull ?: propOrNull(BuildFeatures.shaders) ?: trueIfApp()
                buildFeatures.viewBinding =
                    feats.viewBinding.orNull ?: propOrNull(BuildFeatures.viewBinding) ?: false
            }
        }
    }
}

private fun Project.propOrNull(key: String): Boolean? = getPropertyOrNull(key)?.toBoolean()

private fun HubdleEnableableExtension.trueIfApp(): Boolean =
    hubdleAndroidApplication.isFullEnabled.get()

internal val HubdleEnableableExtension.hubdleAndroidBuildFeatures:
    HubdleKotlinAndroidBuildFeaturesExtension
    get() = getHubdleExtension()
