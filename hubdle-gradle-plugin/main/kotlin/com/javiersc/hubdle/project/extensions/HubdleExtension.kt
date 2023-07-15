package com.javiersc.hubdle.project.extensions

import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.HubdleConfigExtension
import com.javiersc.hubdle.project.extensions.java.HubdleJavaExtension
import com.javiersc.hubdle.project.extensions.kotlin.HubdleKotlinExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    public val config: HubdleConfigExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun config(action: Action<HubdleConfigExtension>) {
        config.enableAndExecute(action)
    }

    public val java: HubdleJavaExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun java(action: Action<HubdleJavaExtension>) {
        java.enableAndExecute(action)
    }

    public val kotlin: HubdleKotlinExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun kotlin(action: Action<HubdleKotlinExtension>) {
        kotlin.enableAndExecute(action)
    }
}

@DslMarker public annotation class HubdleDslMarker
