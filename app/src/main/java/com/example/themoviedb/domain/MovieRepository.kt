package com.example.themoviedb.domain

import androidx.lifecycle.LiveData

interface MovieRepository {
    val savedMovies:LiveData<List<MoviePreviewEntity>>
    suspend fun saveMovies(moviePreview: List<MoviePreviewEntity>)
    suspend fun deleteSavedMovie(moviePreview: MoviePreviewEntity)
    suspend fun getPopularMovies(page: Int?): PageMovieEntity
    suspend fun getUpcomingMovies(page: Int?): PageMovieEntity
    suspend fun getMovieInfo(id: Int): MovieEntity
}