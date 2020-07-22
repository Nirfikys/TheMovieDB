package com.example.themoviedb.domain

import com.example.themoviedb.cache.MoviePreviewCacheEntity
import com.example.themoviedb.cache.SavedMoviePreviewEntity
import com.example.themoviedb.remote.MoviePreview

data class MoviePreviewEntity(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun MoviePreview.toEntity(): MoviePreviewEntity {
    return MoviePreviewEntity(
        id,
        title,
        posterPath ?: "",
        overview ?: ""
    )
}

fun SavedMoviePreviewEntity.toEntity(): MoviePreviewEntity {
    return MoviePreviewEntity(
        movieId,
        title,
        posterPath,
        overview
    )
}

fun MoviePreviewEntity.toSavedMovie(): SavedMoviePreviewEntity {
    return SavedMoviePreviewEntity(
        id,
        title,
        posterPath,
        overview
    )
}

fun MoviePreviewEntity.toCache(): MoviePreviewCacheEntity {
    return MoviePreviewCacheEntity(
        id,
        title,
        posterPath,
        overview
    )
}