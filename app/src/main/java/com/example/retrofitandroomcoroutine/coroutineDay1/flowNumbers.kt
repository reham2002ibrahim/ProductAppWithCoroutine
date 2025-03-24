package com.example.retrofitandroomcoroutine.coroutineDay1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take


fun flowNumbers() = flow<Int> {

    for (i in 1..20) {
        delay(200)
            emit(i*2)
    }
}


suspend fun main(): Unit = coroutineScope {

    flowNumbers()
        .take(10)
        .collect {
            println(it)
        }

}
