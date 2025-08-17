package com.javiersc.hubdle.project.extensions.kotlin.compiler.options

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@HubdleDslMarker
public open class HubdleKotlinCompilerOptionsExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val apiVersion: Property<KotlinVersion> = propertyOptional()

    @HubdleDslMarker
    public fun apiVersion(value: KotlinVersion) {
        apiVersion.set(value)
    }

    @HubdleDslMarker
    public fun compilerOptions(action: Action<KotlinJvmCompilerOptions>) {
        tasks.withType<KotlinCompile>().configureEach { task -> task.compilerOptions(action) }
    }

    public val languageVersion: Property<KotlinVersion> = propertyOptional()

    @HubdleDslMarker
    public fun languageVersion(value: KotlinVersion) {
        languageVersion.set(value)
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            tasks.withType<KotlinCompile>().configureEach { task ->
                task.compilerOptions {
                    val hubdleOptions = this@HubdleKotlinCompilerOptionsExtension
                    val options: KotlinJvmCompilerOptions = this
                    options.apiVersion.set(hubdleOptions.apiVersion)
                    options.languageVersion.set(hubdleOptions.languageVersion)
                }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleKotlinCompilerOptions:
    HubdleKotlinCompilerOptionsExtension
    get() = getHubdleExtension()

internal val Project.hubdleKotlinCompilerOptions: HubdleKotlinCompilerOptionsExtension
    get() = getHubdleExtension()
