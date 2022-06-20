@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.DocumentationExtension
import com.javiersc.hubdle.extensions.config.install.InstallExtension
import com.javiersc.hubdle.extensions.config.nexus.NexusExtension
import com.javiersc.hubdle.extensions.config.versioning.VersioningExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class ConfigExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val documentation: DocumentationExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.documentation(action: Action<in DocumentationExtension> = Action {}) {
        action.execute(documentation)
    }

    private val install: InstallExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.install(action: Action<InstallExtension> = Action {}) {
        action.execute(install)
    }

    private val nexus: NexusExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.nexus(action: Action<NexusExtension> = Action {}) {
        nexus.isEnabled = true
        action.execute(nexus)
        hubdleState.config.nexus.isEnabled = nexus.isEnabled
    }

    private val versioning: VersioningExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versioning(action: Action<in VersioningExtension> = Action {}) {
        versioning.isEnabled = true
        action.execute(versioning)
        hubdleState.config.versioning.isEnabled = versioning.isEnabled
        hubdleState.config.versioning.tagPrefix = versioning.tagPrefix
    }
}
