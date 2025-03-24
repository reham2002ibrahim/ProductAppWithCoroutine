package com.example.retrofitandroomcoroutine.coroutineDay1

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main () {
    val channel = Channel<Int>()
    var intList = arrayOf(1, 2, 3)
    runBlocking {
        launch {
            for (i in intList) {
                channel.send(i)
            }
        }
        // second launch
        launch {

            for (i in intList) {
                println(i)
            }
        }
    }
}