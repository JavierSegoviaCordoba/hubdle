import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

public val KotlinDependencyHandler.compose: ComposePlugin.Dependencies
    get() = project.extensions.getByType<ComposeExtension>().dependencies
