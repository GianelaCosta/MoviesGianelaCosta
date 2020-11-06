package com.example.movies.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("author")
    val reviewer: String = "",
    @SerializedName("content")
    val reviewContent: String = ""
) : Parcelable


data class ReviewsList(
    @SerializedName("results")
    val reviewsList: List<Review>
)

@Entity(tableName = "reviewsEntity")
data class ReviewEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "movie_id")
    val movieId: Int = 0,
    @ColumnInfo(name = "author")
    val reviewer: String = "",
    @ColumnInfo(name = "content")
    val reviewContent: String = ""
)
