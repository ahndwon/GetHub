package com.ahndwon.gethub.api

import android.content.Context
import com.ahndwon.gethub.utils.authHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideGithubApi(context: Context): GithubApi = Retrofit.Builder().apply {
    baseUrl("https://api.github.com/")
    client(authHttpClient(context))
    addConverterFactory(GsonConverterFactory.create())
}.build().create(GithubApi::class.java)