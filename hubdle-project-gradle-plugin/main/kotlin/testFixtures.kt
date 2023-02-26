import org.gradle.api.artifacts.Dependency
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

public fun KotlinDependencyHandler.testFixtures(notation: Any): Dependency =
    project.dependencies.testFixtures(notation)
