import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

val KotlinSourceSet.defaultLanguageSettings: Unit
    get() {
        with(languageSettings) {
            useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            useExperimentalAnnotation("kotlin.RequiresOptIn")
            useExperimentalAnnotation("kotlin.time.ExperimentalTime")
            useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            useExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
            useExperimentalAnnotation("kotlinx.coroutines.ObsoleteCoroutinesApi")
            useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
            useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
            useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
        }
    }
