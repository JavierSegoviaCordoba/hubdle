package com.javiersc.hubdle.project.extensions.shared.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public open class HubdleJavaVersionFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val priority: Priority = Priority.P3

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val noneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJvmToolchainFeature)

    public val jvmVersion: Property<JavaVersion> = property { JavaVersion.VERSION_1_8 }

    @HubdleDslMarker
    public fun jvmVersion(version: JavaVersion) {
        this.jvmVersion.set(version)
    }

    override fun Project.defaultConfiguration() {
        configurable {
            tasks.withType<JavaCompile>().configureEach {
                it.sourceCompatibility = "${jvmVersion.get()}"
                it.targetCompatibility = "${jvmVersion.get()}"
            }

            tasks.withType<KotlinCompile>().configureEach {
                it.kotlinOptions.jvmTarget = "${jvmVersion.get()}"
            }
        }
    }
}

public interface HubdleJavaVersionDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val jvmVersion: HubdleJavaVersionFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun jvmVersion(version: JavaVersion) {
        jvmVersion.jvmVersion.set(version)
    }
}

internal val HubdleEnableableExtension.hubdleJavaVersionFeature: HubdleJavaVersionFeatureExtension
    get() = getHubdleExtension()

internal val Project.hubdleJavaVersionFeature: HubdleJavaVersionFeatureExtension
    get() = getHubdleExtension()
