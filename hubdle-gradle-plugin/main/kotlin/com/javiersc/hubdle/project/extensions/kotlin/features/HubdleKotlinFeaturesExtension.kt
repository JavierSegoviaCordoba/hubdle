package com.javiersc.hubdle.project.extensions.kotlin.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinFeaturesExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)
}

internal val HubdleEnableableExtension.hubdleKotlinFeatures: HubdleKotlinFeaturesExtension
    get() = getHubdleExtension()
