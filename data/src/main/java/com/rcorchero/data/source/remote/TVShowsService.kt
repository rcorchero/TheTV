package com.rcorchero.data.source.remote

import com.rcorchero.data.entities.TVShowListEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TVShowsService {

    companion object {

        // API info
        private const val API_VERSION = "3"
        // TODO add your API key from https://developers.themoviedb.org/3/getting-started/introduction
        private const val API_KEY_VALUE = "YOUR-API-KEY"
        private const val DEFAULT_LANGUAGE = "es-ES"

        // URLs
        private const val TV = "$API_VERSION/tv"
        private const val AIRING_TODAY = "$TV/airing_today"
        private const val POPULAR = "$TV/popular"
        private const val TOP_RATED = "$TV/top_rated"

        // Query params
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
    }

    @GET(AIRING_TODAY)
    fun airingToday(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE
    ): Call<TVShowListEntity>

    @GET(POPULAR)
    fun popular(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE
    ): Call<TVShowListEntity>

    @GET(TOP_RATED)
    fun topRated(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE
    ): Call<TVShowListEntity>
}
