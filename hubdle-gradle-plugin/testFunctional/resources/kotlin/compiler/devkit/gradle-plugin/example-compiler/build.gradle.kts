hubdle {
    kotlin {
        compilerPlugin {
            ideaVersionsEnabled(false)
            componentRegistrar("com.kotlin.devkit.DevKitPluginRegistrar")
            commandLineProcessor("com.kotlin.devkit.DevKitPluginCLP")
        }
    }
}
