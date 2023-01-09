package com.javiersc.hubdle.extensions.kotlin.multiplatform

import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.Android
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.Apple
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.IOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.Jvm
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.JvmAndAndroid
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.Linux
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.MacOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.MinGW
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.Native
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.TvOS
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformTarget.WatchOS

internal enum class HubdleKotlinMultiplatformTarget(val target: String) {
    Apple(target = "apple"),
    Common(target = "common"),
    Android(target = "android"),
    Jvm(target = "jvm"),
    JvmAndAndroid(target = "jvmAndAndroid"),
    IOS(target = "ios"),
    MacOS(target = "macos"),
    WatchOS(target = "watchos"),
    TvOS(target = "tvos"),
    Native(target = "native"),
    Linux(target = "linux"),
    MinGW(target = "mingw"),
    JS(target = "js"),
    WAsm(target = "wasm"),
    ;

    override fun toString(): String = target
}

internal val hubdleAppleTargets = setOf(Apple, IOS, MacOS, WatchOS, TvOS)
internal val nativeTargets = setOf(Native, IOS, Linux, MacOS, MinGW, WatchOS, TvOS)
internal val hubdleJvmTargets = setOf(Android, Jvm, JvmAndAndroid)
