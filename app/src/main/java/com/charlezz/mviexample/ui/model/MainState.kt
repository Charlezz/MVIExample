package com.charlezz.mviexample.ui.model

import com.charlezz.mviexample.data.model.User

data class MainState(
    val users: List<User> = emptyList(),
    val loading:Boolean = false,
    val error: String? = null
)