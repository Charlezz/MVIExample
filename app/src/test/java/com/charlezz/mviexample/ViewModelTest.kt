package com.charlezz.mviexample

import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test

/**
 * @author soohwan.ok
 * @since
 */

@OptIn(DelicateCoroutinesApi::class)
class ViewModelTest{

    lateinit var viewModel: ViewModel

    @Before
    fun setup(){
        viewModel = ViewModel1()
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
            repeat(times) {
                viewModel.onEvent(Event.Increment)
            }
        }
        assert(viewModel.state.value.count == 0) {
            println("count = ${viewModel.state.value.count}")
        }
    }

    @Test
    fun allViewModelTest() = runBlocking {
        val viewModels = listOf(
            ViewModel1(),
            ViewModel2(),
            ViewModel3()
        )

        viewModels.forEachIndexed { index, viewModel ->
            val times = 100000
            launch(newSingleThreadContext("Worker1")) {
                repeat(times) {
                    viewModel.onEvent(Event.Decrement)
                }
            }
            launch(newSingleThreadContext("Worker2")) {
                repeat(times) {
                    viewModel.onEvent(Event.Increment)
                }
            }

            assert(if (viewModel is ViewModel1) viewModel.state.value.count != 0 else viewModel.state.value.count == 0) {
                println("count = ${viewModel.state.value.count}")
            }
        }
    }
}