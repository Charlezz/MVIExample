package com.charlezz.mviexample.ui.intent

import com.charlezz.mviexample.data.model.User

sealed interface MainEvent{
    object Loading:MainEvent
    class Loaded(val users:List<User>): MainEvent

}
