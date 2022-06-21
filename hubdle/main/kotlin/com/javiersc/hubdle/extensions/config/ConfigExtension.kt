package com.javiersc.hubdle.extensions.config

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.semver.VersioningExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public abstract class ConfigExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val versioning: VersioningExtension = objects.newInstance()

    public fun Project.versioning(action: Action<in VersioningExtension> = Action {}) {
        pluginManager.apply(PluginIds.JavierSC.semver)

        action.execute(versioning)

        hubdleState.config.versioning.isEnabled = true
        hubdleState.config.versioning.tagPrefix = versioning.tagPrefix
    }
}
