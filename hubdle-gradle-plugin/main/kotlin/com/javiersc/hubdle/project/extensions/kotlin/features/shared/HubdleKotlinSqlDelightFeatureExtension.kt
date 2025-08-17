package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import app.cash.sqldelight.gradle.SqlDelightDatabase
import app.cash.sqldelight.gradle.SqlDelightExtension
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.hubdleCatalog
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.cash_sqldelight_plugin
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.invoke

public open class HubdleKotlinSqlDelightFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    @HubdleDslMarker
    public fun databases(
        action: Action<NamedDomainObjectContainer<SqlDelightDatabase>> = Action {}
    ) {
        lazyConfigurable { action.invoke(the<SqlDelightExtension>().databases) }
    }

    @HubdleDslMarker
    public fun sqldelight(action: Action<SqlDelightExtension> = Action {}): Unit =
        fallbackAction(action)

    public val HSqlDialect: Provider<String> = provider {
        "app.cash.sqldeight:hsql-dialect:${SqldelightVersion.get()}"
    }

    public val MySqlDialect: Provider<String> = provider {
        "app.cash.sqldeight:mysql-dialect:${SqldelightVersion.get()}"
    }

    public val PostgresDialect: Provider<String> = provider {
        "app.cash.sqldeight:postgres-dialect:${SqldelightVersion.get()}"
    }

    public val SqldelightVersion: Provider<String>
        get() = provider {
            project.hubdleCatalog
                ?.findPlugin(cash_sqldelight_plugin)
                ?.getOrNull()
                ?.map { it.version.displayName }
                ?.orNull
        }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.SqlDelight)
        lazyConfigurable {}
    }
}

public interface HubdleKotlinSqlDelightDelegateFeatureExtension : BaseHubdleExtension {

    public val sqldelight: HubdleKotlinSqlDelightFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun sqldelight(action: Action<HubdleKotlinSqlDelightFeatureExtension> = Action {}) {
        sqldelight.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleSqlDelight: HubdleKotlinSqlDelightFeatureExtension
    get() = getHubdleExtension()

internal val Project.hubdleSqlDelight: HubdleKotlinSqlDelightFeatureExtension
    get() = getHubdleExtension()
