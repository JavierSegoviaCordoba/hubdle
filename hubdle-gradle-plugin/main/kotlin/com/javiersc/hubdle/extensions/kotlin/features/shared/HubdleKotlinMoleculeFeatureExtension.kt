package com.javiersc.hubdle.extensions.kotlin.features.shared

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinMoleculeFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.Molecule
        )
    }
}

public interface HubdleKotlinMoleculeDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val molecule: HubdleKotlinMoleculeFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun molecule(action: Action<HubdleKotlinMoleculeFeatureExtension> = Action {}) {
        molecule.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleMoleculeFeature: HubdleKotlinMoleculeFeatureExtension
    get() = getHubdleExtension()

internal val Project.hubdleMoleculeFeature: HubdleKotlinMoleculeFeatureExtension
    get() = getHubdleExtension()
