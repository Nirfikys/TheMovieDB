package com.example.themoviedb.presenter.modules

import com.example.themoviedb.remote.ApiService
import com.example.themoviedb.remote.ServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(false)
}