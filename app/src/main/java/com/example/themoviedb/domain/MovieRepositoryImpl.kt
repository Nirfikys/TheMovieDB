package com.example.themoviedb.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.themoviedb.cache.MovieDao
import com.example.themoviedb.remote.ApiService

class MovieRepositoryImpl(
    private val remote: ApiService,
    private val cache: MovieDao
) : MovieRepository {
    override val savedMovies: LiveData<List<MovieEntity>> =
        Transformations.map(cache.getAllSavedMovies()) { it.map { it.toEntity() } }

    override suspend fun saveMovie(movieEntity: MovieEntity) {
        cache.saveMovie(movieEntity.toCache())
    }

    override suspend fun deleteSavedMovie(movieEntity: MovieEntity) {
        cache.deleteMovie(movieEntity.toCache())
    }

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