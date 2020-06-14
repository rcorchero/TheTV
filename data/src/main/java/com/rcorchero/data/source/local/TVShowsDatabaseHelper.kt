package com.rcorchero.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class TVShowsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TVShows.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TVShowsContract.TVShowEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TVShowsContract.TVShowEntry.COLUMN_ID} INTEGER," +
                    "${TVShowsContract.TVShowEntry.COLUMN_NAME} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_ORIGINAL_NAME} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_OVERVIEW} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_ORIGIN_COUNTRY} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_ORIGINAL_LANGUAGE} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_GENRE_IDS} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_FIRST_AIR_DATE} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_BACKDROP_PATH} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_POSTER_PATH} TEXT," +
                    "${TVShowsContract.TVShowEntry.COLUMN_POPULARITY} REAL," +
                    "${TVShowsContract.TVShowEntry.COLUMN_VOTE_AVERAGE} REAL," +
                    "${TVShowsContract.TVShowEntry.COLUMN_VOTE_COUNT} INTEGER)"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${TVShowsContract.TVShowEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}