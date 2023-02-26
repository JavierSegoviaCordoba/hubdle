package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import app.cash.sqldelight.gradle.SqlDelightDatabase
import app.cash.sqldelight.gradle.SqlDelightExtension
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.SQLDELIGHT_VERSION
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.invoke

public open class HubdleKotlinSqlDelightFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    @HubdleDslMarker
    public fun databases(
        action: Action<NamedDomainObjectContainer<SqlDelightDatabase>> = Action {}
    ) {
        userConfigurable { action.invoke(the<SqlDelightExtension>().databases) }
    }

    @HubdleDslMarker
    public fun sqldelight(action: Action<SqlDelightExtension> = Action {}) {
        userConfigurable { action.invoke(the()) }
    }

    public val HSqlDialect: String = "app.cash.sqldeight:hsql-dialect:$SQLDELIGHT_VERSION"

    public val MySqlDialect: String = "app.cash.sqldeight:mysql-dialect:$SQLDELIGHT_VERSION"

    public val PostgresDialect: String = "app.cash.sqldeight:postgres-dialect:$SQLDELIGHT_VERSION"

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.SqlDelight
        )
        configurable {}
    }
}

public interface HubdleKotlinSqlDelightDelegateFeatureExtension : BaseHubdleDelegateExtension {

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
