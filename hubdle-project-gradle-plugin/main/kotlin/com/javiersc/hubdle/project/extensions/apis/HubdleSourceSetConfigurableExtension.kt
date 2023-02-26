package com.javiersc.hubdle.project.extensions.apis

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.provider.Property

public abstract class HubdleSourceSetConfigurableExtension<T>(
    project: Project,
) : HubdleMinimalSourceSetConfigurableExtension<T>(project) {

    override val project: Project
        get() = super.project

    public val isTestFunctionalEnabled: Property<Boolean> = property { false }

    internal val isTestFunctionalFullEnabled: Property<Boolean> = property {
        isTestFunctionalEnabled.get() && isFullEnabled.get()
    }

    public abstract val testFunctional: NamedDomainObjectProvider<T>

    @HubdleDslMarker
    public fun testFunctional(action: Action<T> = Action {}) {
        isTestFunctionalEnabled.set(true)
        userConfigurable { testFunctional.configure(action) }
    }

    public val isTestIntegrationEnabled: Property<Boolean> = property { false }

    internal val isTestIntegrationFullEnabled: Property<Boolean> = property {
        isTestIntegrationEnabled.get() && isFullEnabled.get()
    }

    public abstract val testIntegration: NamedDomainObjectProvider<T>

    @HubdleDslMarker
    public fun testIntegration(action: Action<T> = Action {}) {
        isTestIntegrationEnabled.set(true)
        userConfigurable { testIntegration.configure(action) }
    }

    public val isTestFixturesEnabled: Property<Boolean> = property { false }

    internal val isTestFixturesFullEnabled: Property<Boolean> = property {
        isTestFixturesEnabled.get() && isFullEnabled.get()
    }

    public abstract val testFixtures: NamedDomainObjectProvider<T>

    @HubdleDslMarker
    public fun testFixtures(action: Action<T> = Action {}) {
        isTestFixturesEnabled.set(true)
        userConfigurable { testFixtures.configure(action) }
    }
}
