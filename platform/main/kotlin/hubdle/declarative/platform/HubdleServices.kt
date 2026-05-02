package hubdle.declarative.platform

import com.javiersc.kotlin.stdlib.AnsiColor
import com.javiersc.kotlin.stdlib.ansiColor
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider

public interface HubdleServices {
    @get:Inject public val pluginManager: PluginManager
    @get:Inject public val project: Project

    private val logger: Logger
        get() = Logging.getLogger(this::class.java)

    public fun HubdleFeatureEnabled.logLifecycle(message: () -> String) {
        val hubdleLoggingEnabled: Provider<Boolean> =
            project.providers
                .gradleProperty(HubdleProperties.Logging.Enabled)
                .map(String::toBooleanStrictOrNull)
                .orElse(false)

        val enabled: Provider<Boolean> = loggingEnabled.orElse(hubdleLoggingEnabled)
        if (enabled.get()) {
            logger.log(LogLevel.LIFECYCLE, message().ansiColor(AnsiColor.Foreground.Cyan))
        }
    }
}
