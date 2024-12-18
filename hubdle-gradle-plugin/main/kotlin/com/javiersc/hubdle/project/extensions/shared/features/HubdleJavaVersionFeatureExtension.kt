package com.javiersc.hubdle.project.extensions.shared.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public open class HubdleJavaVersionFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny + hubdleJava

    override val noneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJvmToolchainFeature)

    public val jvmVersion: Property<JavaVersion> = property { JavaVersion.VERSION_1_8 }

    @HubdleDslMarker
    public fun jvmVersion(version: JavaVersion) {
        this.jvmVersion.set(version)
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            tasks.withType<JavaCompile>().configureEach {
                it.sourceCompatibility = "${jvmVersion.get()}"
                it.targetCompatibility = "${jvmVersion.get()}"
            }

            tasks.withType<KotlinCompile>().configureEach {
                it.compilerOptions.jvmTarget.set(JvmTarget.fromTarget("${jvmVersion.get()}"))
            }
        }
    }
}

public interface HubdleJavaVersionDelegateFeatureExtension : BaseHubdleExtension {

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
