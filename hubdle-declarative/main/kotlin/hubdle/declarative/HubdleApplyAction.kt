@file:Suppress("UnstableApiUsage")

package hubdle.declarative

import com.android.build.gradle.BasePlugin
import hubdle.platform.HubdleRootDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.kotlin.prepareKotlinIdeaImport
import hubdle.platform.tasks.lifecycle.FixChecksTask
import hubdle.platform.tasks.lifecycle.GenerateTask
import hubdle.platform.tasks.lifecycle.TestsTask
import org.gradle.features.binding.BuildModel
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectTypeApplyAction
import org.gradle.kotlin.dsl.apply

internal abstract class HubdleApplyAction :
    ProjectTypeApplyAction<HubdleRootDefinition, BuildModel.None>, HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleRootDefinition,
        buildModel: BuildModel.None,
    ) = definition.run {
        val isEnabled: Boolean = enabled.orElse(true).get()

        if (isEnabled) {
            logHubdleStatus()
            applyBasePlugin(this@HubdleApplyAction)
            registerHubdleLifecycleTasks(this@HubdleApplyAction)
            registerPrepareKotlinIdeaImportTask(this@HubdleApplyAction)
        }
    }

    private fun HubdleRootDefinition.logHubdleStatus() {
        logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
    }

    private fun applyBasePlugin(services: HubdleServices) {
        services.pluginManager.apply(BasePlugin::class)
    }

    private fun HubdleRootDefinition.registerHubdleLifecycleTasks(services: HubdleServices) {
        FixChecksTask.register(services.project).also { logRegisteringTask(FixChecksTask.NAME) }
        GenerateTask.register(services.project).also { logRegisteringTask(GenerateTask.NAME) }
        TestsTask.register(services.project).also { logRegisteringTask(TestsTask.NAME) }
    }

    private fun HubdleRootDefinition.registerPrepareKotlinIdeaImportTask(services: HubdleServices) {
        services.prepareKotlinIdeaImport.also { logRegisteringTask(it.name) }
    }

    private fun HubdleRootDefinition.logRegisteringTask(taskName: String) {
        logLifecycle { "Registering '$taskName' task" }
    }

    companion object {
        const val NAME = "hubdle"
    }
}
