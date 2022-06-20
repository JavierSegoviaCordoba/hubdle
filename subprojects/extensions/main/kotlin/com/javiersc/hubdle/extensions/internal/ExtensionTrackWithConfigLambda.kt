// package com.javiersc.hubdle.extensions.internal
//
// internal sealed class ExtensionTrack {
//
//    private val name: String
//        get() = this::class.java.simpleName
//
//    var config: () -> Unit = {}
//
//    open fun compatible(features: List<ExtensionTrack>): Boolean = true
//
//    override fun toString(): String = "Feature: $name"
// }
//
// internal sealed class Config : ExtensionTrack() {
//
//    internal object Versioning : Config() {
//
//        override fun compatible(features: List<ExtensionTrack>): Boolean = true
//    }
// }
//
// internal sealed class Kotlin : ExtensionTrack() {
//
//    internal sealed class Android : Kotlin() {
//
//        internal object Library : Android()
//    }
//
//    internal object Gradle : Kotlin()
//
//    internal object JVM : Kotlin()
//
//    internal object Multiplatform : Kotlin()
//
//    internal object Publishing : Kotlin()
// }
