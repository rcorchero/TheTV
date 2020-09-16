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
import com.rcorchero.domain.exception.CacheError
import com.rcorchero.domain.exception.DeleteSuccess
import com.rcorchero.domain.exception.SaveSuccess
import com.rcorchero.domain.functional.Either
import java.util.*

class TVShowsLocalDataSourceImpl(
    private val sqLiteOpenHelper: SQLiteOpenHelper
) : TVShowsLocalDataSource {

    companion object {
        const val SQL_GET_AIRING_TODAY = "SELECT * FROM $TABLE_NAME_AIRING_TODAY"
        const val SQL_GET_POPULAR = "SELECT * FROM $TABLE_NAME_POPULAR"
        const val SQL_GET_TOP_RATED = "SELECT * FROM $TABLE_NAME_TOP_RATED"
    }

    override fun getTVShows(type: TVShowsType): Either<CacheError, List<TVShowEntity>> {
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

        return Either.Right(movieEntityList)
    }

    override fun saveTVShows(
        type: TVShowsType,
        tvShowsList: List<TVShowEntity>
    ): Either<CacheError, SaveSuccess> {
        val db = sqLiteOpenHelper.writableDatabase

        db.beginTransaction()
        for (tvShow in tvShowsList) {
            db.insert(type.tableName(), null, tvShow.getContentValues())
        }
        db.setTransactionSuccessful()
        db.endTransaction()
        return Either.Right(SaveSuccess)
    }

    override fun deleteTVShows(type: TVShowsType): Either<CacheError, DeleteSuccess> {
        val db = sqLiteOpenHelper.writableDatabase
        db.delete(type.tableName(), null, null)
        return Either.Right(DeleteSuccess)
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