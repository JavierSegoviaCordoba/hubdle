package com.javiersc.hubdle.extensions.apis

import org.gradle.api.Project

public interface BaseHubdleDelegateExtension : BaseHubdleExtension {

    public val project: Project
}
