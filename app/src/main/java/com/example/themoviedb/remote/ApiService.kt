package com.example.themoviedb.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val API_KEY = "fbe4e6280f6a460beaad8ebe2bc130ac"

        //methods
        const val POPULAR_MOVIE = "3/discover/movie?api_key=$API_KEY&sort_by=popularity.desc"
        const val UPCOMING_MOVIE = "3/movie/upcoming?api_key=$API_KEY"
        const val INFO = "3/movie/{movie_id}?api_key=$API_KEY"
        const val CAST = "3/movie/{movie_id}/credits?api_key=$API_KEY"

        //params
        const val PARAM_PAGE = "page"
    }

    @GET(POPULAR_MOVIE)
    suspend fun getPopularMovies(@Query(PARAM_PAGE) page: Int?): PageMovie

    @GET(UPCOMING_MOVIE)
    suspend fun getUpcomingMovies(@Query(PARAM_PAGE) page: Int?): PageMovie

    @GET(INFO)
    suspend fun getMovieInfo(@Path("movie_id") id: Int): MovieInfo

    @GET(CAST)
    suspend fun getCastInfo(@Path("movie_id") id: Int): MovieCast
}