package com.example.themoviedb.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import javax.inject.Inject

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieCacheEntity")
    fun getAllSavedMovies(): LiveData<List<MovieCacheEntity>>

    @Inject
    suspend fun saveMovie(movie:MovieCacheEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieCacheEntity)
}