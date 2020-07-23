package com.example.themoviedb.domain

import com.example.themoviedb.cache.SavedMoviePreviewEntity
import com.example.themoviedb.remote.MovieInfo
import java.io.Serializable

data class MovieEntity(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
):Serializable

fun MovieInfo.toEntity(): MovieEntity {
    return MovieEntity(
        id,
        title,
        posterPath ?: "",
        overview ?: ""
    )
}

fun MovieEntity.toSaved():SavedMoviePreviewEntity{
    return SavedMoviePreviewEntity(
        id,
        title,
        posterPath,
        overview
    )
}

