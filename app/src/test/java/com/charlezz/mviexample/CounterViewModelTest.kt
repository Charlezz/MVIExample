package com.charlezz.mviexample

import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test

/**
 * @author soohwan.ok
 * @since
 */

@OptIn(DelicateCoroutinesApi::class)
class CounterViewModelTest{

    lateinit var viewModel: CounterViewModel

    @Before
    fun setup(){
        viewModel = CounterViewModel1()
    }
    @Test
    fun countMustBeZero() = runBlocking{
        val times = 100000
        launch(newSingleThreadContext("Worker1")) {
            repeat(times){
                viewModel.onEvent(Event.Decrement)
            }
        }
        launch(newSingleThreadContext("Worker2")) {
            repeat(times){
                viewModel.onEvent(Event.Increment)
            }
        }
        assert(viewModel.state.value.count == 0){
            println("count = ${viewModel.state.value.count}")
        }
    }
}