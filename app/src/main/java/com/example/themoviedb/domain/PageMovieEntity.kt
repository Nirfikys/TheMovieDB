package com.example.themoviedb.domain

import com.example.themoviedb.remote.PageMovie

data class PageMovieEntity(
    val page: Int,
    val movies: List<MoviePreviewEntity>,
    val totalPages: Int
)

fun PageMovie.toEntity():PageMovieEntity{
    return PageMovieEntity(
        page,
        results.map { it.toEntity() },
        totalPages
    )
}