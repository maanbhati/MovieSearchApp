package com.movie.search.model.api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("data")
    val movieList: List<Movie>?
) : Parcelable

@Parcelize
@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int?,
    @SerializedName("genre")
    val genre: String?,
    @SerializedName("poster")
    val poster: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("year")
    val year: String?
) : Parcelable