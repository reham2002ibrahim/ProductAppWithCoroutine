package com.example.retrofitandroomcoroutine.coroutineDay1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


fun getNumbers() = flow<Int> {

    for (i in 1..10) {
        delay(200)
        emit(i)
    }
}



suspend fun main () : Unit = coroutineScope {

    getNumbers()
        .filter { it <= 5  }
        .map { it *2 }
        .collect{
            println(it)
        }

}
