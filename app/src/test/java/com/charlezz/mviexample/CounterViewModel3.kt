package com.charlezz.mviexample

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn

class CounterViewModel3 : CounterViewModel() {

    private val events = Channel<Event>()

    /**
     * state 를 더이상 외부에서 수정하지 못함.
     * 궁극적으로 이벤트 처리만을 통해 state 가 변경된다.
     */
    override val state = events.receiveAsFlow()
        .runningFold(State(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, State())

    override fun onEvent(event: Event) {
        events.trySend(event)
    }

    private fun reduceState(current: State, event: Event): State {
        return when (event) {
            is Event.Increment -> current.copy(count = current.count + 1)
            is Event.Decrement -> current.copy(count = current.count - 1)
        }
    }
}