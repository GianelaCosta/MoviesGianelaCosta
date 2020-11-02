package com.example.movies.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    @SerializedName("author")
    val reviewer: String = "",
    @SerializedName("content")
    val reviewContent: String = ""
): Parcelable


data class ReviewsList(
    @SerializedName("results")
    val reviewsList: List<Review>
)