package com.javiersc.hubdle.extensions

import com.javiersc.hubdle.extensions.config.ConfigExtension
import com.javiersc.hubdle.extensions.kotlin.KotlinExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public abstract class HubdleExtension @Inject constructor(objects: ObjectFactory) {

    private val config: ConfigExtension = objects.newInstance()

    public fun config(action: Action<in ConfigExtension>) {
        action.execute(config)
    }

    private val kotlin: KotlinExtension = objects.newInstance()

    public fun kotlin(action: Action<in KotlinExtension>) {
        action.execute(kotlin)
    }
}
