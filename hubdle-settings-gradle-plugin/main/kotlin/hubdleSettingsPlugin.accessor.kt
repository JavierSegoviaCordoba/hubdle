import com.javiersc.hubdle.settings.HubdleSettingsDslMarker
import com.javiersc.hubdle.settings.HubdleSettingsExtension
import com.javiersc.hubdle.settings.hubdleSettings
import org.gradle.api.Action
import org.gradle.api.initialization.Settings

@HubdleSettingsDslMarker
public fun Settings.hubdleSettings(action: Action<HubdleSettingsExtension> = Action {}) {
    action.execute(hubdleSettings)
}
