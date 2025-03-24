package com.example.retrofitandroomcoroutine.coroutineDay2

import com.example.retrofitandroomcoroutine.coroutineDay1.flowNumbers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


suspend fun main(): Unit = coroutineScope {
    val sharedFlow = MutableSharedFlow<Int>(replay = 1)

    launch {
        sharedFlow.collect {
            println("theFirstFlow $it ")
        }
    }

    launch {
        sharedFlow.collect {
            println("theSecondFlow $it ")
        }
    }


    sharedFlow.emit(5)
    sharedFlow.emit(3)

    launch {
        sharedFlow.collect {
            println("theThirdFlow $it ")
        }
    }


}
