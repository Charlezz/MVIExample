package com.charlezz.mviexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlezz.mviexample.data.repository.MainRepository
import com.charlezz.mviexample.ui.intent.MainEvent
import com.charlezz.mviexample.ui.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val events = Channel<MainEvent>()

    val state: StateFlow<MainState> = events.receiveAsFlow()
        .runningFold(MainState(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainState())

    private val _sideEffects = Channel<String>()

    val sideEffects = _sideEffects.receiveAsFlow()

    private fun reduceState(current: MainState,event:MainEvent):MainState{
        return when(event){
            MainEvent.Loading -> {
                current.copy(loading = true)
            }
            is MainEvent.Loaded -> {
                current.copy(loading = false, users = event.users)
            }
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            events.send(MainEvent.Loading)
            val users = repository.getUsers()
            events.send(MainEvent.Loaded(users = users))
            _sideEffects.send("${users.size} user(s) loaded")
        }
    }


}