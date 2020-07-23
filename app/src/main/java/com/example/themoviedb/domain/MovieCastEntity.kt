package com.example.themoviedb.domain

import com.example.themoviedb.remote.Cast

data class MovieCastEntity(
    val id: Int,
    val character: String,
    val gender: Int,
    val profilePath: String,
    val name: String
)

fun Cast.toEntity(): MovieCastEntity {
    return MovieCastEntity(
        id,
        character,
        gender ?: 0,
        profilePath ?: "",
        name
    )
}
