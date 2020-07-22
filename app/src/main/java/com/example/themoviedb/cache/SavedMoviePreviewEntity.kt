package com.example.themoviedb.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedMoviePreviewEntity(
    @PrimaryKey
    val movieId: Int,
    val title: String,
    val posterPath: String,
    val overview: String
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is SavedMoviePreviewEntity -> movieId == other.movieId
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return movieId
    }
}