import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    kotlin {
        jvm {
            target = 12
        }
    }
}

println("HELLO")
println(project.version)
project.tasks.withType<KotlinCompile>().all {
    println(kotlinOptions.jvmTarget)
}
