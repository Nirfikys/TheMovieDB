package com.example.themoviedb.domain

import com.example.themoviedb.cache.MovieCacheEntity
import com.example.themoviedb.remote.MovieInfo

data class MovieEntity(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun MovieInfo.toEntity(): MovieEntity {
    return MovieEntity(
        id,
        title,
        posterPath ?: "",
        overview ?: ""
    )
}

fun MovieCacheEntity.toEntity():MovieEntity{
    return MovieEntity(
        id,
        title,
        posterPath,
        overview
    )
}

fun MovieEntity.toCache():MovieCacheEntity{
    return MovieCacheEntity(
        id,
        title,
        posterPath,
        overview
    )
}