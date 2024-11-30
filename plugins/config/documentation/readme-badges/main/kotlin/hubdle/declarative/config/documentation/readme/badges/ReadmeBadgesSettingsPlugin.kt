@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme.badges

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.internal.plugins.software.RegistersSoftwareTypes
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

@RegistersSoftwareTypes(value = [ReadmeBadgesProjectPlugin::class])
public abstract class ReadmeBadgesSettingsPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        val logger: Logger = Logging.getLogger(ReadmeBadgesSettingsPlugin::class.java)
        logger.quiet("Hubdle Declarative Settings Plugin applied")
    }
}
