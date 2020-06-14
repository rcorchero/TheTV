package com.rcorchero.data.entities

import android.content.ContentValues
import android.database.Cursor
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_BACKDROP_PATH
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_FIRST_AIR_DATE
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_GENRE_IDS
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_ID
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_NAME
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_ORIGINAL_LANGUAGE
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_ORIGINAL_NAME
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_ORIGIN_COUNTRY
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_OVERVIEW
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_POPULARITY
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_POSTER_PATH
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_VOTE_AVERAGE
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.COLUMN_VOTE_COUNT
import com.rcorchero.data.source.local.listToString
import com.rcorchero.data.source.local.stringToList
import com.rcorchero.domain.model.TVShow
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVShowEntity(
    @Json(name = "id") val id: Int = -1,
    @Json(name = "name") val name: String = "",
    @Json(name = "original_name") val originalName: String = "",
    @Json(name = "overview") val overview: String = "",
    @Json(name = "origin_country") val originCountry: List<String> = listOf(),
    @Json(name = "original_language") val originalLanguage: String = "",
    @Json(name = "genre_ids") val genreIds: List<Int> = listOf(),
    @Json(name = "first_air_date") val firstAirDate: String = "",
    @Json(name = "backdrop_path") val backdropPath: String? = "",
    @Json(name = "poster_path") val posterPath: String? = "",
    @Json(name = "popularity") val popularity: Double = 0.0,
    @Json(name = "vote_average") val voteAverage: Double = 0.0,
    @Json(name = "vote_count") val voteCount: Int = 0
) {
    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    fun toDomainModel() = TVShow(
        id = id,
        name = name,
        voteAverage = voteAverage,
        posterUrl = getImageUrl()
    )

    private fun getImageUrl() = "$IMAGE_BASE_URL$posterPath"

    constructor(cursor: Cursor) : this(
        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
        cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_NAME)),
        cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)),
        cursor.getString(cursor.getColumnIndex(COLUMN_ORIGIN_COUNTRY)).stringToList() as List<String>,
        cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_LANGUAGE)),
        cursor.getString(cursor.getColumnIndex(COLUMN_GENRE_IDS)).stringToList() as List<Int>,
        cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_AIR_DATE)),
        cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP_PATH)),
        cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)),
        cursor.getDouble(cursor.getColumnIndex(COLUMN_POPULARITY)),
        cursor.getDouble(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE)),
        cursor.getInt(cursor.getColumnIndex(COLUMN_VOTE_COUNT))
    )

    fun getContentValues() = ContentValues().apply {
        put(COLUMN_ID, id)
        put(COLUMN_NAME, name)
        put(COLUMN_ORIGINAL_NAME, originalName)
        put(COLUMN_OVERVIEW, overview)
        put(COLUMN_ORIGIN_COUNTRY, originCountry.listToString())
        put(COLUMN_ORIGINAL_LANGUAGE, originalLanguage)
        put(COLUMN_GENRE_IDS, genreIds.listToString())
        put(COLUMN_FIRST_AIR_DATE, firstAirDate)
        put(COLUMN_BACKDROP_PATH, backdropPath)
        put(COLUMN_POSTER_PATH, posterPath)
        put(COLUMN_POPULARITY, popularity)
        put(COLUMN_VOTE_AVERAGE, voteAverage)
        put(COLUMN_VOTE_COUNT, voteCount)
    }
}

fun List<TVShowEntity>.toDomainModel(): List<TVShow> = this.map { it.toDomainModel() }