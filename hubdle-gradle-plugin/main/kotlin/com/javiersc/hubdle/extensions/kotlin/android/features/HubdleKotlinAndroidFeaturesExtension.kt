package com.javiersc.hubdle.extensions.kotlin.android.features

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.extensions.kotlin.android.library.hubdleAndroidLibrary
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinAndroidFeaturesExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAndroidApplication, hubdleAndroidLibrary)
}

internal val HubdleEnableableExtension.hubdleAndroidFeatures: HubdleKotlinAndroidFeaturesExtension
    get() = getHubdleExtension()
