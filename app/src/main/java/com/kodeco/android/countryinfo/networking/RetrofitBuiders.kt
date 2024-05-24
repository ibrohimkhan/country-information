package com.kodeco.android.countryinfo.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://restcountries.com"

fun moshi() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun buildClient(): OkHttpClient = OkHttpClient
    .Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

fun buildRetrofit(): Retrofit = Retrofit.Builder()
    .client(buildClient())
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi()))
    .build()

fun buildApiService(): RemoteApiService =
    buildRetrofit().create(RemoteApiService::class.java)
