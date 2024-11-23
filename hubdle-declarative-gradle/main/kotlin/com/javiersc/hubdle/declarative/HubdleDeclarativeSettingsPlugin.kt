@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.internal.plugins.software.RegistersSoftwareTypes
import org.gradle.api.logging.Logging

@RegistersSoftwareTypes(value = [HubdleDeclarativeProjectPlugin::class])
public abstract class HubdleDeclarativeSettingsPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        val logger = Logging.getLogger(HubdleDeclarativeSettingsPlugin::class.java)
        logger.quiet("Hubdle Declarative Settings Plugin applied")
    }
}
