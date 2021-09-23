package com.movie.search.model.di

import android.app.Application
import androidx.room.Room
import com.movie.search.model.api.ApiHelper
import com.movie.search.model.api.ApiHelperImpl
import com.movie.search.model.api.ApiService
import com.movie.search.model.repository.MovieListRepository
import com.movie.search.model.repository.MovieListRepositoryImpl
import com.movie.search.model.room.MovieDao
import com.movie.search.model.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService): ApiHelper = ApiHelperImpl(apiService)

    @Provides
    @Singleton
    fun provideMovieListRepository(
        apiHelper: ApiHelperImpl,
        movieDao: MovieDao
    ): MovieListRepository = MovieListRepositoryImpl(apiHelper, movieDao)

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: MovieDatabase.Callback): MovieDatabase {
        return Room.databaseBuilder(application, MovieDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.getMovieDao()
    }

    private const val BASE_URL = "https://movies-sample.herokuapp.com/api/"
}