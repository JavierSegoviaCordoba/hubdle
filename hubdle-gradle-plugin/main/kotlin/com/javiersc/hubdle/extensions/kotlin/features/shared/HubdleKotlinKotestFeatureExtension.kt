package com.javiersc.hubdle.extensions.kotlin.features.shared

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.catalogDependency
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinKotestFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    override fun Project.defaultConfiguration() {
        configurable {
            forKotlinSetsDependencies("test", "commonTest") {
                implementation(catalogDependency(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
            }
        }
    }
}

public interface HubdleKotlinKotestDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val kotest: HubdleKotlinKotestFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun kotest(action: Action<HubdleKotlinKotestFeatureExtension> = Action {}) {
        kotest.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleKotestFeature: HubdleKotlinKotestFeatureExtension
    get() = getHubdleExtension()
