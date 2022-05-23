package com.javiersc.gradle.plugins.core

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.property

inline fun <reified T> ObjectFactory.convention(provider: Provider<T>): Property<T> =
    property<T>().convention(provider)
