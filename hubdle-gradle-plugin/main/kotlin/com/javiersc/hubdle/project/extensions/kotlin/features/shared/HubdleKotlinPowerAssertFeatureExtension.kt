package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin._internal.isKotlinTest
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

public open class HubdleKotlinPowerAssertFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    public val functions: ListProperty<String> = listProperty {
        listOf(
            "kotlin.assert",
            "kotlin.check",
            "kotlin.checkNotNull",
            "kotlin.require",
            "kotlin.requireNotNull",
            "kotlin.test.assertContains",
            "kotlin.test.assertContentEquals",
            "kotlin.test.assertEquals",
            "kotlin.test.assertNotEquals",
            "kotlin.test.assertFails",
            "kotlin.test.assertFailsWith",
            "kotlin.test.assertFalse",
            "kotlin.test.assertIs",
            "kotlin.test.assertIsNot",
            "kotlin.test.assertNotNull",
            "kotlin.test.assertNotSame",
            "kotlin.test.assertNull",
            "kotlin.test.assertSame",
            "kotlin.test.assertTrue",
            "kotlin.test.expect",
        )
    }

    public val includedSourceSets: ListProperty<KotlinSourceSet> = listProperty {
        project.extensions
            .findByType<KotlinProjectExtension>()
            ?.sourceSets
            ?.filter { kotlinSourceSet -> kotlinSourceSet.isKotlinTest }
            ?.toList()
            .orEmpty()
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinPluginPowerAssert,
        )
        afterConfigurable {
            configure<PowerAssertGradleExtension> {
                val extension = this@HubdleKotlinPowerAssertFeatureExtension
                functions.set(extension.functions)
                includedSourceSets.set(
                    extension.includedSourceSets.map { it.map(KotlinSourceSet::getName) }
                )
            }
        }
    }
}

public interface HubdleKotlinPowerDelegateAssertFeatureExtension : BaseHubdleExtension {

    public val powerAssert: HubdleKotlinPowerAssertFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun powerAssert(action: Action<HubdleKotlinPowerAssertFeatureExtension> = Action {}) {
        powerAssert.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdlePowerAssertFeature:
    HubdleKotlinPowerAssertFeatureExtension
    get() = getHubdleExtension()
