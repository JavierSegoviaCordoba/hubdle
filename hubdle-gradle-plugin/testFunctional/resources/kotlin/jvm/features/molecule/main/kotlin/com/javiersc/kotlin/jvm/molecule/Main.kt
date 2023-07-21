package com.javiersc.kotlin.jvm.molecule

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import kotlinx.coroutines.runBlocking

fun main() {
    runCatching {
        runBlocking {
            val store = FooMoleculeStore(FooDatasource())
            moleculeFlow(RecompositionMode.Immediate) { store.state() }
                .collect {
                    if (it.counter == 1) store.effect(FooEffect.A)
                    if (it.counter == 3) store.effect(FooEffect.B)
                    if (it.counter == 5) error(it.counter)
                    println(it)
                }
        }
    }
}
