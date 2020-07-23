package com.example.themoviedb.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.themoviedb.cache.MovieDao
import com.example.themoviedb.cache.SavedMoviePreviewEntity
import com.example.themoviedb.remote.ApiService

class MovieRepositoryImpl(
    private val remote: ApiService,
    private val cache: MovieDao
) : MovieRepository {
    override val savedMovies: LiveData<List<MoviePreviewEntity>> =
        Transformations.map(cache.getAllSavedMovies()) { it.map { it.toEntity() } }

    override suspend fun saveOrDeleteMovies(moviePreview: List<MoviePreviewEntity>) {
        val movieToSave = ArrayList<SavedMoviePreviewEntity>()
        val movieToDelete = ArrayList<SavedMoviePreviewEntity>()
        moviePreview.map { it.toSavedMovie() }.forEach {
            val savedMovie = cache.getSavedMovieById(it.movieId)
            if (savedMovie == null)
                movieToSave.add(it)
            else
                movieToDelete.add(it)
        }
        cache.saveMovies(movieToSave)
        cache.deleteSavedMovies(movieToDelete)
    }

    override suspend fun saveOrDeleteMovie(movie: MovieEntity) {
        val savedMovie = cache.getSavedMovieById(movie.id)
        val movieToSave = movie.toSaved()
        if (savedMovie != null)
            cache.deleteSavedMovie(movieToSave)
        cache.saveMovie(movieToSave)
    }

    override suspend fun getCastMovie(movie: MovieEntity): List<MovieCastEntity> {
        return remote.getCastInfo(movie.id).cast.map { it.toEntity() }
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