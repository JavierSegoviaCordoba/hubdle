package com.javiersc.hubdle.extensions.kotlin.multiplatform.features

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.multiplatform._internal.multiplatformFeatures
import org.gradle.api.Action
import org.gradle.api.Project
import org.jetbrains.compose.desktop.DesktopExtension

@HubdleDslMarker
public open class ComposeExtension {

    public var Project.isEnabled: Boolean
        get() = multiplatformFeatures.compose.isEnabled
        set(value) = multiplatformFeatures.run { compose.isEnabled = value }

    @HubdleDslMarker
    public fun Project.desktop(action: Action<DesktopExtension> = Action {}) {
        hubdleState.kotlin.multiplatform.features.compose.desktop = action
    }
}
