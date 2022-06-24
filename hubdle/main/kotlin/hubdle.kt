import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.extensions._internal.state.checkCompatibility
import com.javiersc.hubdle.extensions._internal.state.configureState
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.getProperty
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the

public fun Project.hubdle(action: Action<HubdleExtension> = Action {}) {
    group = getProperty(PropertyKey.Project.group)

    action.invoke(the())

    checkCompatibility()
    configureState()
}
