package hubdle.declarative.platform

import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import java.io.File
import java.util.Properties
import org.gradle.StartParameter
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property

public interface PlatformFactory {

    public val startParameter: StartParameter

    public val objects: ObjectFactory

    public val providers: ProviderFactory

    public val layout: ProjectLayout

    public val rootDirectory: File

    public fun <T> providers(action: ProviderFactory.() -> T): T = action(providers)

    public fun <T> provider(value: () -> T): Provider<T> = providers.provider(value)

    public fun getProperty(key: String): Provider<String> =
        getCliGradleProperty(key)
            .orElse(getLocalProperty(key))
            .orElse(gradleProperty(key))
            .orElse(getEnvironmentProperty(key))

    public fun getCliGradleProperty(key: String): Provider<String> {
        val property: String =
            startParameter.projectProperties[key]
                ?: startParameter.projectProperties[key.toSnakeCase()]
                ?: return objects.property()
        return provider { property }
    }

    public fun getLocalProperty(key: String): Provider<String> {
        val file: File =
            rootDirectory.resolve("local.properties").takeIf(File::exists)
                ?: layout.projectDirectory.file("local.properties").asFile

        if (!file.exists()) return objects.property()
        val properties: Properties = Properties().also { file.inputStream().use(it::load) }
        val property: String =
            properties.getProperty(key).takeIf { it.isNotNullNorBlank() }
                ?: properties.getProperty(key).takeIf { it.isNotNullNorBlank() }
                ?: return objects.property()
        return provider { property }
    }

    public fun getEnvironmentProperty(variableName: String): Provider<String> = providers {
        environmentVariable(variableName).orElse(environmentVariable(variableName.toSnakeCase()))
    }

    public fun gradleProperty(key: String): Provider<String> = providers {
        gradleProperty(key).orElse(gradleProperty(key.toSnakeCase()))
    }
}

public inline fun <reified T> PlatformFactory.property(value: () -> T): Property<T> =
    objects.property<T>().convention(value())

public inline fun <reified T> PlatformFactory.property(value: Provider<T>): Property<T> =
    objects.property<T>().convention(value)

internal fun String.toSnakeCase(): String =
    map { char -> if (char.isUpperCase()) "_$char" else char.uppercaseChar() }
        .joinToString("")
        .replace(".", "_")

public fun Project.platformFactory(action: PlatformFactory.() -> Unit) {
    val platformFactory: PlatformFactory =
        object : PlatformFactory {

            override val startParameter: StartParameter
                get() = this@platformFactory.gradle.startParameter

            override val objects: ObjectFactory
                get() = this@platformFactory.objects

            override val providers: ProviderFactory
                get() = this@platformFactory.providers

            override val layout: ProjectLayout
                get() = this@platformFactory.layout

            override val rootDirectory: File
                get() = this@platformFactory.rootDir
        }
    action(platformFactory)
}
