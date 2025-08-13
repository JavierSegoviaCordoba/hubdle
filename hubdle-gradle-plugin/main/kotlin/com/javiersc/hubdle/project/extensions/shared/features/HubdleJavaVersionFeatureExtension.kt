package com.javiersc.hubdle.project.extensions.shared.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.withAndroidApplication
import com.javiersc.hubdle.project.extensions._internal.withAndroidLibrary
import com.javiersc.hubdle.project.extensions._internal.withJava
import com.javiersc.hubdle.project.extensions._internal.withKotlin
import com.javiersc.hubdle.project.extensions._internal.withKotlinAndroid
import com.javiersc.hubdle.project.extensions._internal.withKotlinJvm
import com.javiersc.hubdle.project.extensions._internal.withKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
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
        fun getJvmTarget(): Provider<JvmTarget> =
            jvmVersion.map { version -> JvmTarget.fromTarget("$version") }

        withAndroidApplication {
            compileOptions {
                sourceCompatibility = jvmVersion.get()
                targetCompatibility = jvmVersion.get()
            }
        }

        withAndroidLibrary {
            compileOptions {
                sourceCompatibility = jvmVersion.get()
                targetCompatibility = jvmVersion.get()
            }
        }

        withJava {
            sourceCompatibility = jvmVersion.get()
            targetCompatibility = jvmVersion.get()
        }

        withKotlin {
            tasks.withType<KotlinCompile>().configureEach { kotlinCompile: KotlinCompile ->
                kotlinCompile.compilerOptions.jvmTarget.set(getJvmTarget())
            }
        }

        withKotlinAndroid { compilerOptions.jvmTarget.set(getJvmTarget()) }

        withKotlinJvm { compilerOptions.jvmTarget.set(getJvmTarget()) }

        withKotlinMultiplatform {
            targets.withType<KotlinJvmTarget>().configureEach {
                it.compilerOptions.jvmTarget.set(getJvmTarget())
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
