package com.kotlin.devkit

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.devkit.DevKitCLP
import org.jetbrains.kotlin.config.CompilerConfiguration

public class DevKitPluginCLP : DevKitCLP {

    override val pluginOptions: Collection<AbstractCliOption> = emptyList()

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration,
    ) {}
}
