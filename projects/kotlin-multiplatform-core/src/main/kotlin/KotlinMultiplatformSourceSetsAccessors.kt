import org.gradle.api.NamedDomainObjectCollection as NamedCollection
import org.gradle.api.NamedDomainObjectProvider as NamedProvider
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

val NamedCollection<KotlinSourceSet>.androidMain: NamedProvider<KotlinSourceSet>
    get() = named("androidMain")

val NamedCollection<KotlinSourceSet>.androidTest: NamedProvider<KotlinSourceSet>
    get() = named("androidTest")

val NamedCollection<KotlinSourceSet>.iosMain: NamedProvider<KotlinSourceSet>
    get() = named("iosMain")

val NamedCollection<KotlinSourceSet>.iosTest: NamedProvider<KotlinSourceSet>
    get() = named("iosTest")

val NamedCollection<KotlinSourceSet>.iosArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("iosArm64Main")

val NamedCollection<KotlinSourceSet>.iosArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("iosArm64Test")

val NamedCollection<KotlinSourceSet>.iosSimulatorArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("iosSimulatorArm64Main")

val NamedCollection<KotlinSourceSet>.iosSimulatorArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("iosSimulatorArm64Test")

val NamedCollection<KotlinSourceSet>.iosX64Main: NamedProvider<KotlinSourceSet>
    get() = named("iosX64Main")

val NamedCollection<KotlinSourceSet>.iosX64Test: NamedProvider<KotlinSourceSet>
    get() = named("iosX64Test")

val NamedCollection<KotlinSourceSet>.jvmMain: NamedProvider<KotlinSourceSet>
    get() = named("jvmMain")

val NamedCollection<KotlinSourceSet>.jvmTest: NamedProvider<KotlinSourceSet>
    get() = named("jvmTest")

val NamedCollection<KotlinSourceSet>.jsMain: NamedProvider<KotlinSourceSet>
    get() = named("jsMain")

val NamedCollection<KotlinSourceSet>.jsTest: NamedProvider<KotlinSourceSet>
    get() = named("jsTest")

val NamedCollection<KotlinSourceSet>.linuxArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("linuxArm64Main")

val NamedCollection<KotlinSourceSet>.linuxArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("linuxArm64Test")

val NamedCollection<KotlinSourceSet>.linuxX64Main: NamedProvider<KotlinSourceSet>
    get() = named("linuxX64Main")

val NamedCollection<KotlinSourceSet>.linuxX64Test: NamedProvider<KotlinSourceSet>
    get() = named("linuxX64Test")

val NamedCollection<KotlinSourceSet>.macosArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("macosArm64Main")

val NamedCollection<KotlinSourceSet>.macosArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("macosArm64Test")

val NamedCollection<KotlinSourceSet>.macosX64Main: NamedProvider<KotlinSourceSet>
    get() = named("macosX64Main")

val NamedCollection<KotlinSourceSet>.macosX64Test: NamedProvider<KotlinSourceSet>
    get() = named("macosX64Test")

val NamedCollection<KotlinSourceSet>.mingwX64Main: NamedProvider<KotlinSourceSet>
    get() = named("mingwX64Main")

val NamedCollection<KotlinSourceSet>.mingwX64Test: NamedProvider<KotlinSourceSet>
    get() = named("mingwX64Test")

val NamedCollection<KotlinSourceSet>.tvosMain: NamedProvider<KotlinSourceSet>
    get() = named("tvosMain")

val NamedCollection<KotlinSourceSet>.tvosTest: NamedProvider<KotlinSourceSet>
    get() = named("tvosTest")

val NamedCollection<KotlinSourceSet>.tvosArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("tvosArm64Main")

val NamedCollection<KotlinSourceSet>.tvosArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("tvosArm64Test")

val NamedCollection<KotlinSourceSet>.tvosSimulatorArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("tvosSimulatorArm64Main")

val NamedCollection<KotlinSourceSet>.tvosSimulatorArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("tvosSimulatorArm64Test")

val NamedCollection<KotlinSourceSet>.tvosX64Main: NamedProvider<KotlinSourceSet>
    get() = named("tvosX64Main")

val NamedCollection<KotlinSourceSet>.tvosX64Test: NamedProvider<KotlinSourceSet>
    get() = named("tvosX64Test")

val NamedCollection<KotlinSourceSet>.watchosMain: NamedProvider<KotlinSourceSet>
    get() = named("watchosMain")

val NamedCollection<KotlinSourceSet>.watchosTest: NamedProvider<KotlinSourceSet>
    get() = named("watchosTest")

val NamedCollection<KotlinSourceSet>.watchosArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("watchosArm64Main")

val NamedCollection<KotlinSourceSet>.watchosArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("watchosArm64Test")

val NamedCollection<KotlinSourceSet>.watchosSimulatorArm64Main: NamedProvider<KotlinSourceSet>
    get() = named("watchosSimulatorArm64Main")

val NamedCollection<KotlinSourceSet>.watchosSimulatorArm64Test: NamedProvider<KotlinSourceSet>
    get() = named("watchosSimulatorArm64Test")

val NamedCollection<KotlinSourceSet>.watchosX64Main: NamedProvider<KotlinSourceSet>
    get() = named("watchosX64Main")

val NamedCollection<KotlinSourceSet>.watchosX64Test: NamedProvider<KotlinSourceSet>
    get() = named("watchosX64Test")
