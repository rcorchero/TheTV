package com.rcorchero.data.source.local

import android.database.sqlite.SQLiteOpenHelper
import com.rcorchero.data.entities.TVShowEntity
import com.rcorchero.data.source.local.TVShowsContract.TVShowEntry.TABLE_NAME
import java.util.ArrayList

class TVShowsLocalDataSourceImpl(
    private val sqLiteOpenHelper: SQLiteOpenHelper
) : TVShowsLocalDataSource {

    companion object {
        private const val SQL_GET_AIRING_TODAY = "SELECT * FROM $TABLE_NAME"
    }

    override fun getAiringTodayTvShows(): List<TVShowEntity> {
        val db = sqLiteOpenHelper.readableDatabase

        val movieEntityList = ArrayList<TVShowEntity>()

        val cursor = db.rawQuery(SQL_GET_AIRING_TODAY, null)
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

    override fun saveAiringTodayTvShows(tvShowsList: List<TVShowEntity>) {
        val db = sqLiteOpenHelper.writableDatabase

        db.beginTransaction()
        for (tvShow in tvShowsList) {
            db.insert(TABLE_NAME, null, tvShow.getContentValues())
        }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun deleteAiringTodayTvShows() {
        val db = sqLiteOpenHelper.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }
}