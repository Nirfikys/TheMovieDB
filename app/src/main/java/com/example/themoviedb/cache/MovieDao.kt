package com.example.themoviedb.cache

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM SavedMoviePreviewEntity")
    fun getAllSavedMovies(): LiveData<List<SavedMoviePreviewEntity>>

    @Query("SELECT * FROM SavedMoviePreviewEntity WHERE movieId = :id")
    suspend fun getSavedMovieById(id: Int): SavedMoviePreviewEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovies(movies: List<SavedMoviePreviewEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: SavedMoviePreviewEntity)

    @Delete
    suspend fun deleteSavedMovie(movie: SavedMoviePreviewEntity)

    @Delete
    suspend fun deleteSavedMovies(movies: List<SavedMoviePreviewEntity>)

    @Insert
    suspend fun cacheMovie(movie: MoviePreviewCacheEntity)

    @Insert
    suspend fun cacheMovies(movies: List<MoviePreviewCacheEntity>)

    @Delete
    suspend fun deleteMovie(movie: MoviePreviewCacheEntity)

    @Delete
    suspend fun deleteMovies(movies: List<MoviePreviewCacheEntity>)


    @Delete
    suspend fun deletePage(page: PageCacheEntity)

    @Query("SELECT * FROM PageCacheEntity WHERE page = :page")
    suspend fun getCachedPage(page: Int): PageCacheEntity?

    @Transaction
    @Query("SELECT * FROM PageCacheEntity WHERE page == :page")
    suspend fun getPage(page: Int): PageWithMovie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrosses(crosses: List<PageMovieCrossRef>)

    @Insert
    suspend fun insertPage(page: PageCacheEntity): Long

    @Transaction
    suspend fun cachePage(page: PageWithMovie) {
        val pageNumber = page.page.page
        val cachedPage = getCachedPage(pageNumber)
        if (cachedPage != null) {
            deletePage(cachedPage)
            deleteMovies(page.movies)
        }
        val pageId = insertPage(page.page).toInt()
        val cross = page.movies.map { PageMovieCrossRef(it.movieId, pageId) }
        insertCrosses(cross)
        cacheMovies(page.movies)
    }
}