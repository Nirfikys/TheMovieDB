package com.example.themoviedb.domain

import com.example.themoviedb.remote.ApiService

class MovieRepositoryImpl(
    private val remote: ApiService
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int?): PageMovieEntity {
        return remote.getPopularMovies(page).toEntity()
    }

    override suspend fun getUpcomingMovies(page: Int?): PageMovieEntity {
        return remote.getUpcomingMovies(page).toEntity()
    }

    override suspend fun getMovieInfo(id: Int): MovieEntity {
        return remote.getMovieInfo(id).toEntity()
    }

}