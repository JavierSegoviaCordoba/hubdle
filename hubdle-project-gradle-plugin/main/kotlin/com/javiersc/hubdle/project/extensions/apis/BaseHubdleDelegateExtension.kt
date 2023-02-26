package com.javiersc.hubdle.project.extensions.apis

import org.gradle.api.Project

public interface BaseHubdleDelegateExtension : BaseHubdleExtension {

    public val project: Project
}
