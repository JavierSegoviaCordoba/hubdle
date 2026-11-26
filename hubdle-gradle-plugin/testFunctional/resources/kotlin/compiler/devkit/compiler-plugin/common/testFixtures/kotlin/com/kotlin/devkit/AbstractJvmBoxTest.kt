package com.kotlin.devkit

import org.jetbrains.kotlin.compiler.plugin.devkit.runners.DevKitJvmBoxTest
import org.jetbrains.kotlin.compiler.plugin.devkit.services.configurePlugin

public open class AbstractJvmBoxTest : DevKitJvmBoxTest({ configurePlugin(DevKitPluginRegistrar()) })
