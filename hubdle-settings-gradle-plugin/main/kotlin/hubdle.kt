import com.javiersc.hubdle.HubdleSettingsDslMarker
import com.javiersc.hubdle.HubdleSettingsExtension
import com.javiersc.hubdle.configureAutoInclude
import com.javiersc.hubdle.hubdle
import org.gradle.api.Action
import org.gradle.api.initialization.Settings

@HubdleSettingsDslMarker
public fun Settings.hubdle(action: Action<HubdleSettingsExtension> = Action {}) {
    action.execute(hubdle)

    configureAutoInclude()
}
