@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions

import com.javiersc.hubdle.extensions.config.ConfigExtension
import com.javiersc.hubdle.extensions.kotlin.KotlinExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class HubdleExtension @Inject constructor(objects: ObjectFactory) {

    private val config: ConfigExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.config(action: Action<in ConfigExtension>) {
        action.execute(config)
    }

    private val kotlin: KotlinExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.kotlin(action: Action<in KotlinExtension>) {
        action.execute(kotlin)
    }
}

@DslMarker public annotation class HubdleDslMarker
