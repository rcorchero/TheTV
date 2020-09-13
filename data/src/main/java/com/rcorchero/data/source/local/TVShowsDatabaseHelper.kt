package com.rcorchero.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry

class TVShowsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TVShows.db"

        private const val SQL_COLUMNS =
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TVShowEntry.COLUMN_ID} INTEGER," +
                    "${TVShowEntry.COLUMN_NAME} TEXT," +
                    "${TVShowEntry.COLUMN_ORIGINAL_NAME} TEXT," +
                    "${TVShowEntry.COLUMN_OVERVIEW} TEXT," +
                    "${TVShowEntry.COLUMN_ORIGIN_COUNTRY} TEXT," +
                    "${TVShowEntry.COLUMN_ORIGINAL_LANGUAGE} TEXT," +
                    "${TVShowEntry.COLUMN_GENRE_IDS} TEXT," +
                    "${TVShowEntry.COLUMN_FIRST_AIR_DATE} TEXT," +
                    "${TVShowEntry.COLUMN_BACKDROP_PATH} TEXT," +
                    "${TVShowEntry.COLUMN_POSTER_PATH} TEXT," +
                    "${TVShowEntry.COLUMN_POPULARITY} REAL," +
                    "${TVShowEntry.COLUMN_VOTE_AVERAGE} REAL," +
                    "${TVShowEntry.COLUMN_VOTE_COUNT} INTEGER)"

        private const val SQL_CREATE_AIRING_TODAY =
            "CREATE TABLE ${TVShowEntry.TABLE_NAME_AIRING_TODAY} (" + SQL_COLUMNS
        private const val SQL_CREATE_POPULAR =
            "CREATE TABLE ${TVShowEntry.TABLE_NAME_POPULAR} (" + SQL_COLUMNS
        private const val SQL_CREATE_TOP_RATED =
            "CREATE TABLE ${TVShowEntry.TABLE_NAME_TOP_RATED} (" + SQL_COLUMNS

        private const val SQL_DELETE_AIRING_TODAY =
            "DROP TABLE IF EXISTS ${TVShowEntry.TABLE_NAME_AIRING_TODAY}"
        private const val SQL_DELETE_POPULAR =
            "DROP TABLE IF EXISTS ${TVShowEntry.TABLE_NAME_POPULAR}"
        private const val SQL_DELETE_TOP_RATED =
            "DROP TABLE IF EXISTS ${TVShowEntry.TABLE_NAME_TOP_RATED}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_AIRING_TODAY)
        db.execSQL(SQL_CREATE_POPULAR)
        db.execSQL(SQL_CREATE_TOP_RATED)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_AIRING_TODAY)
        db.execSQL(SQL_DELETE_POPULAR)
        db.execSQL(SQL_DELETE_TOP_RATED)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}