import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

@HubdleDslMarker
public fun Project.testDependsOnPublishToMavenLocalTestFrom(
    projectDependency: DelegatingProjectDependency
) {
    val tasks: TaskContainer = project.project(projectDependency.path).tasks
    val publishToMavenLocalTest: TaskProvider<Task> = tasks.named(PUBLISH_TO_MAVEN_LOCAL_TEST)
    tasks.withType<Test>().configureEach { task ->
        task.dependsOn(publishToMavenLocalTest)
        task.dependsOn(tasks.named(PUBLISH_ALL_TO_MAVEN_LOCAL_TEST))
    }
}

@HubdleDslMarker
public fun Project.testDependsOnPublishToMavenLocalTestFrom(project: Project) {
    val publishToMavenLocalTest = project.tasks.named(PUBLISH_TO_MAVEN_LOCAL_TEST)
    tasks.withType<Test>().configureEach { task ->
        task.dependsOn(publishToMavenLocalTest)
        task.dependsOn(project.tasks.named(PUBLISH_ALL_TO_MAVEN_LOCAL_TEST))
    }
}

internal const val PUBLISH_TO_MAVEN_LOCAL_TEST = "publishToMavenLocalTest"

internal const val PUBLISH_ALL_TO_MAVEN_LOCAL_TEST =
    "publishAllPublicationsToMavenLocalTestRepository"
