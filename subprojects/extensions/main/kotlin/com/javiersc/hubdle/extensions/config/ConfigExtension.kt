package com.javiersc.hubdle.extensions.config

import com.javiersc.hubdle.extensions.config.semver.VersioningExtension
import com.javiersc.hubdle.extensions.internal.Config
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.internal.extensionTracker
import com.javiersc.semver.gradle.plugin.SemverExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the

public abstract class ConfigExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val versioning: VersioningExtension = objects.newInstance()

    public fun Project.versioning(
        action: Action<in VersioningExtension> = Action {},
    ): VersioningExtension {
        extensionTracker.put(Config.Versioning)

        pluginManager.apply(PluginIds.JavierSC.semver)

        action.execute(versioning)

        the<SemverExtension>().tagPrefix.set(versioning.tagPrefix)

        return versioning
    }
}
