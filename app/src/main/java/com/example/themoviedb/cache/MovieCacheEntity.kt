package com.example.themoviedb.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieCacheEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is MovieCacheEntity -> id == other.id
            else -> super.equals(other)
        }
    }
}