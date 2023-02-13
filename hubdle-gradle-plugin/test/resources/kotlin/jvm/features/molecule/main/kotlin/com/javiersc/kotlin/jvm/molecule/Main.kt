package com.javiersc.kotlin.jvm.molecule

import app.cash.molecule.RecompositionClock
import app.cash.molecule.moleculeFlow
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val store = FooMoleculeStore(FooDatasource())
        moleculeFlow(RecompositionClock.Immediate) { store.state() }.collect {
            if (it.counter == 1) store.effect(FooEffect.A)
            if (it.counter == 3) store.effect(FooEffect.B)
            if (it.counter == 5) error(it.counter)
            println(it)
        }
    }
}
