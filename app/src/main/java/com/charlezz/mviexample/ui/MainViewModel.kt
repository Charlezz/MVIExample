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
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(), ContainerHost<MainState, String> {

    override val container: Container<MainState, String> = container(MainState())

    fun fetchUser() = intent{
        viewModelScope.launch {
            reduce { state.copy(loading = true) }
            val users = repository.getUsers()
            reduce { state.copy(users = users, loading = false) }
            postSideEffect("${users.size} user(s) loaded")
        }
    }

}