package com.javiersc.hubdle.project.extensions.kotlin.jvm

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.configurableDependencies
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.maven.configurableMavenPublishing
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableKotlinTestFunctionalSourceSets
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableKotlinTestIntegrationSourceSets
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableSrcDirs
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableTestFixtures
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableTestFunctionalSourceSets
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableTestIntegrationSourceSets
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.HubdleKotlinJvmFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.shared.HubdleGradleDependencies
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

@HubdleDslMarker
public open class HubdleKotlinJvmExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinSourceSetConfigurableExtension(project), HubdleGradleDependencies {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val features: HubdleKotlinJvmFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinJvmFeaturesExtension>) {
        features.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun kotlin(action: Action<KotlinJvmProjectExtension>) {
        configurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsKotlinJvm)
        configurableTestFixtures()
        configurableSrcDirs()
        configurableKotlinTestFunctionalSourceSets()
        configurableTestIntegrationSourceSets()
        configurableKotlinTestIntegrationSourceSets()
        configurableTestFunctionalSourceSets()
        configurableKotlinTestFunctionalSourceSets()
        configurableDependencies()
        configurableMavenPublishing(
            mavenPublicationName = "java",
            configJavaExtension = true,
        )
    }
}

internal val HubdleEnableableExtension.hubdleKotlinJvm: HubdleKotlinJvmExtension
    get() = getHubdleExtension()

internal val Project.hubdleKotlinJvm: HubdleKotlinJvmExtension
    get() = getHubdleExtension()
