package com.charlezz.mviexample

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * - 이벤트 처리를 통해 state 가 변경된다.
 * - 외부 요인으로 state 변경 할 수 없음.
 */
class ViewModel3 : ViewModel() {

    private val events = Channel<Event>()

    //state reducer
    override val state = events.receiveAsFlow()
        .runningFold(State(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, State())

    override fun onEvent(event: Event) {
        viewModelScope.launch {
            events.send(event)
        }
    }

    private fun reduceState(current: State, event: Event): State {
        return when (event) {
            is Event.Increment -> current.copy(count = current.count + 1)
            is Event.Decrement -> current.copy(count = current.count - 1)
        }
    }
}