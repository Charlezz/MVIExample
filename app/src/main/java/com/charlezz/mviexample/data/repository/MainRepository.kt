package com.charlezz.mviexample.data.repository

import com.charlezz.mviexample.data.api.ApiHelper
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getUsers() = apiHelper.getUsers()

}