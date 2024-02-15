package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import org.gradle.api.Action

internal inline fun <reified T : Any> HubdleEnableableExtension.fallbackAction(action: Action<T>) {
    afterConfigurable { action.execute(the<T>()) }
}
