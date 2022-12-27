package com.charlezz.mviexample

import kotlinx.coroutines.flow.*

/**
 * - Event 발생 -> State 변경 -> View 에 반영
 * - onEvent 함수는 스레드에 안전하지 않음.
 */
class ViewModel1 :ViewModel(){

    private val _state = MutableStateFlow(State())
    override val state:StateFlow<State> = _state

    override fun onEvent(event: Event) {
        when (event) {
            is Event.Increment -> _state.update { it.copy(count = it.count + 1) }
            is Event.Decrement -> _state.update { it.copy(count = it.count - 1) }
        }
    }
}