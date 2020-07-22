package com.example.themoviedb.cache

import androidx.room.*

@Entity
data class PageCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val pageId:Int?,
    // TODO: 22.07.2020 enum
    val type: String,
    val page:Int,
    val totalPages:Int
)

@Entity(primaryKeys = ["pageId", "movieId"])
data class PageMovieCrossRef(
    val movieId:Int,
    val pageId:Int
)

data class PageWithMovie(
    @Embedded val page:PageCacheEntity,
    @Relation(
        parentColumn = "pageId",
        entityColumn = "movieId",
        associateBy = Junction(PageMovieCrossRef::class)
    )
    val movies:List<MoviePreviewCacheEntity>
)
