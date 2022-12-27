package com.charlezz.mviexample

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class CounterViewModel2:CounterViewModel() {

    /**
     * Channel 을 도입하여 이벤트를 순차적으로 처리함 (Thread-safety)
     */
    private val events = Channel<Event>()

    private val _state = MutableStateFlow(State())

    override val state:StateFlow<State> = MutableStateFlow(State())

    init {
        events.receiveAsFlow()
            .onEach(::updateState)
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: Event) {
        events.trySend(event)
    }

    // updateState 함수가 아니여도 state 를 수정하는 것이 가능은 함.
    private fun updateState(event: Event) {
        when (event) {
            is Event.Increment -> _state.update { it.copy(count = it.count + 1) }
            is Event.Decrement -> _state.update { it.copy(count = it.count - 1) }
        }
    }
}