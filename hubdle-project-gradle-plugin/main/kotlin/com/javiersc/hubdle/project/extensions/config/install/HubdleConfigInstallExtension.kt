package com.javiersc.hubdle.project.extensions.config.install

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.HubdleConfigInstallPreCommitsExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleConfigInstallExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val preCommits: HubdleConfigInstallPreCommitsExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun preCommits(action: Action<HubdleConfigInstallPreCommitsExtension> = Action {}) {
        preCommits.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleInstall: HubdleConfigInstallExtension
    get() = getHubdleExtension()
