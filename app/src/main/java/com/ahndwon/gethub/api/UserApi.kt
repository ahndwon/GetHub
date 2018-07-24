package com.ahndwon.gethub.api

import com.ahndwon.gethub.api.model.UserData
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("user")
    fun getUser(): Call<UserData>
}