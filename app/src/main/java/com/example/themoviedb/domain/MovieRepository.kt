package com.example.themoviedb.domain

import androidx.lifecycle.LiveData

interface MovieRepository {
    val savedMovies:LiveData<List<MovieEntity>>
    suspend fun saveMovie(movieEntity: MovieEntity)
    suspend fun deleteSavedMovie(movieEntity: MovieEntity)
    suspend fun getPopularMovies(page: Int?): PageMovieEntity
    suspend fun getUpcomingMovies(page: Int?): PageMovieEntity
    suspend fun getMovieInfo(id: Int): MovieEntity
}