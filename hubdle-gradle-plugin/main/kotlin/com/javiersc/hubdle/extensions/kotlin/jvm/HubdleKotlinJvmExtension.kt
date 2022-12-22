package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions._internal.configureDependencies
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.config.publishing._internal.configurableMavenPublishing
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlin
import com.javiersc.hubdle.extensions.kotlin.jvm.features.HubdleKotlinJvmFeaturesExtension
import com.javiersc.hubdle.extensions.shared.HubdleGradleDependencies
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinJvmExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleGradleDependencies {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    override val priority: Priority = Priority.P3

    public val features: HubdleKotlinJvmFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinJvmFeaturesExtension>) {
        features.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun main(action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("main", action::execute) }
        }
    }

    @HubdleDslMarker
    public fun test(action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("test", action::execute) }
        }
    }

    @HubdleDslMarker
    public fun kotlin(action: Action<KotlinJvmProjectExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinJvm
        )

        configurable {
            configureDefaultKotlinSourceSets()
            configureDependencies()
        }
        configurableMavenPublishing(mavenPublicationName = "java", configJavaExtension = true)
    }
}

internal val HubdleEnableableExtension.hubdleKotlinJvm: HubdleKotlinJvmExtension
    get() = getHubdleExtension()

internal val Project.hubdleKotlinJvm: HubdleKotlinJvmExtension
    get() = getHubdleExtension()
