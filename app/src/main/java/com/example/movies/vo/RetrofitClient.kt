package com.example.movies.vo

import com.example.movies.BuildConfig
import com.example.movies.domain.WebService
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY: String = BuildConfig.API_KEY
private const val API_URL: String = BuildConfig.API_URL

object RetrofitClient {

    private val requestInterceptor = Interceptor { chain ->

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

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    val webservice: WebService by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build().create(WebService::class.java)
    }
}