package com.javiersc.hubdle.extensions.config.documentation.readme

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class ReadmeBadgesExtension : EnableableOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.documentation.readme.badges.isEnabled
        set(value) = hubdleState.config.documentation.readme.badges.run { isEnabled = value }

    public var Project.kotlin: Boolean
        get() = hubdleState.config.documentation.readme.badges.kotlin
        set(value) = hubdleState.config.documentation.readme.badges.run { kotlin = value }

    public var Project.mavenCentral: Boolean
        get() = hubdleState.config.documentation.readme.badges.mavenCentral
        set(value) = hubdleState.config.documentation.readme.badges.run { mavenCentral = value }

    public var Project.snapshots: Boolean
        get() = hubdleState.config.documentation.readme.badges.snapshots
        set(value) = hubdleState.config.documentation.readme.badges.run { snapshots = value }

    public var Project.build: Boolean
        get() = hubdleState.config.documentation.readme.badges.build
        set(value) = hubdleState.config.documentation.readme.badges.run { build = value }

    public var Project.coverage: Boolean
        get() = hubdleState.config.documentation.readme.badges.coverage
        set(value) = hubdleState.config.documentation.readme.badges.run { coverage = value }

    public var Project.quality: Boolean
        get() = hubdleState.config.documentation.readme.badges.quality
        set(value) = hubdleState.config.documentation.readme.badges.run { quality = value }

    public var Project.techDebt: Boolean
        get() = hubdleState.config.documentation.readme.badges.techDebt
        set(value) = hubdleState.config.documentation.readme.badges.run { techDebt = value }
}
