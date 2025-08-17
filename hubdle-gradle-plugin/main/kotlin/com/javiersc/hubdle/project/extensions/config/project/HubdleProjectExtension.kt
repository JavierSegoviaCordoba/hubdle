package com.javiersc.hubdle.project.extensions.config.project

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.features.tasks.GenerateAdditionalDataTask
import com.javiersc.hubdle.project.extensions.shared.features.tasks.GenerateProjectDataTask
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
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

    public val projectData: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun generateProjectData(value: Boolean) {
        projectData.set(value)
    }

    public val additionalData: Property<String> = propertyOptional()

    @HubdleDslMarker
    public fun generateAdditionalData(data: AdditionalDataBuilder.() -> Unit) {
        additionalData.set(
            provider {
                val additionalData = AdditionalDataBuilder()
                additionalData.data()
                additionalData.text
            }
        )
    }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            GenerateProjectDataTask.register(project).configure { task ->
                task.enabled = isFullEnabled.get() && projectData.get()
            }

            GenerateAdditionalDataTask.register(project, additionalData).configure { task ->
                task.enabled = isFullEnabled.get() && additionalData.orNull.isNotNullNorBlank()
            }
        }
    }

    public class AdditionalDataBuilder {

        private var _text: MutableList<String> = mutableListOf()
        internal val text: String
            get() = _text.joinToString("\n")

        public fun text(text: String) {
            this._text.addAll(text.split("\n"))
        }

        public fun const(name: String, type: String, value: String) {
            _text.add("public const val $name: $type = \"$value\"")
        }
    }
}

internal val HubdleEnableableExtension.hubdleProjectConfig: HubdleProjectExtension
    get() = getHubdleExtension()
