package com.javiersc.hubdle.extensions.config.nexus

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class NexusExtension : EnableableOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.nexus.isEnabled
        set(value) = hubdleState.config.nexus.run { isEnabled = value }
}
