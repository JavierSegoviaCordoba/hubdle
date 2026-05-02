package hubdle.platform

public enum class PluginIds(public val id: String) {
    Sonarqube("org.sonarqube")
}

public fun HubdleServices.applyPlugin(pluginId: PluginIds) {
    pluginManager.apply(pluginId.id)
}
