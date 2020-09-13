package com.rcorchero.data.source.local

import android.database.sqlite.SQLiteOpenHelper
import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.TABLE_NAME_AIRING_TODAY
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.TABLE_NAME_POPULAR
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.TABLE_NAME_TOP_RATED
import com.rcorchero.data.source.local.TVShowsLocalDataSource.TVShowsType
import com.rcorchero.data.source.local.TVShowsLocalDataSourceImpl.Companion.SQL_GET_AIRING_TODAY
import com.rcorchero.data.source.local.TVShowsLocalDataSourceImpl.Companion.SQL_GET_POPULAR
import com.rcorchero.data.source.local.TVShowsLocalDataSourceImpl.Companion.SQL_GET_TOP_RATED
import java.util.ArrayList

class TVShowsLocalDataSourceImpl(
    private val sqLiteOpenHelper: SQLiteOpenHelper
) : TVShowsLocalDataSource {

    companion object {
        const val SQL_GET_AIRING_TODAY = "SELECT * FROM $TABLE_NAME_AIRING_TODAY"
        const val SQL_GET_POPULAR = "SELECT * FROM $TABLE_NAME_POPULAR"
        const val SQL_GET_TOP_RATED = "SELECT * FROM $TABLE_NAME_TOP_RATED"
    }

    override fun getTVShows(type: TVShowsType): List<TVShowEntity> {
        val db = sqLiteOpenHelper.readableDatabase

        val movieEntityList = ArrayList<TVShowEntity>()

        val cursor = db.rawQuery(type.getAllSqlQuery(), null)
        if (cursor.moveToFirst()) {
            do {
                movieEntityList.add(TVShowEntity(cursor))
            } while (cursor.moveToNext())
        }

        if (!cursor.isClosed) {
            cursor.close()
        }

        return movieEntityList
    }

    override fun saveTVShows(type: TVShowsType, tvShowsList: List<TVShowEntity>) {
        val db = sqLiteOpenHelper.writableDatabase

        db.beginTransaction()
        for (tvShow in tvShowsList) {
            db.insert(type.tableName(), null, tvShow.getContentValues())
        }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun deleteTVShows(type: TVShowsType) {
        val db = sqLiteOpenHelper.writableDatabase
        db.delete(type.tableName(), null, null)
    }
}

fun TVShowsType.tableName(): String =
    when (this) {
        TVShowsType.AIRING_TODAY -> TABLE_NAME_AIRING_TODAY
        TVShowsType.POPULAR -> TABLE_NAME_POPULAR
        TVShowsType.TOP_RATED -> TABLE_NAME_TOP_RATED
    }

fun TVShowsType.getAllSqlQuery(): String =
    when (this) {
        TVShowsType.AIRING_TODAY -> SQL_GET_AIRING_TODAY
        TVShowsType.POPULAR -> SQL_GET_POPULAR
        TVShowsType.TOP_RATED -> SQL_GET_TOP_RATED
    }