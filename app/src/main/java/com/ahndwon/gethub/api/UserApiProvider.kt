package com.ahndwon.gethub.api

import android.content.Context
import com.ahndwon.gethub.utils.authHttpClient
import com.ahndwon.gethub.utils.loggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun provideUserApi(context: Context) = Retrofit.Builder().apply {
    baseUrl("https://api.github.com/")
    client(authHttpClient(context))
    addConverterFactory(GsonConverterFactory.create())
}.build().create(UserApi::class.java)