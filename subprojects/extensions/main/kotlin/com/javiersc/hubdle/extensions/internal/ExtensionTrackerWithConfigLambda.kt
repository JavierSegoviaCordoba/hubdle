// package com.javiersc.hubdle.extensions.internal
//
// internal val extensionTracker = ExtensionTracker()
//
// internal class ExtensionTracker {
//
//    private val _extensions: MutableList<ExtensionTrack> = mutableListOf()
//    private val extensions: Set<ExtensionTrack> = _extensions.toSet()
//
//    inline fun <reified T : ExtensionTrack> has(): Boolean = extensions.any { it is T }
//
//    inline fun <reified T : ExtensionTrack> get(): ExtensionTrack {
//        val tracks: List<T> = extensions.filterIsInstance<T>()
//        check(tracks.size == 1) { "There is 0 or more than 1 `Ext`" }
//        return tracks.first()
//    }
//
//    inline fun <reified T : ExtensionTrack> getOrNull(): ExtensionTrack? =
//        runCatching { get<T>() }.getOrNull()
//
//    inline fun <reified T : ExtensionTrack> addConfig(noinline config: () -> Unit) {
//        val ext = getOrNull<T>()
//        val previousConfig = ext?.config
//        ext?.config = {
//            previousConfig?.invoke()
//            config()
//        }
//    }
//
//    fun put(extension: ExtensionTrack, config: () -> Unit) {
//        if (extension !in extensions) {
//            extension.config = config
//            _extensions.add(extension)
//        }
//    }
//
//    fun checkCompatibility() {
//        val tracks = extensions.toList()
//        for (track in tracks) {
//            track.compatible(tracks - track)
//        }
//    }
// }
//
// private fun Set<ExtensionTrack>.maxOneKotlin(): Boolean = count { it is Kotlin } <= 1
