package com.charlezz.mviexample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*

abstract class ViewModel {
    internal val viewModelScope:CoroutineScope = GlobalScope
    abstract val state:StateFlow<State>
    abstract fun onEvent(event:Event)
}