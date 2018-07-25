package com.ahndwon.gethub.api

import com.ahndwon.gethub.api.model.Issue
import com.ahndwon.gethub.api.model.UserData
import retrofit2.Call
import retrofit2.http.GET

interface IssueApi {
    @GET("user/issues")
    fun getIssue(): Call<List<Issue>>
}