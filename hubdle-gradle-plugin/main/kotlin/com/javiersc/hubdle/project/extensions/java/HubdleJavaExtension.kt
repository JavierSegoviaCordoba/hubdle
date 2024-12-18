package com.javiersc.hubdle.project.extensions.java

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.java.features.HubdleJavaFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin._internal.normalAndGeneratedDirs
import com.javiersc.hubdle.project.extensions.shared.PluginId
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure

@HubdleDslMarker
public open class HubdleJavaExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    public val features: HubdleJavaFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleJavaFeaturesExtension> = Action {}) {
        features.enableAndExecute(action)
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.GradleJava)
        configurableSourceSet()
    }

    private fun Project.configurableSourceSet() {
        lazyConfigurable {
            configure<JavaPluginExtension> {
                sourceSets.configureEach { set ->
                    set.java.setSrcDirs(emptySet<File>())
                    set.java.srcDirs(normalAndGeneratedDirs(provider { "${set.name}/java" }))
                    set.resources.setSrcDirs(emptySet<File>())
                    set.resources.srcDirs(
                        normalAndGeneratedDirs(provider { "${set.name}/resources" })
                    )
                }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleJava: HubdleJavaExtension
    get() = getHubdleExtension()
