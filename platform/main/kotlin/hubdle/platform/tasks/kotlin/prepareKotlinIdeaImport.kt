package hubdle.platform.tasks.kotlin

import hubdle.platform.HubdleServices
import org.gradle.api.Task

public val HubdleServices.prepareKotlinIdeaImport: Task
    get() = project.tasks.maybeCreate("prepareKotlinIdeaImport")
