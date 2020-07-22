package com.example.themoviedb.domain

import com.example.themoviedb.cache.PageCacheEntity
import com.example.themoviedb.cache.PageWithMovie
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

fun PageMovieEntity.toSavedMovie():PageWithMovie{
    return PageWithMovie(
        PageCacheEntity(
            null,
            "popular",
            page,
            totalPages
        ),
        movies.map { it.toCache() }
    )
}