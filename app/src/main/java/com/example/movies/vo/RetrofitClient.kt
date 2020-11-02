package com.example.movies.vo

import com.example.movies.domain.WebService
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "52b7001946c183203509dceff3469d89"

object RetrofitClient {

    val requestInterceptor = Interceptor { chain ->

        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    val webservice by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build().create(WebService::class.java)
    }
}