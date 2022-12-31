package com.charlezz.mviexample

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

/**
 * - Channel 을 도입하여 이벤트를 순차적으로 처리함 (Thread-safety)
 * - updateState 함수 밖에서도 state 를 수정할 수 있음.
 */
class ViewModel2:ViewModel() {

    private val events = Channel<Event>()

    private val _state = MutableStateFlow(State())

    override val state:StateFlow<State> = MutableStateFlow(State())

    init {
        events.receiveAsFlow()
            .onEach(::updateState)
            .launchIn(viewModelScope)
    }

    override suspend fun onEvent(event: Event) {
        events.send(event)
    }

    private fun updateState(event: Event) {
        when (event) {
            is Event.Increment -> _state.update { it.copy(count = it.count + 1) }
            is Event.Decrement -> _state.update { it.copy(count = it.count - 1) }
        }
    }
}