package com.javiersc.kotlin.jvm.molecule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FooMoleculeStore(
    private val datasource: FooDatasource,
) : MoleculeStore<FooEffect, FooState>() {

    @Composable
    override fun state(): FooState {
        var name by remember { mutableStateOf("Unknown") }
        val counter by datasource.flow.collectAsState(initial = 0)

        CollectedEffect { effect ->
            name =
                when (effect) {
                    FooEffect.A -> "A"
                    FooEffect.B -> "B"
                }
        }
        return FooState(name = name, counter = counter)
    }
}

abstract class MoleculeStore<E, S>(
    private val scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext),
) {

    private val events = MutableSharedFlow<E>(extraBufferCapacity = 20)

    @Composable abstract fun state(): S

    @Composable
    fun CollectedEffect(block: (effect: E) -> Unit) {
        LaunchedEffect(Unit) { events.collect { effect -> block(effect) } }
    }

    fun effect(effect: E) {
        scope.launch { events.emit(effect) }
    }
}
