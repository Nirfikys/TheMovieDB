package com.example.themoviedb.domain

interface MovieRepository {
    suspend fun getPopularMovies(page: Int?): PageMovieEntity
    suspend fun getUpcomingMovies(page: Int?): PageMovieEntity
    suspend fun getMovieInfo(id: Int): MovieEntity
}