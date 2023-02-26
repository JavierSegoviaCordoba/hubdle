package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension as BaseHubdleExt
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension as HubdleEnableableExt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

internal inline fun <reified T : BaseHubdleExt> Project.getHubdleExtension(): T =
    extensions.getByType()

internal inline fun <reified T : BaseHubdleExt> HubdleEnableableExt.getHubdleExtension(): T =
    project.getHubdleExtension()
