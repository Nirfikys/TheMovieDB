package com.example.themoviedb.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MoviePreviewCacheEntity::class, PageCacheEntity::class, PageMovieCrossRef::class, SavedMoviePreviewEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}