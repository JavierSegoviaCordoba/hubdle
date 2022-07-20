import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions.HubdleDslMarker
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

@HubdleDslMarker
public fun TaskContainer.testDependsOnPublishToMavenLocalTestFrom(
    project: DelegatingProjectDependency
) {
    val publishToMavenLocalTest =
        project.dependencyProject.tasks.namedLazily<Task>(publishToMavenLocalTest)
    withType<Test>().configureEach {
        dependsOn(publishToMavenLocalTest)
        dependsOn(project.dependencyProject.tasks.namedLazily<Task>(publishAllToMavenLocalTest))
    }
}

@HubdleDslMarker
public fun TaskContainer.testDependsOnPublishToMavenLocalTestFrom(project: Project) {
    val publishToMavenLocalTest = project.tasks.namedLazily<Task>(publishToMavenLocalTest)
    withType<Test>().configureEach {
        dependsOn(publishToMavenLocalTest)
        dependsOn(project.tasks.namedLazily<Task>(publishAllToMavenLocalTest))
    }
}

private const val publishToMavenLocalTest = "publishToMavenLocalTest"

private const val publishAllToMavenLocalTest = "publishAllPublicationsToMavenLocalTestRepository"
