package hubdle.platform

public enum class PluginIds(public val id: String) {
    JetbrainsChangelog("org.jetbrains.changelog"),
    Sonarqube("org.sonarqube"),
}

public fun HubdleServices.applyPlugin(pluginId: PluginIds) {
    pluginManager.apply(pluginId.id)
}
