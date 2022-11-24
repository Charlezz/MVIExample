package com.charlezz.mviexample.ui.intent

sealed interface MainIntent{
    object FetchUsers: MainIntent
}
