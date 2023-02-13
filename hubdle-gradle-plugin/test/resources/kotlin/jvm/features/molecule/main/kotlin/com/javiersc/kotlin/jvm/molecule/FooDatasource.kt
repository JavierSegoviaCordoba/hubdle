package com.javiersc.kotlin.jvm.molecule

import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FooDatasource {
    val flow: Flow<Int> = flow {
        emit(1)
        delay(20.milliseconds)
        emit(2)
        delay(20.milliseconds)
        emit(3)
        delay(20.milliseconds)
        emit(4)
        delay(20.milliseconds)
        emit(5)
    }
}
