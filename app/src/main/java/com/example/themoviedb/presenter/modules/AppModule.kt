package com.example.themoviedb.presenter.modules

import android.content.Context
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.domain.MovieRepositoryImpl
import com.example.themoviedb.remote.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideAppContext(): Context = context


    @Provides
    @Singleton
    fun provideMovieRepository(movieRemote: ApiService): MovieRepository {
        return MovieRepositoryImpl(movieRemote)
    }
}