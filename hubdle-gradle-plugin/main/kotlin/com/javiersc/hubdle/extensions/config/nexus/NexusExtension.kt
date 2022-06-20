package com.javiersc.hubdle.extensions.config.nexus

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions

@HubdleDslMarker
public open class NexusExtension : EnableableOptions {

    override var isEnabled: Boolean = IS_ENABLED

    public companion object {
        internal const val IS_ENABLED = false
    }
}
