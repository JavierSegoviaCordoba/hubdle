package hubdle.platform

import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Cyan
import com.javiersc.kotlin.stdlib.AnsiColor.Foreground.Yellow
import com.javiersc.kotlin.stdlib.ansiColor
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel.LIFECYCLE
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.configure

public interface HubdleServices {
    @get:Inject public val pluginManager: PluginManager
    @get:Inject public val project: Project
    @get:Inject public val providers: ProviderFactory
    @get:Inject public val objects: ObjectFactory

    private val logger: Logger
        get() = Logging.getLogger(this::class.java)

    public fun HubdleDefinition.logLifecycle(message: () -> String) {
        val hubdleLoggingEnabled: Provider<Boolean> =
            project.providers
                .gradleProperty(HubdleProperties.Logging.Enabled)
                .map(String::toBooleanStrictOrNull)
                .orElse(false)

        val loggingEnabled: Provider<Boolean> = loggingEnabled.orElse(hubdleLoggingEnabled)
        if (loggingEnabled.get()) {
            val label = "[HUBDLE|$featureName]".ansiColor(Cyan)
            val content = message().ansiColor(Yellow)
            logger.log(LIFECYCLE, "$label $content")
        }
    }

    public fun HubdleServices.gradleProperty(propertyName: String): Provider<String> =
        providers.gradleProperty(propertyName)
}

public inline fun <reified T : Any> HubdleServices.configure(noinline action: T.() -> Unit) {
    project.configure<T>(action)
}
