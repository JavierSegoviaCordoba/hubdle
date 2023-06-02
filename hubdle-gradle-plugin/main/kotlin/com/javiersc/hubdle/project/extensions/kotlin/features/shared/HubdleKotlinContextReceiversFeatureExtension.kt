package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public open class HubdleKotlinContextReceiversFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    override fun Project.defaultConfiguration() {
        configurable {
            tasks.withType<KotlinCompile>().configureEach { task ->
                task.compilerOptions.freeCompilerArgs.add("-Xcontext-receivers")
            }
        }
    }
}

public interface HubdleKotlinContextReceiversDelegateFeatureExtension :
    BaseHubdleDelegateExtension {

    public val contextReceivers: HubdleKotlinContextReceiversFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun contextReceivers(
        action: Action<HubdleKotlinContextReceiversFeatureExtension> = Action {}
    ) {
        contextReceivers.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleContextReceivers:
    HubdleKotlinContextReceiversFeatureExtension
    get() = getHubdleExtension()

internal val Project.hubdleContextReceivers: HubdleKotlinContextReceiversFeatureExtension
    get() = getHubdleExtension()