import com.javiersc.hubdle.extensions.HubdleDslMarker
import org.gradle.api.Project
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

@HubdleDslMarker
public fun TaskContainer.testDependsOnPublishAllPublicationsToMavenLocalTestRepositoryFrom(
    project: DelegatingProjectDependency
) {
    withType<Test>().configureEach {
        dependsOn(project.dependencyProject.tasks.named(publishToMavenLocalTestRepository))
    }
}

@HubdleDslMarker
public fun TaskContainer.testDependsOnPublishAllPublicationsToMavenLocalTestRepositoryFrom(
    project: Project
) {
    withType<Test>().configureEach {
        dependsOn(project.tasks.named(publishToMavenLocalTestRepository))
    }
}

private const val publishToMavenLocalTestRepository =
    "publishAllPublicationsToMavenLocalTestRepository"
