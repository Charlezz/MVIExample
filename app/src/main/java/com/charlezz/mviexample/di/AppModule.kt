package com.charlezz.mviexample.di

import com.charlezz.mviexample.data.api.ApiHelper
import com.charlezz.mviexample.data.api.ApiHelperImpl
import com.charlezz.mviexample.data.api.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * @author soohwan.ok
 * @since
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    companion object{
        @Provides
        @Singleton
        fun providesRetrofit():Retrofit{
            return Retrofit.Builder()
                .baseUrl("https://637e448ecfdbfd9a63acc2a1.mockapi.io/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun providesApiService(retrofit:Retrofit):ApiService{
            return retrofit.create(ApiService::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindsApiHelper(apiHelper:ApiHelperImpl):ApiHelper
}