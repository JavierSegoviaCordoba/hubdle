import com.javiersc.hubdle.settings.HubdleSettingsDslMarker
import java.io.File
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.maven

@HubdleSettingsDslMarker
public fun RepositoryHandler.sonatypeSnapshot(action: MavenArtifactRepository.() -> Unit = {}) {
    maven("https://oss.sonatype.org/content/repositories/snapshots", action)
}

@HubdleSettingsDslMarker
public fun RepositoryHandler.jetbrainsKotlinBootstrap(
    action: MavenArtifactRepository.() -> Unit = {}
) {
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap", action)
}

@HubdleSettingsDslMarker
public fun RepositoryHandler.jetbrainsComposeDev(action: MavenArtifactRepository.() -> Unit = {}) {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev", action)
}

@HubdleSettingsDslMarker
public fun RepositoryHandler.mavenLocalTest(action: MavenArtifactRepository.() -> Unit = {}) {
    val userHome = File(System.getProperty("user.home"))
    maven(url = userHome.resolve("mavenLocalTest/repository").toURI()) {
        name = "mavenLocalTest"
        action(this)
    }
}

@HubdleSettingsDslMarker
public fun RepositoryHandler.mavenLocalBuildTest(action: MavenArtifactRepository.() -> Unit = {}) {
    val workingDir = File(System.getProperty("user.dir"))
    maven(url = workingDir.resolve("build/mavenLocalBuildTest/repository").toURI()) {
        name = "mavenLocalBuildTest"
        action(this)
    }
}
