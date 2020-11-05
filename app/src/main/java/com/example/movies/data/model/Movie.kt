package com.example.movies.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("poster_path")
    val imagePath: String = "",
    @SerializedName("title")
    val name: String = "",
    @SerializedName("overview")
    val description: String = "",
    @SerializedName("vote_average")
    val rate: Float = 0F,
    @SerializedName("popularity")
    val popularity: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    val createdTime: Long = 0
): Parcelable

data class MoviesList(
    @SerializedName("results")
    val moviesList: List<Movie>
)

@Entity(tableName = "moviesEntity")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "movie_backdrop_path")
    val backdropPath: String = "",
    @ColumnInfo(name = "movie_poster_path")
    val imagePath: String = "",
    @ColumnInfo(name = "movie_title")
    val name: String = "",
    @ColumnInfo(name = "movie_overview")
    val description: String = "",
    @ColumnInfo(name = "movie_vote_average")
    val rate: Float = 0F,
    @ColumnInfo(name = "movie_popularity")
    val popularity: String = "",
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "created_time")
    val createdTime: Long = System.currentTimeMillis()
)


