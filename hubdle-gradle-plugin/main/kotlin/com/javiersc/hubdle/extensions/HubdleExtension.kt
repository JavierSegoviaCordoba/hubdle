@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions

import com.javiersc.hubdle.extensions.config.HubdleConfigExtension
import com.javiersc.hubdle.extensions.kotlin.HubdleKotlinExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class HubdleExtension @Inject constructor(objects: ObjectFactory) {

    private val config: HubdleConfigExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.config(action: Action<in HubdleConfigExtension>) {
        action.execute(config)
    }

    private val kotlin: HubdleKotlinExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.kotlin(action: Action<in HubdleKotlinExtension>) {
        action.execute(kotlin)
    }
}

@DslMarker public annotation class HubdleDslMarker
