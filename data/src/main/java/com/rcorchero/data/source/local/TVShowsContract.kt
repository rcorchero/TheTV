package com.rcorchero.data.source.local

import android.provider.BaseColumns

object TVShowsContract {

    object TVShowEntry: BaseColumns {

        const val TABLE_NAME_AIRING_TODAY = "airingToday"
        const val TABLE_NAME_POPULAR = "popular"
        const val TABLE_NAME_TOP_RATED = "topRated"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_ORIGINAL_NAME = "originalName"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_ORIGIN_COUNTRY = "originCountry"
        const val COLUMN_ORIGINAL_LANGUAGE = "originalLanguage"
        const val COLUMN_GENRE_IDS = "genreIds"
        const val COLUMN_FIRST_AIR_DATE = "firstAirDate"
        const val COLUMN_BACKDROP_PATH = "backdropPath"
        const val COLUMN_POSTER_PATH = "posterPath"
        const val COLUMN_POPULARITY = "popularity"
        const val COLUMN_VOTE_AVERAGE = "voteAverage"
        const val COLUMN_VOTE_COUNT = "voteCount"
    }
}