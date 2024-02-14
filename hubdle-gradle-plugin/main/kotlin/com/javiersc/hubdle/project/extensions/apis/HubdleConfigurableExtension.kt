package com.javiersc.hubdle.project.extensions.apis

import org.gradle.api.Project

public abstract class HubdleConfigurableExtension(
    project: Project,
) : HubdleEnableableExtension(project) {

    internal abstract fun Project.defaultConfiguration()
}
