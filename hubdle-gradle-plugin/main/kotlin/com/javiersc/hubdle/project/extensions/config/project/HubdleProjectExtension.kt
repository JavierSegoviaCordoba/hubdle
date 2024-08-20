package com.javiersc.hubdle.project.extensions.config.project

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.features.tasks.GenerateProjectDataTask
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleProjectExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val generateProjectData: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun generateProjectData(value: Boolean) {
        generateProjectData.set(value)
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            GenerateProjectDataTask.register(project).configure { task ->
                task.enabled = isFullEnabled.get()
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleProjectConfig: HubdleProjectExtension
    get() = getHubdleExtension()
