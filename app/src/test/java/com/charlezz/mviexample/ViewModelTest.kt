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
    fun setup() {
        viewModel = ViewModel3()
    }

    @Test
    fun countMustBeZero() {
        println("# Test Started")
        runBlocking {
            println("# runBlocking Started")
            val times = 100000
            launch(newSingleThreadContext("Worker1")) {
                println("Worker1 Started")
                repeat(times) {
//                        println("++")
                    viewModel.onEvent(Event.Decrement)
                }
                println("Worker1 Finished")
            }
            launch(newSingleThreadContext("Worker2")) {
                println("Worker2 Started")
                repeat(times) {
//                        println("--")
                    viewModel.onEvent(Event.Increment)
                }
                println("Worker2 Finished")
            }.join()
            println("# runBlocking Finished")
        }
        assert(viewModel.state.value.count == 0) {
            println("count = ${viewModel.state.value.count}")
        }
        println("## Test Finished")
    }
}