package com.example.movies.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.data.DataSource
import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.vo.Resource
import java.util.*

class RepoImpl(private val dataSource: DataSource): Repo {

    override suspend fun getMoviesList(): Resource<List<Movie>> {
        return dataSource.getMovies()
   }

    override suspend fun getReviewsList(movieId: Int): Resource<List<Review>> {
        return dataSource.getReviews(movieId)
    }

    override suspend fun getSavedMoviesList(): List<MovieEntity> {
       return dataSource.getSavedMovies()
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        dataSource.insertMovieRoom(movie)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}