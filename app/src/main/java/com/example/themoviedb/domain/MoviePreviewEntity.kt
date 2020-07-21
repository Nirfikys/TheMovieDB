package com.example.themoviedb.domain

import com.example.themoviedb.remote.MoviePreview

data class MoviePreviewEntity (
    val id:Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun MoviePreview.toEntity():MoviePreviewEntity{
    return MoviePreviewEntity(
        id,
        title,
        posterPath ?: "",
        overview ?: ""
    )
}
