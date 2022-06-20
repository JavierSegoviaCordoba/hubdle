package com.javiersc.hubdle.extensions.config.documentation.readme

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions

@HubdleDslMarker
public open class ReadmeBadgesExtension : EnableableOptions {

    override var isEnabled: Boolean = IS_ENABLED

    public var kotlin: Boolean = KOTLIN
    public var mavenCentral: Boolean = MAVEN_CENTRAL
    public var snapshots: Boolean = SNAPSHOTS
    public var build: Boolean = BUILD
    public var coverage: Boolean = COVERAGE
    public var quality: Boolean = QUALITY
    public var techDebt: Boolean = TECH_DEBT

    public companion object {
        internal const val IS_ENABLED = false
        internal const val KOTLIN = true
        internal const val MAVEN_CENTRAL = true
        internal const val SNAPSHOTS = true
        internal const val BUILD = true
        internal const val COVERAGE = true
        internal const val QUALITY = true
        internal const val TECH_DEBT = true
    }
}
