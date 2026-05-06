package hubdle.platform

public enum class PluginIds(public val id: String) {
    JavierscSemver("com.javiersc.semver"),
    Sonarqube("org.sonarqube"),
}

public fun HubdleServices.applyPlugin(pluginId: PluginIds) {
    pluginManager.apply(pluginId.id)
}
