package com.example.movies.data.model

import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    @SerializedName("name")
    val genreName: String = ""
) : Parcelable

class GenreConverter {

    @TypeConverter
    fun listToJson(value: List<Genre>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()

}

