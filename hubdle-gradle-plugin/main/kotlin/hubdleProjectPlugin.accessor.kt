import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.extensions._internal.checkCompatibility
import com.javiersc.hubdle.extensions._internal.hubdleState
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke

@HubdleDslMarker
public fun Project.hubdle(action: Action<HubdleExtension> = Action {}) {
    val hubdle: HubdleExtension = extensions.getByType()
    action.invoke(hubdle)

    checkCompatibility()
    hubdleState.configure()
}
