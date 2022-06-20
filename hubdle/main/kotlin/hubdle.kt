import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.extensions.checkCompatibility
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the

public fun Project.hubdle(action: Action<HubdleExtension> = Action {}) {
    action.invoke(the())

    checkCompatibility()
}
