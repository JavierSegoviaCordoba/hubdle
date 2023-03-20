package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl

@HubdleDslMarker
public open class HubdleKotlinMultiplatformJsExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)

    override val priority: Priority = Priority.P3

    override val targetName: String = "js"

    @HubdleDslMarker
    public fun browser(action: Action<KotlinJsBrowserDsl> = Action {}) {
        userConfigurable {
            configure<KotlinMultiplatformExtension> { js { browser { action.execute(this) } } }
        }
    }

    @HubdleDslMarker
    public fun nodejs(action: Action<KotlinJsNodeDsl> = Action {}) {
        userConfigurable {
            configure<KotlinMultiplatformExtension> { js { nodejs { action.execute(this) } } }
        }
    }

    override fun Project.defaultConfiguration() {
        configurable { configure<KotlinMultiplatformExtension> { js() } }
    }
}
