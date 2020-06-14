package com.rcorchero.app.core.di.data

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.rcorchero.app.BuildConfig
import com.rcorchero.app.core.platform.NetworkHandler
import com.rcorchero.data.TVShowsRepositoryImpl
import com.rcorchero.data.source.local.TVShowsDatabaseHelper
import com.rcorchero.data.source.local.TVShowsLocalDataSource
import com.rcorchero.data.source.local.TVShowsLocalDataSourceImpl
import com.rcorchero.data.source.remote.TVShowsRemoteDataSource
import com.rcorchero.data.source.remote.TVShowsRemoteDataSourceImpl
import com.rcorchero.data.source.remote.TVShowsService
import com.rcorchero.domain.repository.TVShowsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
    }

    @Provides
    internal fun provideRepository(
        networkHandler: NetworkHandler,
        localDataSource: TVShowsLocalDataSource,
        remoteDataSource: TVShowsRemoteDataSource
    ): TVShowsRepository =
        TVShowsRepositoryImpl(
            isOnline = networkHandler.isOnline(),
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )

    @Provides
    @Singleton
    internal fun provideLocalDataSource(sqLiteOpenHelper: SQLiteOpenHelper): TVShowsLocalDataSource =
        TVShowsLocalDataSourceImpl(sqLiteOpenHelper)

    @Provides
    @Singleton
    internal fun provideSQLiteOpenHelper(context: Context): SQLiteOpenHelper =
        TVShowsDatabaseHelper(context)

    @Provides
    @Singleton
    internal fun provideRemoteDataSource(tvShowsService: TVShowsService): TVShowsRemoteDataSource =
        TVShowsRemoteDataSourceImpl(tvShowsService)

    @Provides
    @Singleton
    internal fun provideService(): TVShowsService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(createClient())
            .build()
            .create(TVShowsService::class.java)

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }
}