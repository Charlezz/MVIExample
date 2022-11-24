package com.charlezz.mviexample.data.api

import com.charlezz.mviexample.data.model.User

interface ApiHelper {

    suspend fun getUsers(): List<User>

}