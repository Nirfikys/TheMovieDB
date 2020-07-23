package com.example.themoviedb.domain

import com.example.themoviedb.cache.SavedMoviePreviewEntity
import com.example.themoviedb.remote.MovieInfo
import java.io.Serializable

data class MovieEntity(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val cast: List<MovieCastEntity>
) : Serializable

fun MovieInfo.toEntity(cast: List<MovieCastEntity>): MovieEntity {
    return MovieEntity(
        id,
        title,
        posterPath ?: "",
        overview ?: "",
        cast
    )
}

fun MovieEntity.toSaved(): SavedMoviePreviewEntity {
    return SavedMoviePreviewEntity(
        id,
        title,
        posterPath,
        overview
    )
}

