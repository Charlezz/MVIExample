package com.charlezz.mviexample.data.api

import com.charlezz.mviexample.data.model.User
import retrofit2.http.GET

interface ApiService {

   @GET("users")
   suspend fun getUsers(): List<User>
}