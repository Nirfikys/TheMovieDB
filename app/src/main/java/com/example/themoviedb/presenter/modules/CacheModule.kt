package com.example.themoviedb.presenter.modules

import android.content.Context
import androidx.room.Room
import com.example.themoviedb.cache.MovieDao
import com.example.themoviedb.cache.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun provideApiService(context: Context): MovieDao {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "themoviedb")
            .fallbackToDestructiveMigration()
            .build().movieDao
    }
}