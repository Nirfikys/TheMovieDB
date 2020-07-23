package com.example.themoviedb.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.themoviedb.cache.MovieDao
import com.example.themoviedb.remote.ApiService

class MovieRepositoryImpl(
    private val remote: ApiService,
    private val cache: MovieDao
) : MovieRepository {
    override val savedMovies: LiveData<List<MoviePreviewEntity>> =
        Transformations.map(cache.getAllSavedMovies()) { it.map { it.toEntity() } }

    override suspend fun saveMovies(moviePreview: List<MoviePreviewEntity>) {
        cache.saveMovie(moviePreview.map { it.toSavedMovie() })
    }

    override suspend fun saveMovie(movie: MovieEntity) {
        cache.saveMovie(listOf(
            movie.toSaved()
        ))
    }

    override suspend fun deleteSavedMovie(moviePreview: MoviePreviewEntity) {
        cache.deleteSavedMovie(moviePreview.toSavedMovie())
    }

    override suspend fun getPopularMovies(page: Int?): PageMovieEntity {
        val remotePage = remote.getPopularMovies(page).toEntity()
        cache.cachePage(remotePage.toSavedMovie())
        return remotePage
    }

    override suspend fun getUpcomingMovies(page: Int?): PageMovieEntity {
        return remote.getUpcomingMovies(page).toEntity()
    }

    override suspend fun getMovieInfo(id: Int): MovieEntity {
        return remote.getMovieInfo(id).toEntity()
    }

}