package com.javiersc.hubdle.project.extensions.kotlin.android.features

import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.android._internal.configureAndroidCommonExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.project.extensions.kotlin.android.library.hubdleAndroidLibrary
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinAndroidBuildFeaturesExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAndroidApplication, hubdleAndroidLibrary)

    public val aidl: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.aidl).orElse(trueIfApp()).get()
    }
    public val buildConfig: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.buildConfig).orElse(trueIfApp()).get()
    }
    public val compose: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.compose).orElse(false).get()
    }
    public val renderScript: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.renderScript).orElse(trueIfApp()).get()
    }
    public val resValues: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.resValues).orElse(trueIfApp()).get()
    }
    public val shaders: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.shaders).orElse(trueIfApp()).get()
    }
    public val viewBinding: Property<Boolean> = property {
        getBooleanProperty(BuildFeatures.viewBinding).orElse(false).get()
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            val feats = this@HubdleKotlinAndroidBuildFeaturesExtension
            configureAndroidCommonExtension {
                buildFeatures.aidl = feats.aidl.get()
                buildFeatures.buildConfig = feats.buildConfig.get()
                buildFeatures.compose = feats.compose.get()
                buildFeatures.renderScript = feats.renderScript.get()
                buildFeatures.resValues = feats.resValues.get()
                buildFeatures.shaders = feats.shaders.get()
                buildFeatures.viewBinding = feats.viewBinding.get()
            }
        }
    }

    public object BuildFeatures {
        public const val aidl: String = "android.defaults.buildfeatures.aidl"
        public const val buildConfig: String = "android.defaults.buildfeatures.buildconfig"
        public const val compose: String = "android.defaults.buildfeatures.compose"
        public const val renderScript: String = "android.defaults.buildfeatures.renderscript"
        public const val resValues: String = "android.defaults.buildfeatures.resvalues"
        public const val shaders: String = "android.defaults.buildfeatures.shaders"
        public const val viewBinding: String = "android.defaults.buildfeatures.viewbinding"
    }
}

private fun HubdleEnableableExtension.trueIfApp(): Boolean =
    hubdleAndroidApplication.isFullEnabled.get()

internal val HubdleEnableableExtension.hubdleAndroidBuildFeatures:
    HubdleKotlinAndroidBuildFeaturesExtension
    get() = getHubdleExtension()
