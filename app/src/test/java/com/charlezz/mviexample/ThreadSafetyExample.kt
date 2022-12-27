package com.charlezz.mviexample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author soohwan.ok
 * @since
 */

sealed class Event {
    object Increment : Event()
    object Decrement : Event()
}

data class State(
    val counter: Int = 0
)

class ViewModel {

    val state = MutableStateFlow(State())

    fun handleEvent(event: Event) {
        when (event) {
            is Event.Increment -> state.update { it.copy(counter = it.counter + 1) }
            is Event.Decrement -> state.update { it.copy(counter = it.counter - 1) }
        }
    }

}


class ThreadSafetyExample{

    lateinit var viewModel: ViewModel

    @Before
    fun setup(){
        viewModel = ViewModel()
    }
    @Test
    fun stateMustBeZero() = runBlocking{

        val count = 1000
        launch(Dispatchers.IO) {
            repeat(count){
                viewModel.handleEvent(Event.Decrement)
            }
        }
        launch(Dispatchers.Default) {
            repeat(count){
                viewModel.handleEvent(Event.Increment)
            }
        }
        println("final result = ${viewModel.state.value}")
    }
}