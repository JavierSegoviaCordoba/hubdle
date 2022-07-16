package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Action
import org.gradle.api.Project

public interface AndroidSdkBuildFeaturesOptions {

    public val buildFeatures: BuildFeaturesExtension

    @HubdleDslMarker
    public fun Project.buildFeatures(action: Action<BuildFeaturesExtension> = Action {}) {
        action.execute(buildFeatures)
    }

    @HubdleDslMarker
    public open class BuildFeaturesExtension {
        public var Project.aidl: Boolean?
            get() = feats.aidl
            set(value) = feats.run { aidl = value }

        public var Project.buildConfig: Boolean?
            get() = feats.buildConfig
            set(value) = feats.run { buildConfig = value }

        public var Project.compose: Boolean?
            get() = feats.compose
            set(value) = feats.run { compose = value }

        public var Project.renderScript: Boolean?
            get() = feats.renderScript
            set(value) = feats.run { renderScript = value }

        public var Project.resValues: Boolean?
            get() = feats.resValues
            set(value) = feats.run { resValues = value }

        public var Project.shaders: Boolean?
            get() = feats.shaders
            set(value) = feats.run { shaders = value }

        public var Project.viewBinding: Boolean?
            get() = feats.viewBinding
            set(value) = feats.run { viewBinding = value }
    }
}

private val Project.feats
    get() = hubdleState.kotlin.android.buildFeatures
