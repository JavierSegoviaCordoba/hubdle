import com.javiersc.hubdle.settings.HubdleSettingsDslMarker
import java.io.File
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.maven

public const val JetBrainsComposeDevRepo: String =
    "https://maven.pkg.jetbrains.space/public/p/compose/dev"

public const val JetBrainsKotlinBootstrapRepo: String =
    "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap"

public const val JetBrainsKotlinDevRepo: String =
    "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev"

public const val SonatypeSnapshotsRepo: String =
    "https://oss.sonatype.org/content/repositories/snapshots"

@HubdleSettingsDslMarker
public fun RepositoryHandler.sonatypeSnapshot(action: MavenArtifactRepository.() -> Unit = {}) {
    maven(SonatypeSnapshotsRepo, action)
}

@HubdleSettingsDslMarker
public fun RepositoryHandler.jetbrainsKotlinBootstrap(
    action: MavenArtifactRepository.() -> Unit = {}
) {
    maven(JetBrainsKotlinBootstrapRepo, action)
}

@HubdleSettingsDslMarker
public fun RepositoryHandler.jetbrainsComposeDev(action: MavenArtifactRepository.() -> Unit = {}) {
    maven(JetBrainsComposeDevRepo, action)
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
