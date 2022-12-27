package com.charlezz.mviexample

sealed class Event {
    object Increment : Event()
    object Decrement : Event()
}